/**
 * 
 */
package data.source.internal.dataset.timeseries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;

import data.source.external.database.influxdb.InternalStockTimeSeriesQueryInfluxdb;
import data.source.external.database.influxdb.InternalTimeSeriesQueryRequestInfluxdb;
import data.source.external.database.influxdb.TimeSeriesCleanerNullValuesStockInfluxdb;
import data.source.external.database.influxdb.mirrors.alphaVantage.StockTimeSeriesPointInfluxdb;
import data.source.internal.dataset.timeseries.cleaning.TimeSeriesCleanerI;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPoint;
import data.source.internal.dataset.timeseries.standard.InternalTimeSeriesFactoryImpl;
import data.source.internal.dataset.timeseries.standard.stock.InternalStockTimeSeriesImpl;

/**
 * @author stefanopenazzi
 *
 */
class TestInternalTimeSeries {

	
	@Test
	void testInternalTimeSeriesFromInfluxDB() throws ParseException {
	    
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		
	     Date startDate = sdf.parse("2020/10/19 00:00:00");
		 Date endDate = null;
		 String market = "US_STOCKS_TIME_SERIES_INTRADAY_1MIN";
		 String code = "FB";
		 String inter = "8h";
		
		 Injector injector = Guice.createInjector(new BasicModule());
		 
		InternalStockTimeSeriesQueryInfluxdb  query = new InternalStockTimeSeriesQueryInfluxdb (startDate,endDate,market,code,inter);
		InternalTimeSeriesFactoryImpl<StockTimeSeriesPointInfluxdb> itsf  = injector.getInstance(Key.get(new TypeLiteral<InternalTimeSeriesFactoryImpl<StockTimeSeriesPointInfluxdb>>(){}));
		InternalTimeSeriesQueryRequestInfluxdb itsq = injector.getInstance(InternalTimeSeriesQueryRequestInfluxdb.class);
		InternalStockTimeSeriesImpl<StockTimeSeriesPointInfluxdb> its =  itsf.createTimeSeries(new ArrayList<String>() {{add("NULL_INFLUXDB");}},itsq,query);
		
		System.out.println();
	}
	
	public class BasicModule extends AbstractModule {
		 
	    @Override
	    protected void configure() {
	        MapBinder<String, InternalTimeSeriesPoint> mapbinderInternalTimeSeriesPoint
	            = MapBinder.newMapBinder(binder(), String.class, InternalTimeSeriesPoint.class);
	        mapbinderInternalTimeSeriesPoint.addBinding("US_STOCKS_TIME_SERIES_INTRADAY_1MIN").to(StockTimeSeriesPointInfluxdb.class);
	       
	      
	    MapBinder<String,TimeSeriesCleanerI<? extends InternalTimeSeriesPoint>> mapbinderTimeSeriesCleaner
	        = MapBinder.newMapBinder(binder(), new TypeLiteral<String>(){}, new TypeLiteral<TimeSeriesCleanerI<? extends InternalTimeSeriesPoint>>(){});
	    
	    mapbinderTimeSeriesCleaner.addBinding("NULL_INFLUXDB").to((Class<? extends TimeSeriesCleanerI<? extends InternalTimeSeriesPoint>>) TimeSeriesCleanerNullValuesStockInfluxdb.class);
   
	    }
	}

}
