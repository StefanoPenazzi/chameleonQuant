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
import data.source.external.database.influxdb.InternalStockTimeSeriesQueryInfluxdb;
import data.source.external.database.influxdb.InternalTimeSeriesQueryRequestInfluxdb;
import data.source.external.database.influxdb.mirrors.alphaVantage.StockEODTimeSeriesPointInfluxdb;
import data.source.internal.dataset.timeseries.InternalTimeSeriesI;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPointI;
import data.source.internal.dataset.timeseries.standard.InternalTimeSeriesFactoryImpl;


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
		 
		 InternalTimeSeriesFactoryImpl<StockEODTimeSeriesPointInfluxdb> itsf  = injector.getInstance(new Key<InternalTimeSeriesFactoryImpl<StockEODTimeSeriesPointInfluxdb>>() {});
		 InternalTimeSeriesQueryRequestInfluxdb<StockEODTimeSeriesPointInfluxdb> itsq = new InternalTimeSeriesQueryRequestInfluxdb<StockEODTimeSeriesPointInfluxdb>(StockEODTimeSeriesPointInfluxdb.class);
		 
		 DatasetImpl dts = new DatasetImpl();
		 
		 for(String stock: stocks) {
			 InternalStockTimeSeriesQueryInfluxdb  query = new InternalStockTimeSeriesQueryInfluxdb (startInstant,endInstant,market,stock,inter);
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
		 
		 InternalTimeSeriesFactoryImpl<StockEODTimeSeriesPointInfluxdb> itsf  = controller.getInjector().getInstance(new Key<InternalTimeSeriesFactoryImpl<StockEODTimeSeriesPointInfluxdb>>() {});
		 InternalTimeSeriesQueryRequestInfluxdb<StockEODTimeSeriesPointInfluxdb> itsq = new InternalTimeSeriesQueryRequestInfluxdb<StockEODTimeSeriesPointInfluxdb>(StockEODTimeSeriesPointInfluxdb.class);
		 
		 DatasetImpl dts = new DatasetImpl();
		 
		 for(String stock: stocks) {
			 InternalStockTimeSeriesQueryInfluxdb  query = new InternalStockTimeSeriesQueryInfluxdb (startInstant,endInstant,market,stock,inter);
			 dts.addTimeSeries(itsf.createTimeSeriesQueryRequest(new ArrayList<String>(){{add("NULL_INFLUXDB");}},itsq,query));
		 }
		 
		 InternalTimeSeriesI<? extends InternalTimeSeriesPointI> its = dts.getTimeSeries(new InternalStockTimeSeriesQueryInfluxdb (startInstant,endInstant,market,"AAPL",inter));
		
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
		 
		  List<InternalStockTimeSeriesQueryInfluxdb> listQueries = new ArrayList<>();
		 
		 for(String stock: stocks) {
			 listQueries.add(new InternalStockTimeSeriesQueryInfluxdb (startInstant,endInstant,market,stock,inter));
		 }
		
		 Controller.run();
		
		 DatasetInfluxFactory dif = new DatasetInfluxFactory(listQueries);
		 DatasetImpl dts = (DatasetImpl) dif.create();
		 
		 InternalTimeSeriesI<? extends InternalTimeSeriesPointI> its = dts.getTimeSeries(new InternalStockTimeSeriesQueryInfluxdb (startInstant,endInstant,market,"AACG",inter));
			
		 System.out.println();
		
	}

}
