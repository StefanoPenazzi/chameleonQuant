/**
 * 
 */
package data.source.external.database.influxdb.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import data.source.external.database.influxdb.Influxdb;
import data.source.external.database.influxdb.mirrors.alphaVantage.StockEODTimeSeriesPointInfluxdb;
import data.source.external.web.connector.alphaVantage.AlphaVantageConnector;
import data.source.external.web.parameter.alphaVantage.functions.Function;
import data.source.external.web.parameter.alphaVantage.output.OutputSize;
import data.source.external.web.parameter.alphaVantage.output.OutputType;
import data.source.external.web.parameter.alphaVantage.symbols.Symbol;
import data.source.utils.IO.TxtUtils;

/**
 * @author stefanopenazzi
 *
 */
public class UpdateFromAlphaVantageStocksEOD extends UpdateFromAlphaVantageAbstract {

	/**
	 * @param maxReqPerMin
	 * @param maxReqPerDay
	 * @param nThreads
	 */
	public UpdateFromAlphaVantageStocksEOD(int maxReqPerMin, int maxReqPerDay, int nThreads) {
		super(maxReqPerMin, maxReqPerDay, nThreads);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Callable<Boolean> getWorker(String serie, String database) {
		return new WorkerAlphaVantageStocksEOD(serie,database,this.getAlphaVantageConnector(),this.getInfluxdb());
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
		test = test.replaceFirst("timestamp", "time");
		try {
			TxtUtils.stringToFile(test,csvPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object[] options = {true,',','"'};
		idb.writingBatchFromCsvFile(database, stock, csvPath ,StockEODTimeSeriesPointInfluxdb.class ,options);
		
		Files.deleteIfExists(Paths.get(csvPath)); 
		
		return true;
	}
	
}
