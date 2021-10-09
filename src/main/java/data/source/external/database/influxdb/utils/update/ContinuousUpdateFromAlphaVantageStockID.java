package data.source.external.database.influxdb.utils.update;

import java.io.FileNotFoundException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import data.source.external.database.influxdb.Influxdb;
import data.source.external.database.influxdb.mirrors.StockIDTimeSeriesPointInfluxdb;
import data.source.external.financialdatavendors.alphavantage.AlphaVantageConnector;
import data.source.external.financialdatavendors.alphavantage.parameters.functions.Function;
import data.source.external.financialdatavendors.alphavantage.parameters.intradaytimeseries.Interval;
import data.source.external.financialdatavendors.alphavantage.parameters.output.OutputSize;
import data.source.external.financialdatavendors.alphavantage.parameters.output.OutputType;
import data.source.external.financialdatavendors.alphavantage.parameters.symbols.Symbol;
import data.source.utils.IO.CSVUtils;

public class ContinuousUpdateFromAlphaVantageStockID extends UpdateFromAlphaVantageAbstract {

	private final Integer interval; 
	public ContinuousUpdateFromAlphaVantageStockID(Integer interval) {
		super();
		this.interval = interval;
	}

	@Override
	public Callable<Boolean> getWorker(String serie, String database,Influxdb idb) {
		return new ContinuousWorkerAlphaVantageStocksID(serie,database,this.getAlphaVantageConnector(),idb,interval);
	}
}

class ContinuousWorkerAlphaVantageStocksID implements Callable<Boolean>{
	
	private static final Logger log = LogManager.getLogger(ContinuousWorkerAlphaVantageStocksID.class);
	
	private final String stock;
	private final AlphaVantageConnector avc;
	private final Influxdb idb;
	private final String database;
	private final Integer interval;
	
	public ContinuousWorkerAlphaVantageStocksID(String stock, String database, AlphaVantageConnector avc,Influxdb idb,Integer interval) {
		this.stock = stock;
		this.avc = avc;
		this.idb = idb;
		this.database = database;
		this.interval = interval;
	}

	@Override
	public Boolean call() throws Exception {
		while(true) {
			String apiRes = avc.call(Function.TIME_SERIES_INTRADAY_EXTENDED,new Symbol(stock),Interval.ONE_MIN,OutputSize.COMPACT,OutputType.CSV);
			apiRes = apiRes.replaceFirst("timestamp", "time");
			try {
				idb.update(database, stock,StockIDTimeSeriesPointInfluxdb.class ,CSVUtils.parseCsv2Map(apiRes,true,',','"'));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				TimeUnit.MILLISECONDS.sleep(interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
