/**
 * 
 */
package data.source.external.database;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import data.source.external.database.influxdb.Influxdb;
import data.source.external.database.influxdb.mirrors.alphaVantage.StockEODTimeSeriesPointInfluxdb;
import data.source.external.database.influxdb.utils.UpdateFromAlphaVantageFOREXEOD;
import data.source.external.database.influxdb.utils.UpdateFromAlphaVantageStocksEOD;
import data.source.external.web.connector.alphaVantage.AlphaVantageConnector;
import data.source.external.web.parameter.alphaVantage.functions.Function;
import data.source.external.web.parameter.alphaVantage.intradaytimeseries.Interval;
import data.source.external.web.parameter.alphaVantage.intradaytimeseries.Slice;
import data.source.external.web.parameter.alphaVantage.output.OutputSize;
import data.source.external.web.parameter.alphaVantage.output.OutputType;
import data.source.external.web.parameter.alphaVantage.symbols.Symbol;
import data.source.utils.IO.TxtUtils;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;

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
		idb.writingBatchFromCsvFile("US_STOCKS_TIME_SERIES_INTRADAY_1MIN", "IBM", csvFile ,StockEODTimeSeriesPointInfluxdb.class ,options);
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
		idb.writingBatchFromCsvFile("FX_DAILY", "EUR_USD", csvFile , StockEODTimeSeriesPointInfluxdb.class ,options);
		idb.close();
	}
	
	@Test
	void testPipeLineAlphaVantBatchStocks() {
		
		String stock = "AMZN";
		
		String csvPath = System.getProperty("user.dir")+"/output/"+stock+"_"+Function.TIME_SERIES_INTRADAY_EXTENDED+"_"+Interval.ONE_MIN+"_"+Slice.YEAR1MONTH1+"_"+OutputSize.FULL+"_"+OutputType.CSV+".csv";
		
		AlphaVantageConnector avc = new AlphaVantageConnector(10000);
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
		idb.writingBatchFromCsvFile("NASDAQ_ID", stock, csvPath ,StockEODTimeSeriesPointInfluxdb.class ,options);
		idb.close();
	}
	
	@Test
	void testFormatdate() {
		//SimpleDateFormat formatIntraday = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
		//formatIntraday.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    LocalDateTime dateTime = LocalDateTime.parse("2020-12-24 17:00:00", formatter);
	    OffsetDateTime utcDateTime = dateTime.atOffset(ZoneOffset.UTC);
		Long date = date = utcDateTime.toInstant().toEpochMilli();
		System.out.println();
	}
	
	@Test
	void testPipeLineAlphaVantMultipleBatchStocks() {
		
		List<String> stocks = Arrays.asList("AAPL","AMZN","TSLA","FB","BRK-A","C");
		
		for (String stock: stocks) {
			
			String csvPath = System.getProperty("user.dir")+"/output/"+stock+"_"+Function.TIME_SERIES_INTRADAY_EXTENDED+"_"+Interval.ONE_MIN+"_"+Slice.YEAR1MONTH1+"_"+OutputSize.FULL+"_"+OutputType.CSV+".csv";
			
			AlphaVantageConnector avc = new AlphaVantageConnector(60000);
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
			idb.writingBatchFromCsvFile("US_STOCKS_TIME_SERIES_INTRADAY_1MIN", stock, csvPath ,StockEODTimeSeriesPointInfluxdb.class ,options);
			idb.close();
		}

	}
	
	@Test
	void testQuerySelect() {
		Influxdb idb = new Influxdb();
		final String serverURL = "http://127.0.0.1:7086", username = "stefanopenazzi", password = "korky1987";
		String[] dbCon = {serverURL,username,password};
		//the server must be on(service influxdb start) otherwise the connection will not be successful
		idb.connect(dbCon);
		List<StockEODTimeSeriesPointInfluxdb> sts = (List<StockEODTimeSeriesPointInfluxdb>) idb.performQuery("SELECT * FROM IBM", "US_STOCKS_TIME_SERIES_DAILY", StockEODTimeSeriesPointInfluxdb.class );
		idb.close();
	}
	
	
	@Test
	void testQueryGroupByPOJO() {
        String stock = "IBM";
		
		Influxdb idb = new Influxdb();
		final String serverURL = "http://127.0.0.1:7086", username = "stefanopenazzi", password = "korky1987";
		String[] dbCon = {serverURL,username,password};
		//the server must be on(service influxdb start) otherwise the connection will not be successful
		idb.connect(dbCon);
		//the StockTimeSeries class has an annotation refering the name of the measure in the db.
		//this has to be changed accordingly with the query
		
		//Query data from InfluxDB
		Query query = new Query("SELECT first(open) AS open, last(close) AS close, max(high) AS high, min(low) AS low, sum(volume) AS volume FROM "+stock+" WHERE time>'2020-10-19 09:30:00'  GROUP BY time(8h)", "US_STOCKS_TIME_SERIES_INTRADAY_1MIN");
		QueryResult queryResult = idb.getInfluxDB().query(query);
		//Convert QueryResult to POJO
		String measurement = stock;
		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		List<StockEODTimeSeriesPointInfluxdb> results = resultMapper.toPOJO(queryResult, StockEODTimeSeriesPointInfluxdb.class, measurement );
		    
		idb.close();
	}
	
	@Test
	void testUpdateAlphaVantage() {
        List<String> stocksList = new ArrayList<>();
		
		//stocksList.add("ACER");
		//stocksList.add("ACET");
		//stocksList.add("ACEV");
		//stocksList.add("ACEVU");
		//stocksList.add("ACEVW");
		//stocksList.add("ACGL");
		//stocksList.add("ACGLO");
		//stocksList.add("ACGLP");
		//stocksList.add("ACHC");
		//stocksList.add("ACHV");
		//stocksList.add("ACIA");
		stocksList.add("AAL");
		
		UpdateFromAlphaVantageStocksEOD upf = new UpdateFromAlphaVantageStocksEOD(5, 500, 5);
		upf.run(stocksList, "NASDAQ_EOD");
	}
	
	@Test
	void testFOREXUpdateAlphaVantage() {
        List<String> forexList = new ArrayList<>();
		forexList.add("EUR-USD");
		UpdateFromAlphaVantageFOREXEOD upf = new UpdateFromAlphaVantageFOREXEOD(5, 500, 5);
		upf.run(forexList, "FOREX_EOD");
	}

}
