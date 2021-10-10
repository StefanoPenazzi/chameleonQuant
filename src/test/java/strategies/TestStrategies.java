/**
 * 
 */
package strategies;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import controller.Controller;
import data.source.external.database.influxdb.TimeSeriesRequestIdInfluxdb;
import data.source.internal.dataset.DatasetI;
import data.source.internal.timeseries.TimeSeriesI;
import data.source.internal.timeseries.TimeSeriesRequestIdI;
import data.source.internal.timeseries.standard.TimeSeriesIdImpl;
import strategies.positionsizing.InitialMoneyAmount;

/**
 * @author stefanopenazzi
 *
 */
class TestStrategies {

	@Test
	void testSimpleMovingAverageStrategy() throws Exception {
        Controller.run();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Instant startInstant = (sdf.parse("2018-01-01 00:00:00")).toInstant();
		Instant endInstant = null;
		
		List<TimeSeriesRequestIdI> listQueries = new ArrayList<>();
		listQueries.add(new TimeSeriesRequestIdInfluxdb.Builder(new TimeSeriesIdImpl.Builder("KO")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1d")
				 .build())
				.build());
		 
		 
		DatasetI dts = Controller.getDataset();
		dts.addTimeSeries(listQueries);
		 
		 SingleSimpleMovingAverageStrategy smas = new SingleSimpleMovingAverageStrategy.Builder(dts.getTimeSeries(new TimeSeriesIdImpl.Builder("KO")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1d")
				 .build()))
				 .source("close")
				 .length(35)
				 .build();
		 smas.run();
		 
		System.out.println(smas.getPerformanceReport());
	}
	
	@Test
	void testDualSimpleMovingAverageCrossoverStrategy() throws Exception {
        Controller.run();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Instant startInstant = (sdf.parse("2015-01-01 00:00:00")).toInstant();
		Instant endInstant = null;
		
		List<TimeSeriesRequestIdI> listQueries = new ArrayList<>();
		listQueries.add(new TimeSeriesRequestIdInfluxdb.Builder(new TimeSeriesIdImpl.Builder("KO")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1d")
				 .build())
				.build());
		 
		 
		DatasetI dts = Controller.getDataset();
		dts.addTimeSeries(listQueries);
		 
		 DualSimpleMovingAverageCrossoverStrategy dsmac = new DualSimpleMovingAverageCrossoverStrategy.Builder(dts.getTimeSeries(new TimeSeriesIdImpl.Builder("KO")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1d")
				 .build()))
				 .source("close")
				 .lengthShortTermMA(66)
				 .lengthLongTermMA(79)
				 .build();
		 dsmac.run();
		
		System.out.println(dsmac.getPerformanceReport());
	}
	
	@Test
	void testTripleMovingAverageCrossoverStrategy() throws Exception {
        Controller.run();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Instant startInstant = (sdf.parse("2015-01-01 00:00:00")).toInstant();
		Instant endInstant = null;
		
		List<TimeSeriesRequestIdI> listQueries = new ArrayList<>();
		listQueries.add(new TimeSeriesRequestIdInfluxdb.Builder(new TimeSeriesIdImpl.Builder("AMZN")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1d")
				 .build())
				.build());
		 
		 
		DatasetI dts = Controller.getDataset();
		dts.addTimeSeries(listQueries);
		 
		 TripleSimpleMovingAverageCrossoverStrategy tsmac = new TripleSimpleMovingAverageCrossoverStrategy.Builder(dts.getTimeSeries(new TimeSeriesIdImpl.Builder("AMZN")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1d")
				 .build()))
				 .source("close")
				 .lengthShortTermMA(5)
				 .lengthMediumTermMA(11)
				 .lengthLongTermMA(13)
				 .positionSizing(new InitialMoneyAmount.Builder()
						.initialMoneyAmount(10000)
						.build())
				 .build();
		 tsmac.run();
		 
		 System.out.println(tsmac.getPerformanceReport());
	}

}
