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

import data.source.external.database.influxdb.mirrors.alphaVantage.StockTimeSeriesPoint;
import data.source.internal.dataset.timeseries.InternalTimeSeriesQueryInfluxdb.Interval;
import data.source.internal.dataset.timeseries.InternalTimeSeriesQueryInfluxdb.Market;
import data.source.internal.dataset.timeseries.cleaning.TimeSeriesCleanerI;
import data.source.internal.dataset.timeseries.datastructure.RBTree;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPoint;

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
		 Market market = Market.US_STOCKS;
		 String code = "AAPL";
		 Interval inter = Interval.HOUR8;
		
		 Injector injector = Guice.createInjector(new BasicModule());
		 
		InternalQuery  query = new InternalQuery (startDate,endDate,market,code,inter);
		InternalTimeSeriesFromInfluxdbFactory itsf  = injector.getInstance(InternalTimeSeriesFromInfluxdbFactory.class);
		InternalStockTimeSeriesImpl<RBTree> its =  itsf.createTimeSeries(new ArrayList<String>(),query);
		
		System.out.println();
	}
	
	public class BasicModule extends AbstractModule {
		 
	    @Override
	    protected void configure() {
	        MapBinder<Market, InternalTimeSeriesPoint> mapbinderInternalTimeSeriesPoint
	            = MapBinder.newMapBinder(binder(), Market.class, InternalTimeSeriesPoint.class);
	        mapbinderInternalTimeSeriesPoint.addBinding(Market.US_STOCKS).to(StockTimeSeriesPoint.class);
	       
	      
	    MapBinder<String,TimeSeriesCleanerI> mapbinderTimeSeriesCleaner
	        = MapBinder.newMapBinder(binder(), String.class, TimeSeriesCleanerI.class);
   
	    }
	}

}
