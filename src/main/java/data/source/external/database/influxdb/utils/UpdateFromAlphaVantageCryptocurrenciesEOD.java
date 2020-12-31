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
import data.source.external.database.influxdb.mirrors.alphaVantage.FOREXEODTimeSeriesPointInfluxdb;
import data.source.external.web.connector.alphaVantage.AlphaVantageConnector;
import data.source.external.web.parameter.alphaVantage.currencies.FromCurrency;
import data.source.external.web.parameter.alphaVantage.currencies.ToCurrency;
import data.source.external.web.parameter.alphaVantage.functions.Function;
import data.source.external.web.parameter.alphaVantage.markets.Market;
import data.source.external.web.parameter.alphaVantage.output.OutputSize;
import data.source.external.web.parameter.alphaVantage.output.OutputType;
import data.source.external.web.parameter.alphaVantage.symbols.Symbol;
import data.source.utils.IO.TxtUtils;

/**
 * @author stefanopenazzi
 *
 */
public class UpdateFromAlphaVantageCryptocurrenciesEOD extends UpdateFromAlphaVantageAbstract{

	/**
	 * @param maxReqPerMin
	 * @param maxReqPerDay
	 * @param nThreads
	 */
	public UpdateFromAlphaVantageCryptocurrenciesEOD(int maxReqPerMin, int maxReqPerDay, int nThreads) {
		super(maxReqPerMin, maxReqPerDay, nThreads);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Callable<Boolean> getWorker(String serie, String database) {
		return new WorkerAlphaVantageCryptocurrenciesEOD(serie,database,this.getAlphaVantageConnector(),this.getInfluxdb());
	}

}

class WorkerAlphaVantageCryptocurrenciesEOD implements Callable<Boolean>{
	
	private static final Logger log = LogManager.getLogger(WorkerAlphaVantageCryptocurrenciesEOD.class);
	private final String serie;
	private final AlphaVantageConnector avc;
	private final Influxdb idb;
	private final String database;
	
	public WorkerAlphaVantageCryptocurrenciesEOD(String serie, String database, AlphaVantageConnector avc,Influxdb idb) {
		//TODO check the serie if fit with the std
		this.serie = serie;
		this.avc = avc;
		this.idb = idb;
		this.database = database;
	}

	@Override
	public Boolean call() throws Exception {
		String[] parts = serie.split("-");
		String fromSymbol = parts[0];
		String toMarket = parts[1];
		String csvPath = System.getProperty("user.dir")+"/output/"+this.serie+"_"+Function.TIME_SERIES_DAILY+"_"+OutputSize.FULL+".csv";
		
		String test = avc.call(Function.DIGITAL_CURRENCY_DAILY,new Symbol(fromSymbol),new Market(toMarket),OutputType.CSV);
		test = test.replaceFirst("timestamp", "time");
		try {
			TxtUtils.stringToFile(test,csvPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object[] options = {true,',','"'};
		idb.writingBatchFromCsvFile(database, serie, csvPath ,FOREXEODTimeSeriesPointInfluxdb.class ,options);
		
		Files.deleteIfExists(Paths.get(csvPath)); 
		
		return true;
	}
	
}
