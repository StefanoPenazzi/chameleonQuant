/**
 * 
 */
package data.source.internal.dataset.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import data.source.external.database.influxdb.mirrors.alphaVantage.StockTimeSeriesPointInfluxdb;
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
		
	     Date startDate = sdf.parse("2020/10/19 00:00:00");
		 Date endDate = null;
		 String market = "US_STOCKS_TIME_SERIES_INTRADAY_1MIN";
		 String inter = "8h";
		
		 Injector injector = Guice.createInjector(new BasicModule());
		 
		 InternalTimeSeriesFactoryImpl<StockTimeSeriesPointInfluxdb> itsf  = injector.getInstance(new Key<InternalTimeSeriesFactoryImpl<StockTimeSeriesPointInfluxdb>>() {});
		 InternalTimeSeriesQueryRequestInfluxdb<StockTimeSeriesPointInfluxdb> itsq = new InternalTimeSeriesQueryRequestInfluxdb<StockTimeSeriesPointInfluxdb>(new StockTimeSeriesPointInfluxdb());
		 
		 DatasetImpl dts = new DatasetImpl();
		 
		 for(String stock: stocks) {
			 InternalStockTimeSeriesQueryInfluxdb  query = new InternalStockTimeSeriesQueryInfluxdb (startDate,endDate,market,stock,inter);
			 dts.addTimeSeries(itsf.createTimeSeries(new ArrayList<String>(){{add("NULL_INFLUXDB");}},itsq,query));
		 }
		
		System.out.println();
	}
	
	@Test
	void testDatasetController() throws ParseException, InstantiationException, IllegalAccessException {
		
		
		List<String> stocks = Arrays.asList("AAPL","AMZN","TSLA","FB","C");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		
	     Date startDate = sdf.parse("2020/10/19 00:00:00");
		 Date endDate = null;
		 String market = "US_STOCKS_TIME_SERIES_INTRADAY_1MIN";
		 String inter = "8h";
		
		 Controller controller = new Controller();
		 controller.run();
		 
		 InternalTimeSeriesFactoryImpl<StockTimeSeriesPointInfluxdb> itsf  = controller.getInjector().getInstance(new Key<InternalTimeSeriesFactoryImpl<StockTimeSeriesPointInfluxdb>>() {});
		 InternalTimeSeriesQueryRequestInfluxdb<StockTimeSeriesPointInfluxdb> itsq = new InternalTimeSeriesQueryRequestInfluxdb<StockTimeSeriesPointInfluxdb>(new StockTimeSeriesPointInfluxdb());
		 
		 DatasetImpl dts = new DatasetImpl();
		 
		 for(String stock: stocks) {
			 InternalStockTimeSeriesQueryInfluxdb  query = new InternalStockTimeSeriesQueryInfluxdb (startDate,endDate,market,stock,inter);
			 dts.addTimeSeries(itsf.createTimeSeries(new ArrayList<String>(){{add("NULL_INFLUXDB");}},itsq,query));
		 }
		
		System.out.println();
	}
	
	public class BasicModule extends AbstractModule {
		 
		@Override
	    protected void configure() {
			/*
			 * MapBinder<String, InternalTimeSeriesPoint> mapbinderInternalTimeSeriesPoint =
			 * MapBinder.newMapBinder(binder(), String.class,
			 * InternalTimeSeriesPoint.class); mapbinderInternalTimeSeriesPoint.addBinding(
			 * "US_STOCKS_TIME_SERIES_INTRADAY_1MIN").to(StockTimeSeriesPointInfluxdb.class)
			 * ;
			 * 
			 * 
			 * MapBinder<String,TimeSeriesCleanerI<? extends InternalTimeSeriesPoint>>
			 * mapbinderTimeSeriesCleaner = MapBinder.newMapBinder(binder(), new
			 * TypeLiteral<String>(){}, new TypeLiteral<TimeSeriesCleanerI<? extends
			 * InternalTimeSeriesPoint>>(){});
			 * 
			 * mapbinderTimeSeriesCleaner.addBinding("NULL_INFLUXDB").to((Class<? extends
			 * TimeSeriesCleanerI<? extends InternalTimeSeriesPoint>>)
			 * TimeSeriesCleanerNullValuesStockInfluxdb.class);
			 */
	    }
	}

}
