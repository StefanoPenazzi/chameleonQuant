/**
 * 
 */
package data.source.external.database.influxdb.utils.update;

import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import data.source.external.database.influxdb.Influxdb;
import data.source.external.database.influxdb.mirrors.StockEODTimeSeriesPointInfluxdb;
import data.source.external.database.influxdb.mirrors.StockIDTimeSeriesPointInfluxdb;
import data.source.external.financialdatavendors.alphavantage.AlphaVantageConnector;
import data.source.external.financialdatavendors.alphavantage.parameters.functions.Function;
import data.source.external.financialdatavendors.alphavantage.parameters.intradaytimeseries.Interval;
import data.source.external.financialdatavendors.alphavantage.parameters.intradaytimeseries.Slice;
import data.source.external.financialdatavendors.alphavantage.parameters.output.OutputSize;
import data.source.external.financialdatavendors.alphavantage.parameters.output.OutputType;
import data.source.external.financialdatavendors.alphavantage.parameters.symbols.Symbol;
import data.source.utils.IO.CSVUtils;

/**
 * @author stefanopenazzi
 *
 */
public class UpdateFromAlphaVantageStocksID extends UpdateFromAlphaVantageAbstract {

	/**
	 * @param maxReqPerMin
	 * @param maxReqPerDay
	 * @param nThreads
	 */
	public UpdateFromAlphaVantageStocksID(int maxReqPerMin, int maxReqPerDay, int nThreads) {
		super(maxReqPerMin, maxReqPerDay, nThreads);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Callable<Boolean> getWorker(String serie, String database,Influxdb idb) {
		return new WorkerAlphaVantageStocksID(serie,database,this.getAlphaVantageConnector(),idb);
	}

}

class WorkerAlphaVantageStocksID implements Callable<Boolean>{
	
	private static final Logger log = LogManager.getLogger(WorkerAlphaVantageStocksEOD.class);
	
	private final String stock;
	private final AlphaVantageConnector avc;
	private final Influxdb idb;
	private final String database;
	
	public WorkerAlphaVantageStocksID(String stock, String database, AlphaVantageConnector avc,Influxdb idb) {
		this.stock = stock;
		this.avc = avc;
		this.idb = idb;
		this.database = database;
	}

	@Override
	public Boolean call() throws Exception {
		String apiRes = avc.call(Function.TIME_SERIES_INTRADAY_EXTENDED,new Symbol(stock),Interval.ONE_MIN,Slice.YEAR1MONTH1,OutputSize.FULL,OutputType.CSV);
		apiRes = apiRes.replaceFirst("timestamp", "time");
		idb.update(database, stock,StockIDTimeSeriesPointInfluxdb.class ,CSVUtils.parseCsv2Map(apiRes,true,',','"'));
		return true;
	}
	
}
