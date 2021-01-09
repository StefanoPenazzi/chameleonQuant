/**
 * 
 */
package indicators.volatility;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.inject.Key;

import controller.Controller;
import data.source.external.database.influxdb.TimeSeriesId;
import data.source.external.database.influxdb.TimeSeriesRequestInfluxdb;
import data.source.external.database.influxdb.mirrors.StockEODTimeSeriesPointInfluxdb;
import data.source.internal.dataset.DatasetImpl;
import data.source.internal.timeseries.standard.TimeSeriesFactoryImpl;
import indicators.movingAverage.ExponentialMovingAverage;
import indicators.movingAverage.MACD;

/**
 * @author stefanopenazzi
 *
 */
class TestVolatility {

	@Test
	void testTrueRange() throws Exception {
		 List<String> stocks = Arrays.asList("ACER");
			
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		 Instant startInstant = (sdf.parse("2020/10/19 00:00:00")).toInstant();
		 Instant endInstant = null;
		 String market = "NASDAQ_EOD";
		 String inter = "1d";
		
		 Controller controller = new Controller();
		 controller.run();
		 
		 TimeSeriesFactoryImpl<StockEODTimeSeriesPointInfluxdb> itsf  = controller.getInjector().getInstance(new Key<TimeSeriesFactoryImpl<StockEODTimeSeriesPointInfluxdb>>() {});
		 TimeSeriesRequestInfluxdb itsq = new TimeSeriesRequestInfluxdb();
		 
		 DatasetImpl dts = new DatasetImpl();
		 
		 for(String stock: stocks) {
			 TimeSeriesId  query = new TimeSeriesId (startInstant,endInstant,market,stock,inter,StockEODTimeSeriesPointInfluxdb.class);
			 dts.addTimeSeries(itsf.createTimeSeriesQueryRequest(new ArrayList<String>(){{add("NULL_INFLUXDB");}},itsq,query));
		 }
		 
		 TrueRange tr = new TrueRange(dts,new TimeSeriesId (startInstant,endInstant,market,"ACER",inter,StockEODTimeSeriesPointInfluxdb.class));
         DatasetImpl ds = tr.create();
		 //InternalTimeSeriesI<? extends InternalTimeSeriesPoint> its = dts.getTimeSeries(new InternalStockTimeSeriesQueryInfluxdb (startDate,endDate,market,"AAPL",inter));
		
		System.out.println();
	}
	
	@Test
	void testAverageTrueRange() throws Exception {
		 List<String> stocks = Arrays.asList("ACER");
			
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		 Instant startInstant = (sdf.parse("2020/10/19 00:00:00")).toInstant();
		 Instant endInstant = null;
		 String market = "NASDAQ_EOD";
		 String inter = "1d";
		
		 Controller controller = new Controller();
		 controller.run();
		 
		 TimeSeriesFactoryImpl<StockEODTimeSeriesPointInfluxdb> itsf  = controller.getInjector().getInstance(new Key<TimeSeriesFactoryImpl<StockEODTimeSeriesPointInfluxdb>>() {});
		 TimeSeriesRequestInfluxdb itsq = new TimeSeriesRequestInfluxdb();
		 
		 DatasetImpl dts = new DatasetImpl();
		 
		 for(String stock: stocks) {
			 TimeSeriesId  query = new TimeSeriesId (startInstant,endInstant,market,stock,inter,StockEODTimeSeriesPointInfluxdb.class);
			 dts.addTimeSeries(itsf.createTimeSeriesQueryRequest(new ArrayList<String>(){{add("NULL_INFLUXDB");}},itsq,query));
		 }
		 
		 AverageTrueRange atr = new AverageTrueRange(dts,new TimeSeriesId (startInstant,endInstant,market,"ACER",inter,StockEODTimeSeriesPointInfluxdb.class),14);
         DatasetImpl ds = atr.create();
		 //InternalTimeSeriesI<? extends InternalTimeSeriesPoint> its = dts.getTimeSeries(new InternalStockTimeSeriesQueryInfluxdb (startDate,endDate,market,"AAPL",inter));
		
		System.out.println();
	}
	
	@Test
	void testMACD() throws Exception {
		 List<String> stocks = Arrays.asList("ACER");
			
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		 Instant startInstant = (sdf.parse("2020/10/19 00:00:00")).toInstant();
		 Instant endInstant = null;
		 String market = "NASDAQ_EOD";
		 String inter = "1d";
		
		 Controller controller = new Controller();
		 controller.run();
		 
		 TimeSeriesFactoryImpl<StockEODTimeSeriesPointInfluxdb> itsf  = controller.getInjector().getInstance(new Key<TimeSeriesFactoryImpl<StockEODTimeSeriesPointInfluxdb>>() {});
		 TimeSeriesRequestInfluxdb itsq = new TimeSeriesRequestInfluxdb();
		 
		 DatasetImpl dts = new DatasetImpl();
		 
		 for(String stock: stocks) {
			 TimeSeriesId  query = new TimeSeriesId (startInstant,endInstant,market,stock,inter,StockEODTimeSeriesPointInfluxdb.class);
			 dts.addTimeSeries(itsf.createTimeSeriesQueryRequest(new ArrayList<String>(){{add("NULL_INFLUXDB");}},itsq,query));
		 }
		 
		 MACD macd = new MACD(dts,new TimeSeriesId (startInstant,endInstant,market,"ACER",inter,StockEODTimeSeriesPointInfluxdb.class),"close", 12,26,9);
         DatasetImpl ds = macd.create();
		 //InternalTimeSeriesI<? extends InternalTimeSeriesPoint> its = dts.getTimeSeries(new InternalStockTimeSeriesQueryInfluxdb (startDate,endDate,market,"AAPL",inter));
		
		System.out.println();
	}


}
