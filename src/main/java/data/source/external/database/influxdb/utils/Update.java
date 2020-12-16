/**
 * 
 */
package data.source.external.database.influxdb.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import data.source.external.database.influxdb.Influxdb;
import data.source.external.database.influxdb.InternalTimeSeriesQueryRequestInfluxdb;
import data.source.external.database.influxdb.mirrors.alphaVantage.StockTimeSeriesPointInfluxdb;
import data.source.external.web.connector.AlphaVantageConnector;
import data.source.external.web.parameter.alphaVantage.functions.Function;
import data.source.external.web.parameter.alphaVantage.intradaytimeseries.Interval;
import data.source.external.web.parameter.alphaVantage.intradaytimeseries.Slice;
import data.source.external.web.parameter.alphaVantage.output.OutputSize;
import data.source.external.web.parameter.alphaVantage.output.OutputType;
import data.source.external.web.parameter.alphaVantage.symbols.Symbol;
import data.source.utils.IO.TxtUtils;

/**
 * @author stefanopenazzi
 *
 */
public class Update {
	
	private static final Logger log = LogManager.getLogger(Update.class);
	
	public static void main(String[] args) throws Exception {
		//TODO check if the number of threads is greater than the max number of calls
		//TODO check if the number of stocks in the list is greater than the max num of stocks
		List<String> stocksList = new ArrayList<>();
		stocksList.add("AAPL"); 
		stocksList.add("AMZN");
		stocksList.add("TSLA");
		stocksList.add("FB");
		stocksList.add("BRK-A");
		stocksList.add("C");
		stocksList.add("AAL");
		stocksList.add("MSFT");
		stocksList.add("GOOGL");
		stocksList.add("PFE");
		stocksList.add("CSCO");
		
		updateFromAlphaVantage("84AHX76LXVJ25F65",stocksList,5,500,5);
	}
	
	public synchronized static void updateFromAlphaVantage(String alphaVantageKey, List<String> stocks, int maxReqPerMin, int maxReqPerDay, int nThreads) {
		
        double alpha  = 10;
		
		ExecutorService service = Executors.newFixedThreadPool(nThreads);
        
        AlphaVantageConnector avc = new AlphaVantageConnector(alphaVantageKey,60000);
		Influxdb idb = new Influxdb();
		final String serverURL = "http://127.0.0.1:7086", username = "stefanopenazzi", password = "korky1987";
		String[] dbCon = {serverURL,username,password};
		//the server must be on(service influxdb start) otherwise the connection will not be successful
		idb.connect(dbCon);
        int i=0;
		while (i<stocks.size()) {
			List<String> stocksT = new ArrayList<>();
			List<Future<Boolean>> futureResultList = new ArrayList<>();
			for(int j = i;j< i+maxReqPerMin; j++ ) {
				if(j< stocks.size()) {
					stocksT.add(stocks.get(j));
					Future<Boolean> future = service.submit(new Worker(stocks.get(j),avc,idb));
					futureResultList.add(future);
				}
			}
			i += maxReqPerMin;
			
			long startTime = System.nanoTime();
			boolean done = false;
			while(!done) {
				done = true;
			    for(Future<Boolean> f: futureResultList) {
			    	if(!f.isDone()) {
			    		done = false;
			    		break;
			    	}
			    }
			    if(!done) {
			    	try {
						Thread.sleep(3000);
						System.out.println("sleep");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
			}
			long endTime = System.nanoTime();
			double elapsedTime =(double)(endTime-startTime)/1_000_000_000.0;
//			if(elapsedTime < 60) {
//				long sleepTime = (long) (((60 - elapsedTime) + alpha)*1000);
//				try {
//					Thread.sleep(sleepTime);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		idb.close();
		service.shutdown();
	}

}
	
class Worker implements Callable<Boolean>{
	
	private static final Logger log = LogManager.getLogger(Worker.class);
	
	private final String stock;
	private final AlphaVantageConnector avc;
	private final Influxdb idb;
	
	public Worker(String stock, AlphaVantageConnector avc,Influxdb idb) {
		this.stock = stock;
		this.avc = avc;
		this.idb = idb;
	}

	@Override
	public Boolean call() throws Exception {
		
		String csvPath = System.getProperty("user.dir")+"/output/"+stock+"_"+Function.TIME_SERIES_DAILY+"_"+OutputSize.FULL+".csv";
		String test = avc.call(Function.TIME_SERIES_DAILY,new Symbol(stock),OutputSize.FULL,OutputType.CSV);
		try {
			TxtUtils.stringToFile(test,csvPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object[] options = {true,',','"'};
		idb.writingBatchFromCsvFile("NASDAQ", stock, csvPath ,StockTimeSeriesPointInfluxdb.class ,options);
		
		Files.deleteIfExists(Paths.get(csvPath)); 
		//log.info("stock: "+ stock + " -- thread id: " +Thread.currentThread().getName() + " -- date: " +LocalDateTime.now()
		//	       .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
		return true;
	}
	
}
