/**
 * 
 */
package indicators.volatility;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import controller.Controller;
import data.source.external.database.influxdb.TimeSeriesRequestIdInfluxdb;
import data.source.internal.dataset.DatasetI;
import data.source.internal.dataset.DatasetHistoricalImpl;
import data.source.internal.timeseries.TimeSeriesRequestIdI;
import data.source.internal.timeseries.standard.TimeSeriesIdImpl;
import data.source.internal.timeseries.standard.TimeSeriesImpl;
import indicators.movingAverage.MACD;

/**
 * @author stefanopenazzi
 *
 */
class TestVolatility {

	@Test
	void testTrueRange() throws Exception {
		
		Controller.run();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Instant startInstant = (sdf.parse("2020-10-19 00:00:00")).toInstant();
		Instant endInstant = null;
		String inter = "1h";
		
		List<TimeSeriesRequestIdI> listQueries = new ArrayList<>();
		listQueries.add(new TimeSeriesRequestIdInfluxdb.Builder(new TimeSeriesIdImpl.Builder("AMZN")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1h")
				 .build())
				.build());
		 
		 
		DatasetI dts = Controller.getDataset();
		dts.addTimeSeries(listQueries);
		 
		 TimeSeriesImpl tr = new TrueRange.Builder(dts.getTimeSeries(new TimeSeriesIdImpl.Builder("AMZN")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval(inter)
				 .build()))
				 .build()
				 .run();
		
		System.out.println();
	}
	
	@Test
	void testAverageTrueRange() throws Exception {
		
		Controller.run();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Instant startInstant = (sdf.parse("2020-10-19 00:00:00")).toInstant();
		Instant endInstant = null;
		String inter = "1h";
		
		List<TimeSeriesRequestIdI> listQueries = new ArrayList<>();
		listQueries.add(new TimeSeriesRequestIdInfluxdb.Builder(new TimeSeriesIdImpl.Builder("AMZN")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1h")
				 .build())
				.build());
		 
		 
		DatasetI dts = Controller.getDataset();
		dts.addTimeSeries(listQueries);
		 
		 TimeSeriesImpl atr = new AverageTrueRange.Builder(dts.getTimeSeries(new TimeSeriesIdImpl.Builder("AMZN")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval(inter)
				 .build()))
				 .length(12)
				 .build()
				 .run();
		System.out.println();
	}
	
	@Test
	void testMACD() throws Exception {
		
		Controller.run();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Instant startInstant = (sdf.parse("2020-10-19 00:00:00")).toInstant();
		Instant endInstant = null;
		String inter = "1h";
		
		List<TimeSeriesRequestIdI> listQueries = new ArrayList<>();
		listQueries.add(new TimeSeriesRequestIdInfluxdb.Builder(new TimeSeriesIdImpl.Builder("AMZN")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1h")
				 .build())
				.build());
		 
		 
		DatasetI dts = Controller.getDataset();
		dts.addTimeSeries(listQueries);
		 
		 DatasetHistoricalImpl macd = (DatasetHistoricalImpl) new MACD.Builder(dts.getTimeSeries(new TimeSeriesIdImpl.Builder("AMZN")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval(inter)
				 .build()))
				 .source("close")
				 .fastEMALength(12)
				 .slowEMALength(26)
				 .signalLineLength(9)
				 .build()
				 .run();
		System.out.println();
	}


}
