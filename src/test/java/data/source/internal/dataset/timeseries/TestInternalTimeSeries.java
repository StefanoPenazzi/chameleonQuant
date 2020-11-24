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
import com.google.inject.multibindings.MapBinder;

import data.source.external.database.influxdb.InternalStockTimeSeriesQueryInfluxdb;
import data.source.external.database.influxdb.InternalTimeSeriesQueryRequestInfluxdb;
import data.source.external.database.influxdb.mirrors.alphaVantage.StockTimeSeriesPoint;
import data.source.internal.dataset.timeseries.cleaning.TimeSeriesCleanerI;
import data.source.internal.dataset.timeseries.datastructure.RBTree;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPoint;
import data.source.internal.dataset.timeseries.standard.InternalTimeSeriesFactoryImpl;
import data.source.internal.dataset.timeseries.standard.stock.InternalStockQuery;
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
		InternalTimeSeriesFactoryImpl itsf  = injector.getInstance(InternalTimeSeriesFactoryImpl.class);
		InternalTimeSeriesQueryRequestInfluxdb itsq = injector.getInstance(InternalTimeSeriesQueryRequestInfluxdb.class);
		InternalStockTimeSeriesImpl<RBTree> its =  itsf.createTimeSeries(new ArrayList<String>(),itsq,query);
		
		System.out.println();
	}
	
	public class BasicModule extends AbstractModule {
		 
	    @Override
	    protected void configure() {
	        MapBinder<String, InternalTimeSeriesPoint> mapbinderInternalTimeSeriesPoint
	            = MapBinder.newMapBinder(binder(), String.class, InternalTimeSeriesPoint.class);
	        mapbinderInternalTimeSeriesPoint.addBinding("US_STOCKS_TIME_SERIES_INTRADAY_1MIN").to(StockTimeSeriesPoint.class);
	       
	      
	    MapBinder<String,TimeSeriesCleanerI> mapbinderTimeSeriesCleaner
	        = MapBinder.newMapBinder(binder(), String.class, TimeSeriesCleanerI.class);
   
	    }
	}

}
