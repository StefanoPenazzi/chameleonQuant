/**
 * 
 */
package strategies;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import controller.Controller;
import data.source.external.database.influxdb.TimeSeriesRequestIdInfluxdb;
import data.source.internal.dataset.DatasetI;
import data.source.internal.timeseries.TimeSeriesRequestIdI;
import data.source.internal.timeseries.standard.TimeSeriesIdImpl;
import data.source.internal.timeseries.standard.TimeSeriesImpl;
import indicators.movingAverage.ExponentialMovingAverage;

/**
 * @author stefanopenazzi
 *
 */
class TestStrategies {

	@Test
	void testSimpleMovingAverageStrategy() throws Exception {
        Controller.run();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Instant startInstant = (sdf.parse("2010-10-19 00:00:00")).toInstant();
		Instant endInstant = null;
		
		List<TimeSeriesRequestIdI> listQueries = new ArrayList<>();
		listQueries.add(new TimeSeriesRequestIdInfluxdb.Builder(new TimeSeriesIdImpl.Builder("AMZN")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1d")
				 .build())
				.build());
		 
		 
		 DatasetI dts = Controller.getDatasetFactory().create(listQueries);
		 
		 SingleSimpleMovingAverageStrategy smas = new SingleSimpleMovingAverageStrategy.Builder(dts.getTimeSeries(new TimeSeriesIdImpl.Builder("AMZN")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1d")
				 .build()))
				 .source("close")
				 .length(200)
				 .build();
		 smas.run();
		 
		 double res = smas.getReturnOnInitialCapital();
		
		System.out.println();
	}
	
	@Test
	void testDualSimpleMovingAverageCrossoverStrategy() throws Exception {
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
		 
		 DualSimpleMovingAverageCrossoverStrategy dsmac = new DualSimpleMovingAverageCrossoverStrategy.Builder(dts.getTimeSeries(new TimeSeriesIdImpl.Builder("AMZN")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1h")
				 .build()))
				 .source("close")
				 .lengthShortTermMA(8)
				 .lengthLongTermMA(16)
				 .build();
		 dsmac.run();
		 
		 double res = dsmac.getReturnOnInitialCapital();
		
		System.out.println();
	}
	
	@Test
	void testTripleSimpleMovingAverageCrossoverStrategy() throws Exception {
        Controller.run();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Instant startInstant = (sdf.parse("2010-01-01 00:00:00")).toInstant();
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
				 .lengthShortTermMA(10)
				 .lengthMediumTermMA(20)
				 .lengthLongTermMA(100)
				 .build();
		 tsmac.run();
		 
		 double res = tsmac.getReturnOnInitialCapital();
		
		System.out.println();
	}

}
