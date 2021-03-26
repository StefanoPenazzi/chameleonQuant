/**
 * 
 */
package or.solver;

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
import or.model.SEMASJeneticsModel;
import or.model.DSMASJeneticsModel;
import or.model.SSMASJeneticsModel;
import or.model.TSMASJeneticsModel;
import strategies.DualSimpleMovingAverageCrossoverStrategy;
import strategies.SingleExpMovingAverageStrategy;
import strategies.SingleSimpleMovingAverageStrategy;
import strategies.TripleSimpleMovingAverageCrossoverStrategy;

/**
 * @author stefanopenazzi
 *
 */
class TestSolver {

	@Test
	void testJenetics() throws ParseException {
		
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
		 
		 
		 DatasetI dts = Controller.getDatasetFactory().create(listQueries);
		
		SSMASJeneticsModel jModel = new SSMASJeneticsModel(SingleSimpleMovingAverageStrategy.class,dts.getTimeSeries(new TimeSeriesIdImpl.Builder("KO")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1d")
				 .build()));
		
		Jenetics<SSMASJeneticsModel> jen = new Jenetics<SSMASJeneticsModel>(jModel);
		jen.run();
	}
	
	@Test
	void testJeneticsSDMAS() throws ParseException {
		
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
		 
		 
		 DatasetI dts = Controller.getDatasetFactory().create(listQueries);
		
		DSMASJeneticsModel jModel = new DSMASJeneticsModel(DualSimpleMovingAverageCrossoverStrategy.class,dts.getTimeSeries(new TimeSeriesIdImpl.Builder("KO")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1d")
				 .build()));
		
		Jenetics<DSMASJeneticsModel> jen = new Jenetics<DSMASJeneticsModel>(jModel);
		jen.run();
	}
	
	@Test
	void testJeneticsTSMAS() throws ParseException {
		
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
		 
		 
		 DatasetI dts = Controller.getDatasetFactory().create(listQueries);
		
		TSMASJeneticsModel jModel = new TSMASJeneticsModel(TripleSimpleMovingAverageCrossoverStrategy.class,dts.getTimeSeries(new TimeSeriesIdImpl.Builder("AMZN")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1d")
				 .build()));
		
		Jenetics<TSMASJeneticsModel> jen = new Jenetics<TSMASJeneticsModel>(jModel);
		jen.run();
	}

}
