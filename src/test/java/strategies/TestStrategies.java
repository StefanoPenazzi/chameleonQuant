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

/**
 * @author stefanopenazzi
 *
 */
class TestStrategies {

	@Test
	void testSimpleMovingAverageStrategy() throws Exception {
        Controller.run();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Instant startInstant = (sdf.parse("2020-01-01 00:00:00")).toInstant();
		Instant endInstant = null;
		
		List<TimeSeriesRequestIdI> listQueries = new ArrayList<>();
		listQueries.add(new TimeSeriesRequestIdInfluxdb.Builder(new TimeSeriesIdImpl.Builder("AMZN")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1d")
				 .build())
				.build());
		 
		 
		 DatasetI dts = Controller.getDatasetFactory().create(listQueries);
		 
		 SingleExpMovingAverageStrategy smas = new SingleExpMovingAverageStrategy.Builder(dts.getTimeSeries(new TimeSeriesIdImpl.Builder("AMZN")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1d")
				 .build()))
				 .source("close")
				 .length(18)
				 .build();
		 smas.run();
		 
		System.out.println(smas.getPerformanceReport());
	}
	
	@Test
	void testDualSimpleMovingAverageCrossoverStrategy() throws Exception {
        Controller.run();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Instant startInstant = (sdf.parse("2020-01-01 00:00:00")).toInstant();
		Instant endInstant = null;
		
		List<TimeSeriesRequestIdI> listQueries = new ArrayList<>();
		listQueries.add(new TimeSeriesRequestIdInfluxdb.Builder(new TimeSeriesIdImpl.Builder("AMZN")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1d")
				 .build())
				.build());
		 
		 
		 DatasetI dts = Controller.getDatasetFactory().create(listQueries);
		 
		 DualSimpleMovingAverageCrossoverStrategy dsmac = new DualSimpleMovingAverageCrossoverStrategy.Builder(dts.getTimeSeries(new TimeSeriesIdImpl.Builder("AMZN")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1d")
				 .build()))
				 .source("close")
				 .lengthShortTermMA(20)
				 .lengthLongTermMA(50)
				 .build();
		 dsmac.run();
		
		System.out.println(dsmac.getPerformanceReport());
	}
	
	@Test
	void testTripleMovingAverageCrossoverStrategy() throws Exception {
        Controller.run();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Instant startInstant = (sdf.parse("2020-01-01 00:00:00")).toInstant();
		Instant endInstant = null;
		
		List<TimeSeriesRequestIdI> listQueries = new ArrayList<>();
		listQueries.add(new TimeSeriesRequestIdInfluxdb.Builder(new TimeSeriesIdImpl.Builder("AMZN")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1d")
				 .build())
				.build());
		 
		 
		 DatasetI dts = Controller.getDatasetFactory().create(listQueries);
		 
		 TripleSimpleMovingAverageCrossoverStrategy tsmac = new TripleSimpleMovingAverageCrossoverStrategy.Builder(dts.getTimeSeries(new TimeSeriesIdImpl.Builder("AMZN")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1d")
				 .build()))
				 .source("close")
				 .lengthShortTermMA(5)
				 .lengthMediumTermMA(10)
				 .lengthLongTermMA(20)
				 .build();
		 tsmac.run();
		 
		 System.out.println(tsmac.getPerformanceReport());
		
		System.out.println();
	}

}
