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
import data.source.external.database.tickers.Ticker;
import data.source.external.database.tickers.TickersSet;
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
public class UpdateFromAlphaVantage extends UpdateAbstract {
	
	private static final Logger log = LogManager.getLogger(UpdateFromAlphaVantage.class);
	private final AlphaVantageConnector avc;
	private final int maxReqPerMin;
	private final int maxReqPerDay;
	private final int nThreads;
	
	public UpdateFromAlphaVantage(int maxReqPerMin, int maxReqPerDay, int nThreads) {
		this.maxReqPerMin = maxReqPerMin;
		this.maxReqPerDay = maxReqPerDay;
		this.nThreads = nThreads;
		this.avc = new AlphaVantageConnector("84AHX76LXVJ25F65",60000);
	}

	@Override
	public Callable<Boolean> getWorker(String serie, String database) {
		return new WorkerAlphaVantageStocksEOD(serie,database,avc,this.getInfluxdb());
	}

	@Override
	public void afterUpdate(Object... objects) {
		// TODO Auto-generated method stub
	}

	@Override
	public void beforeUpdate(List<String> series, String database, int maxReqPerMin, int maxReqPerDay, int nThreads) {
		// TODO Auto-generated method stub
	}

	@Override
	public void runUpdate(final List<String> series,final String database) {
		this.updateMultiThreadingStopwatch_m_d(series,database,this.maxReqPerMin,this.maxReqPerDay,this.nThreads);
	}
}
	
class WorkerAlphaVantageStocksEOD implements Callable<Boolean>{
	
	private static final Logger log = LogManager.getLogger(WorkerAlphaVantageStocksEOD.class);
	
	private final String stock;
	private final AlphaVantageConnector avc;
	private final Influxdb idb;
	private final String database;
	
	public WorkerAlphaVantageStocksEOD(String stock, String database, AlphaVantageConnector avc,Influxdb idb) {
		this.stock = stock;
		this.avc = avc;
		this.idb = idb;
		this.database = database;
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
		idb.writingBatchFromCsvFile(database, stock, csvPath ,StockTimeSeriesPointInfluxdb.class ,options);
		
		Files.deleteIfExists(Paths.get(csvPath)); 
		
		return true;
	}
	
}
