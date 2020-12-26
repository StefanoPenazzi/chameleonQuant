/**
 * 
 */
package indicators.movingAverage;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.google.inject.Key;
import controller.Controller;
import data.source.external.database.influxdb.InternalStockTimeSeriesQueryInfluxdb;
import data.source.external.database.influxdb.InternalTimeSeriesQueryRequestInfluxdb;
import data.source.external.database.influxdb.mirrors.alphaVantage.StockEODTimeSeriesPointInfluxdb;
import data.source.internal.dataset.core.DatasetImpl;
import data.source.internal.dataset.timeseries.standard.InternalTimeSeriesFactoryImpl;

/**
 * @author stefanopenazzi
 *
 */
class TestMovingAverage {

	@Test
	void testSimpleMovingAverage() throws Exception {
         List<String> stocks = Arrays.asList("AAPL","AMZN","TSLA","FB","C");
		
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		
		 Instant startInstant = (sdf.parse("2020/10/19 00:00:00")).toInstant();
		 Instant endInstant = null;
		 String market = "US_STOCKS_TIME_SERIES_INTRADAY_1MIN";
		 String inter = "8h";
		
		 Controller controller = new Controller();
		 controller.run();
		 
		 InternalTimeSeriesFactoryImpl<StockEODTimeSeriesPointInfluxdb> itsf  = controller.getInjector().getInstance(new Key<InternalTimeSeriesFactoryImpl<StockEODTimeSeriesPointInfluxdb>>() {});
		 InternalTimeSeriesQueryRequestInfluxdb<StockEODTimeSeriesPointInfluxdb> itsq = new InternalTimeSeriesQueryRequestInfluxdb<StockEODTimeSeriesPointInfluxdb>(new StockEODTimeSeriesPointInfluxdb());
		 
		 DatasetImpl dts = new DatasetImpl();
		 
		 for(String stock: stocks) {
			 InternalStockTimeSeriesQueryInfluxdb  query = new InternalStockTimeSeriesQueryInfluxdb (startInstant,endInstant,market,stock,inter);
			 dts.addTimeSeries(itsf.createTimeSeriesQueryRequest(new ArrayList<String>(){{add("NULL_INFLUXDB");}},itsq,query));
		 }
		 
		 SimpleMovingAverage sma = new SimpleMovingAverage(dts,new InternalStockTimeSeriesQueryInfluxdb (startInstant,endInstant,market,"TSLA",inter),"low",7);
		 sma.create();
		 //InternalTimeSeriesI<? extends InternalTimeSeriesPoint> its = dts.getTimeSeries(new InternalStockTimeSeriesQueryInfluxdb (startDate,endDate,market,"AAPL",inter));
		
		System.out.println();
	}
	
	@Test
	void testExponentialMovingAverage() throws Exception {
         List<String> stocks = Arrays.asList("AAPL","AMZN","TSLA","FB","C");
		
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		
		 Instant startInstant = (sdf.parse("2020/10/19 00:00:00")).toInstant();
		 Instant endInstant = null;
		 String market = "US_STOCKS_TIME_SERIES_INTRADAY_1MIN";
		 String inter = "8h";
		
		 Controller controller = new Controller();
		 controller.run();
		 
		 InternalTimeSeriesFactoryImpl<StockEODTimeSeriesPointInfluxdb> itsf  = controller.getInjector().getInstance(new Key<InternalTimeSeriesFactoryImpl<StockEODTimeSeriesPointInfluxdb>>() {});
		 InternalTimeSeriesQueryRequestInfluxdb<StockEODTimeSeriesPointInfluxdb> itsq = new InternalTimeSeriesQueryRequestInfluxdb<StockEODTimeSeriesPointInfluxdb>(new StockEODTimeSeriesPointInfluxdb());
		 
		 DatasetImpl dts = new DatasetImpl();
		 
		 for(String stock: stocks) {
			 InternalStockTimeSeriesQueryInfluxdb  query = new InternalStockTimeSeriesQueryInfluxdb (startInstant,endInstant,market,stock,inter);
			 dts.addTimeSeries(itsf.createTimeSeriesQueryRequest(new ArrayList<String>(){{add("NULL_INFLUXDB");}},itsq,query));
		 }
		 
		 ExponentialMovingAverage sma = new ExponentialMovingAverage(dts,new InternalStockTimeSeriesQueryInfluxdb (startInstant,endInstant,market,"TSLA",inter),"low",3);
		 sma.create();
		 //InternalTimeSeriesI<? extends InternalTimeSeriesPoint> its = dts.getTimeSeries(new InternalStockTimeSeriesQueryInfluxdb (startDate,endDate,market,"AAPL",inter));
		
		System.out.println();
	}

}
