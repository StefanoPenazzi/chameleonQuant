/**
 * 
 */
package data.source.external.database.influxdb.utils.update;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import data.source.external.database.influxdb.Influxdb;
import data.source.external.database.influxdb.mirrors.FOREXEODTimeSeriesPointInfluxdb;
import data.source.external.database.influxdb.mirrors.StockEODTimeSeriesPointInfluxdb;
import data.source.external.financialdatavendors.alphavantage.AlphaVantageConnector;
import data.source.external.financialdatavendors.alphavantage.parameters.currencies.FromCurrency;
import data.source.external.financialdatavendors.alphavantage.parameters.currencies.ToCurrency;
import data.source.external.financialdatavendors.alphavantage.parameters.functions.Function;
import data.source.external.financialdatavendors.alphavantage.parameters.output.OutputSize;
import data.source.external.financialdatavendors.alphavantage.parameters.output.OutputType;
import data.source.external.financialdatavendors.alphavantage.parameters.symbols.Symbol;
import data.source.utils.IO.CSVUtils;
import data.source.utils.IO.TxtUtils;

/**
 * @author stefanopenazzi
 *
 */
public class UpdateFromAlphaVantageFOREXEOD extends UpdateFromAlphaVantageAbstract{

	/**
	 * @param maxReqPerMin
	 * @param maxReqPerDay
	 * @param nThreads
	 */
	public UpdateFromAlphaVantageFOREXEOD(int maxReqPerMin, int maxReqPerDay, int nThreads) {
		super(maxReqPerMin, maxReqPerDay, nThreads);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Callable<Boolean> getWorker(String serie, String database,Influxdb idb) {
		return new WorkerAlphaVantageFOREXEOD(serie,database,this.getAlphaVantageConnector(),idb);
	}

}

class WorkerAlphaVantageFOREXEOD implements Callable<Boolean>{
	
	private static final Logger log = LogManager.getLogger(WorkerAlphaVantageFOREXEOD.class);
	private final String serie;
	private final AlphaVantageConnector avc;
	private final Influxdb idb;
	private final String database;
	
	public WorkerAlphaVantageFOREXEOD(String serie, String database, AlphaVantageConnector avc,Influxdb idb) {
		//TODO check the serie if fit with the std
		this.serie = serie;
		this.avc = avc;
		this.idb = idb;
		this.database = database;
	}

	@Override
	public Boolean call() throws Exception {
		String[] parts = serie.split("-");
		String fromCurrency = parts[0];
		String toCurrency = parts[1];
		String apiRes = avc.call(Function.FX_DAILY,new FromCurrency(fromCurrency),new ToCurrency(toCurrency), OutputSize.FULL,OutputType.CSV);
		apiRes = apiRes.replaceFirst("timestamp", "time");
		idb.update(database, serie,FOREXEODTimeSeriesPointInfluxdb.class ,CSVUtils.parseCsv2Map(apiRes,true,',','"'));
		return true;
	}
	
}