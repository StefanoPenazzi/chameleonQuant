/**
 * 
 */
package data.source.internal.dataset.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;

import controller.Controller;
import data.source.external.database.influxdb.TimeSeriesId;
import data.source.external.database.influxdb.TimeSeriesRequestInfluxdb;
import data.source.external.database.influxdb.mirrors.StockEODTimeSeriesPointInfluxdb;
import data.source.external.financialdatavendors.alphavantage.mirrors.StockEODTimeSeriesPointAlphaVantage;
import data.source.internal.dataset.DatasetImpl;
import data.source.internal.dataset.DatasetFactoryI;
import data.source.internal.dataset.DatasetFactoryImpl;
import data.source.internal.dataset.DatasetI;
import data.source.internal.timeseries.TimeSeriesI;
import data.source.internal.timeseries.point.TimeSeriesPointI;
import data.source.internal.timeseries.standard.TimeSeriesFactoryImpl;


/**
 * @author stefanopenazzi
 *
 */
class TestDataset {

	@Test
	void testDataset() throws ParseException, InstantiationException, IllegalAccessException {
		List<String> stocks = Arrays.asList("AAPL","AMZN","TSLA","FB","C");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		
		 Instant startInstant = (sdf.parse("2020/10/19 00:00:00")).toInstant();
		 Instant endInstant = null;
		 String market = "US_STOCKS_TIME_SERIES_INTRADAY_1MIN";
		 String inter = "8h";
		
		 Injector injector = Guice.createInjector();
		 
		 TimeSeriesFactoryImpl<StockEODTimeSeriesPointInfluxdb> itsf  = injector.getInstance(new Key<TimeSeriesFactoryImpl<StockEODTimeSeriesPointInfluxdb>>() {});
		 TimeSeriesRequestInfluxdb itsq = new TimeSeriesRequestInfluxdb();
		 
		 DatasetImpl dts = new DatasetImpl();
		 
		 for(String stock: stocks) {
			 TimeSeriesId  query = new TimeSeriesId (startInstant,endInstant,market,stock,inter,StockEODTimeSeriesPointInfluxdb.class);
			 dts.addTimeSeries(itsf.createTimeSeriesQueryRequest(new ArrayList<String>(){{add("NULL_INFLUXDB");}},itsq,query));
		 }
		
		System.out.println();
	}
	
	@Test
	void testDatasetController() throws ParseException, InstantiationException, IllegalAccessException {
		
		
		List<String> stocks = Arrays.asList("AAPL","AMZN","TSLA","FB","C");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		
		 Instant startInstant = (sdf.parse("2020/10/19 00:00:00")).toInstant();
		 Instant endInstant = null;
		 String market = "US_STOCKS_TIME_SERIES_INTRADAY_1MIN";
		 String inter = "8h";
		
		 Controller controller = new Controller();
		 controller.run();
		 
		 TimeSeriesFactoryImpl<StockEODTimeSeriesPointInfluxdb> itsf  = controller.getInjector().getInstance(new Key<TimeSeriesFactoryImpl<StockEODTimeSeriesPointInfluxdb>>() {});
		 TimeSeriesRequestInfluxdb itsq = new TimeSeriesRequestInfluxdb();
		 
		 DatasetImpl dts = new DatasetImpl();
		 
		 for(String stock: stocks) {
			 TimeSeriesId  query = new TimeSeriesId (startInstant,endInstant,market,stock,inter,StockEODTimeSeriesPointInfluxdb.class);
			 dts.addTimeSeries(itsf.createTimeSeriesQueryRequest(new ArrayList<String>(){{add("NULL_INFLUXDB");}},itsq,query));
		 }
		 
		 TimeSeriesI<? extends TimeSeriesPointI> its = dts.getTimeSeries(new TimeSeriesId (startInstant,endInstant,market,"AAPL",inter,StockEODTimeSeriesPointInfluxdb.class));
		
		System.out.println();
	}
	
	@Test
	void testDatasetInfluxFactory() throws ParseException {
		
         List<String> stocks = Arrays.asList("AACG","AACQ","AACQU","AACQW","AAL");
		
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Instant startInstant = (sdf.parse("2020-10-19 00:00:00")).toInstant();
		 Instant endInstant = null;
		 String market = "NASDAQ_EOD";
		 String inter = "1d";
		 
		  List<TimeSeriesId> listQueries = new ArrayList<>();
		 
		 for(String stock: stocks) {
			 listQueries.add(new TimeSeriesId (startInstant,endInstant,market,stock,inter,StockEODTimeSeriesPointInfluxdb.class));
		 }
		
		 Controller.run();
		 DatasetI dts = Controller.getDatasetFactory().create(listQueries);
		 TimeSeriesI<? extends TimeSeriesPointI> its = dts.getTimeSeries(new TimeSeriesId (startInstant,endInstant,market,"AACG",inter,StockEODTimeSeriesPointInfluxdb.class));
		 System.out.println();
		
	}
	
	@Test
	void testDatasetAlphaVantageFactory() throws ParseException {
		
         List<String> stocks = Arrays.asList("AACG");
		
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Instant startInstant = (sdf.parse("2020-10-19 00:00:00")).toInstant();
		 Instant endInstant = null;
		 String market = "NASDAQ_EOD";
		 String inter = "1d";
		 
		  List<TimeSeriesId> listQueries = new ArrayList<>();
		 
		 for(String stock: stocks) {
			 listQueries.add(new TimeSeriesId (startInstant,endInstant,market,stock,inter,StockEODTimeSeriesPointAlphaVantage.class));
		 }
		
		 Controller.run();
		 DatasetI dts = Controller.getDatasetFactory().create(listQueries);
		 TimeSeriesI<? extends TimeSeriesPointI> its = dts.getTimeSeries(new TimeSeriesId (startInstant,endInstant,market,"AACG",inter,StockEODTimeSeriesPointAlphaVantage.class));
		 System.out.println();
		
	}

}
