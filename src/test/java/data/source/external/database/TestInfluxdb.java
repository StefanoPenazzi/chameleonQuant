/**
 * 
 */
package data.source.external.database;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import data.source.external.database.influxdb.Influxdb;
import data.source.external.database.influxdb.mirrors.alphaVantage.StockTimeSeriesPoint;
import data.source.external.web.connector.AlphaVantageConnector;
import data.source.external.web.parameter.alphaVantage.functions.Function;
import data.source.external.web.parameter.alphaVantage.intradaytimeseries.Interval;
import data.source.external.web.parameter.alphaVantage.intradaytimeseries.Slice;
import data.source.external.web.parameter.alphaVantage.output.OutputSize;
import data.source.external.web.parameter.alphaVantage.output.OutputType;
import data.source.external.web.parameter.alphaVantage.symbols.Symbol;
import data.source.utils.IO.TxtUtils;

import org.influxdb.annotation.Measurement;

/**
 * @author stefanopenazzi
 *
 */
class TestInfluxdb {

	@Test
	void testWriteBatchStocks() {
		Influxdb idb = new Influxdb();
		final String serverURL = "http://127.0.0.1:7086", username = "stefanopenazzi", password = "korky1987";
		String[] dbCon = {serverURL,username,password};
		//the server must be on(service influxdb start) otherwise the connection will not be successful
		idb.connect(dbCon);
		String csvFile = "/home/stefanopenazzi/git/chameleonQuant/output/IBM_TIME_SERIES_INTRADAY_EXTENDED_ONE_MIN_YEAR1MONTH1_FULL_JSON.csv";
		Object[] options = {true,',','"'};
		idb.writingBatchFromCsvFile("US_STOCKS_TIME_SERIES_INTRADAY_1MIN", "IBM", csvFile ,StockTimeSeriesPoint.class ,options);
		idb.close();
	}
	
	@Test
	void testWriteBatchForex() {
		Influxdb idb = new Influxdb();
		final String serverURL = "http://127.0.0.1:7086", username = "stefanopenazzi", password = "korky1987";
		String[] dbCon = {serverURL,username,password};
		//the server must be on(service influxdb start) otherwise the connection will not be successful
		idb.connect(dbCon);
		String csvFile = "/home/stefanopenazzi/git/chameleonQuant/output/EUR-USD_FX_DAILY_FULL_CSV.csv";
		Object[] options = {true,',','"'};
		idb.writingBatchFromCsvFile("FX_DAILY", "EUR_USD", csvFile , StockTimeSeriesPoint.class ,options);
		idb.close();
	}
	
	@Test
	void testPipeLineAlphaVantBatchStocks() {
		
		String stock = "AAPL";
		
		String csvPath = System.getProperty("user.dir")+"/output/"+stock+"_"+Function.TIME_SERIES_INTRADAY_EXTENDED+"_"+Interval.ONE_MIN+"_"+Slice.YEAR1MONTH1+"_"+OutputSize.FULL+"_"+OutputType.CSV+".csv";
		
		AlphaVantageConnector avc = new AlphaVantageConnector("84AHX76LXVJ25F65",10000);
		String test = avc.call(Function.TIME_SERIES_INTRADAY_EXTENDED,new Symbol(stock),Interval.ONE_MIN,Slice.YEAR1MONTH1,OutputSize.FULL,OutputType.CSV);
		try {
			TxtUtils.stringToFile(test,csvPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Influxdb idb = new Influxdb();
		final String serverURL = "http://127.0.0.1:7086", username = "stefanopenazzi", password = "korky1987";
		String[] dbCon = {serverURL,username,password};
		//the server must be on(service influxdb start) otherwise the connection will not be successful
		idb.connect(dbCon);
		Object[] options = {true,',','"'};
		idb.writingBatchFromCsvFile("US_STOCKS_TIME_SERIES_INTRADAY_1MIN", stock, csvPath ,StockTimeSeriesPoint.class ,options);
		idb.close();
	}
	
	@Test
	void testQuerySelect() {
		Influxdb idb = new Influxdb();
		final String serverURL = "http://127.0.0.1:7086", username = "stefanopenazzi", password = "korky1987";
		String[] dbCon = {serverURL,username,password};
		//the server must be on(service influxdb start) otherwise the connection will not be successful
		idb.connect(dbCon);
		List<StockTimeSeriesPoint> sts = (List<StockTimeSeriesPoint>) idb.performQuery("SELECT * FROM IBM", "US_STOCKS_TIME_SERIES_DAILY", StockTimeSeriesPoint.class );
		idb.close();
	}
	
	@Test
	void testQueryGroupBy() {
		
		String stock = "AAPL";
		
		Influxdb idb = new Influxdb();
		final String serverURL = "http://127.0.0.1:7086", username = "stefanopenazzi", password = "korky1987";
		String[] dbCon = {serverURL,username,password};
		//the server must be on(service influxdb start) otherwise the connection will not be successful
		idb.connect(dbCon);
		//the StockTimeSeries class has an annotation refering the name of the measure in the db.
		//this has to be changed accordingly with the query
		
		 
		    
		     // Get the annotation on Test
		    Measurement stss =  StockTimeSeriesPoint.class.getAnnotation(Measurement.class);
		    if (stss == null) {
		      throw new RuntimeException("please add @Measurement for StockTimeSeries");
		    }
		     // Get the proxy processor
		    InvocationHandler invocationHandler = Proxy.getInvocationHandler(stss);
		     // Get the private memberValues ​​property
		    Field f = null;
			try {
				f = invocationHandler.getClass().getDeclaredField("memberValues");
			} catch (NoSuchFieldException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    f.setAccessible(true);
		     // Get the attribute map of the instance
		    Map<String, Object> memberValues = null;
			try {
				memberValues = (Map<String, Object>) f.get(invocationHandler);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     // Modify the attribute value
		    memberValues.put("name", stock);
		    
		    
		
		List<StockTimeSeriesPoint> sts = (List<StockTimeSeriesPoint>) idb.performQuery("SELECT first(open) AS open, last(close) AS close, max(high) AS high, min(low) AS low, sum(volume) AS volume FROM "+stock+" WHERE time>'2020-08-19 09:30:00'  GROUP BY time(8h)", "US_STOCKS_TIME_SERIES_INTRADAY_1MIN", StockTimeSeriesPoint.class );
		idb.close();
	}

}
