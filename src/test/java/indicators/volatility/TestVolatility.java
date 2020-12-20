/**
 * 
 */
package indicators.volatility;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import indicators.movingAverage.ExponentialMovingAverage;

/**
 * @author stefanopenazzi
 *
 */
class TestVolatility {

	@Test
	void testTrueRange() throws Exception {
		 List<String> stocks = Arrays.asList("ACER");
			
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
	     Date startDate = sdf.parse("2019/10/19");
		 Date endDate = null;
		 String market = "NASDAQ_EOD";
		 String inter = "1d";
		
		 Controller controller = new Controller();
		 controller.run();
		 
		 InternalTimeSeriesFactoryImpl<StockEODTimeSeriesPointInfluxdb> itsf  = controller.getInjector().getInstance(new Key<InternalTimeSeriesFactoryImpl<StockEODTimeSeriesPointInfluxdb>>() {});
		 InternalTimeSeriesQueryRequestInfluxdb<StockEODTimeSeriesPointInfluxdb> itsq = new InternalTimeSeriesQueryRequestInfluxdb<StockEODTimeSeriesPointInfluxdb>(new StockEODTimeSeriesPointInfluxdb());
		 
		 DatasetImpl dts = new DatasetImpl();
		 
		 for(String stock: stocks) {
			 InternalStockTimeSeriesQueryInfluxdb  query = new InternalStockTimeSeriesQueryInfluxdb (startDate,endDate,market,stock,inter);
			 dts.addTimeSeries(itsf.createTimeSeriesQueryRequest(new ArrayList<String>(){{add("NULL_INFLUXDB");}},itsq,query));
		 }
		 
		 TrueRange tr = new TrueRange(dts,new InternalStockTimeSeriesQueryInfluxdb (startDate,endDate,market,"ACER",inter));
         DatasetImpl ds = tr.create();
		 //InternalTimeSeriesI<? extends InternalTimeSeriesPoint> its = dts.getTimeSeries(new InternalStockTimeSeriesQueryInfluxdb (startDate,endDate,market,"AAPL",inter));
		
		System.out.println();
	}

}
