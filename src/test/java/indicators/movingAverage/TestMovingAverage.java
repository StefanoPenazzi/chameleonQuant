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
import data.source.external.database.influxdb.TimeSeriesRequestIdInfluxdb;
import data.source.external.database.influxdb.TimeSeriesRequestInfluxdb;
import data.source.external.database.influxdb.mirrors.StockEODTimeSeriesPointInfluxdb;
import data.source.external.financialdatavendors.alphavantage.TimeSeriesRequestIdAlphaVantage;
import data.source.external.financialdatavendors.alphavantage.mirrors.StockEODTimeSeriesPointAlphaVantage;
import data.source.external.financialdatavendors.alphavantage.parameters.functions.Function;
import data.source.internal.dataset.DatasetI;
import data.source.internal.dataset.DatasetImpl;
import data.source.internal.timeseries.TimeSeriesRequestIdI;
import data.source.internal.timeseries.standard.TimeSeriesFactoryImpl;
import data.source.internal.timeseries.standard.TimeSeriesIdImpl;
import data.source.internal.timeseries.standard.TimeSeriesImpl;

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
		 
		 TimeSeriesFactoryImpl itsf  = controller.getInjector().getInstance(new Key<TimeSeriesFactoryImpl>() {});
		 TimeSeriesRequestInfluxdb itsq = new TimeSeriesRequestInfluxdb();
		 
		 DatasetImpl dts = new DatasetImpl();
		 
		 //for(String stock: stocks) {
		//	 TimeSeriesId  query = new TimeSeriesId (startInstant,endInstant,market,stock,inter,StockEODTimeSeriesPointInfluxdb.class);
		//	 dts.addTimeSeries(itsf.createTimeSeriesQueryRequest(new ArrayList<String>(){{add("NULL_INFLUXDB");}},itsq,query));
		 //}
		 
		 //SimpleMovingAverage sma = new SimpleMovingAverage(dts,new TimeSeriesId (startInstant,endInstant,market,"TSLA",inter,StockEODTimeSeriesPointInfluxdb.class),"low",7);
		 //sma.create();
		 //InternalTimeSeriesI<? extends InternalTimeSeriesPoint> its = dts.getTimeSeries(new InternalStockTimeSeriesQueryInfluxdb (startDate,endDate,market,"AAPL",inter));
		
		System.out.println();
	}
	
	@Test
	void testExponentialMovingAverage() throws Exception {
        
		Controller.run();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Instant startInstant = (sdf.parse("2020-10-19 00:00:00")).toInstant();
		Instant endInstant = null;
		
		List<TimeSeriesRequestIdI> listQueries = new ArrayList<>();
		listQueries.add(new TimeSeriesRequestIdInfluxdb.Builder(new TimeSeriesIdImpl.Builder("AMZN")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1h")
				 .build())
				.build());
		 
		 
		 DatasetI dts = Controller.getDatasetFactory().create(listQueries);
		 
		 TimeSeriesImpl ema = new ExponentialMovingAverage.Builder(dts.getTimeSeries(new TimeSeriesIdImpl.Builder("AMZN")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1h")
				 .build()))
				 .source("low")
				 .length(5)
				 .build()
				 .run();
		
		System.out.println();
	}

}
