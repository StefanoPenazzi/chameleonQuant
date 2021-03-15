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
import data.source.external.database.influxdb.mirrors.StockEODTimeSeriesPointInfluxdb;
import data.source.external.database.influxdb.utils.queries.StructureQuery;
import data.source.external.database.influxdb.utils.update.UpdateFromAlphaVantageFOREXEOD;
import data.source.external.database.influxdb.utils.update.UpdateFromAlphaVantageStocksEOD;
import data.source.external.database.influxdb.utils.update.UpdateFromAlphaVantageStocksID;
import data.source.external.financialdatavendors.alphavantage.AlphaVantageConnector;
import data.source.external.financialdatavendors.alphavantage.parameters.functions.Function;
import data.source.external.financialdatavendors.alphavantage.parameters.intradaytimeseries.Interval;
import data.source.external.financialdatavendors.alphavantage.parameters.intradaytimeseries.Slice;
import data.source.external.financialdatavendors.alphavantage.parameters.output.OutputSize;
import data.source.external.financialdatavendors.alphavantage.parameters.output.OutputType;
import data.source.external.financialdatavendors.alphavantage.parameters.symbols.Symbol;
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
	void testUpdateAlphaVantageEOD() {
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
		//stocksList.add("AAL");
		
		stocksList.add("BAND");
		stocksList.add("BANF");	 
		stocksList.add("BANFP");	 
		stocksList.add("BAN");
		stocksList.add("BANX"); 
		stocksList.add("BASI"); 
		stocksList.add("BATRA");	 
		stocksList.add("BATRK"); 
		stocksList.add("BBB");
		stocksList.add("BBCP"); 
		stocksList.add("BBGI");
		stocksList.add("BBH");
		stocksList.add("BBI");	 
		stocksList.add("BBIG"); 
		stocksList.add("BBIO");	 
		stocksList.add("BBQ");
		stocksList.add("BBSI");	 
		stocksList.add("BCAB");
		stocksList.add("BCBP");
		
		UpdateFromAlphaVantageStocksEOD upf = new UpdateFromAlphaVantageStocksEOD(5, 500, 5);
		upf.run(stocksList, "NASDAQ_EOD");
	}
	
	@Test
	void testFOREXUpdateAlphaVantageEOD() {
        List<String> forexList = new ArrayList<>();
		forexList.add("EUR-USD");
		UpdateFromAlphaVantageFOREXEOD upf = new UpdateFromAlphaVantageFOREXEOD(5, 500, 5);
		upf.run(forexList, "FOREX_EOD");
	}
	
	@Test
	void testUpdateAlphaVantageID() {
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
		//stocksList.add("AAL");
		
		stocksList.add("BAND");
		stocksList.add("BANF");	 
		stocksList.add("BANFP");	 
		stocksList.add("BAN");
		stocksList.add("BANX"); 
		stocksList.add("BASI"); 
		stocksList.add("BATRA");	 
		stocksList.add("BATRK"); 
		stocksList.add("BBB");
		stocksList.add("BBCP"); 
		stocksList.add("BBGI");
		stocksList.add("BBH");
		stocksList.add("BBI");	 
		stocksList.add("BBIG"); 
		stocksList.add("BBIO");	 
		stocksList.add("BBQ");
		stocksList.add("BBSI");	 
		stocksList.add("BCAB");
		stocksList.add("BCBP");
		
		UpdateFromAlphaVantageStocksID upf = new UpdateFromAlphaVantageStocksID(5, 500, 5);
		upf.run(stocksList, "NASDAQ_ID");
	}
	
	
	@Test
	void testStructureQuery() {
		List<String> s = new ArrayList<String>(){{add("NASDAQ_EOD");add("FOREX_EOD");}} ;
		StructureQuery.getDatabaseMap();
	}
	
	@Test
	void testPingInfluxdb() {
		Influxdb idb = new Influxdb();
		idb.connect();
		idb.ping();
		idb.close();
	}
	
	

}
