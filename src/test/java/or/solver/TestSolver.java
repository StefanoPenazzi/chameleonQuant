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
import or.model.SSMASJeneticModel;
import strategies.SingleSimpleMovingAverageStrategy;

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
		
		SSMASJeneticModel jModel = new SSMASJeneticModel(SingleSimpleMovingAverageStrategy.class,dts.getTimeSeries(new TimeSeriesIdImpl.Builder("KO")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1d")
				 .build()));
		
		Jenetics<SSMASJeneticModel> jen = new Jenetics<SSMASJeneticModel>(jModel);
		jen.run();
	}

}
