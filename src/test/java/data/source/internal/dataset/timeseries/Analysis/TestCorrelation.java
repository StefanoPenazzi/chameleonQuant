package data.source.internal.dataset.timeseries.Analysis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import org.junit.jupiter.api.Test;
import controller.Controller;
import data.source.external.database.influxdb.TimeSeriesRequestIdInfluxdb;
import data.source.internal.dataset.DatasetI;
import data.source.internal.timeseries.TimeSeriesRequestIdI;
import data.source.internal.timeseries.analysis.Correlation;
import data.source.internal.timeseries.analysis.Differencing;
import data.source.internal.timeseries.standard.TimeSeriesIdImpl;

class TestCorrelation {

	@Test
	void testCorrelogram() {
		
        Controller.run();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Instant startInstant = null;
		try {
			startInstant = (sdf.parse("2012-10-19 00:00:00")).toInstant();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Instant endInstant = null;
		
		List<TimeSeriesRequestIdI> listQueries = new ArrayList<>();
		listQueries.add(new TimeSeriesRequestIdInfluxdb.Builder(new TimeSeriesIdImpl.Builder("EUR-USD")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1d")
				 .build())
				.build());
		 
		DatasetI dts = Controller.getDataset();
		dts.addTimeSeries(listQueries);
		 
		 TreeMap<Integer,List<Double>> res = Correlation.correlogram(Differencing.differencing(dts.getTimeSeries(new TimeSeriesIdImpl.Builder("EUR-USD")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1d")
				 .build()), "close",1), "value",100); 
		
		System.out.println();
		
	}
	
	@Test
	void testCrossCorrelation() {
		
        Controller.run();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Instant startInstant = null;
		Instant endInstant = null;
		try {
			startInstant = (sdf.parse("2013-10-19 00:00:00")).toInstant();
			endInstant = (sdf.parse("2020-10-19 00:00:00")).toInstant();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		List<TimeSeriesRequestIdI> listQueries = new ArrayList<>();
		listQueries.add(new TimeSeriesRequestIdInfluxdb.Builder(new TimeSeriesIdImpl.Builder("AMZN")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1d")
				 .build())
				.build());
		
		listQueries.add(new TimeSeriesRequestIdInfluxdb.Builder(new TimeSeriesIdImpl.Builder("EUR-USD")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1d")
				 .build())
				.build());
		 
		DatasetI dts = Controller.getDataset();
		dts.addTimeSeries(listQueries);
		 
		 TreeMap<Integer,List<Double>> res = Correlation.crossCorrelation(Differencing.differencing(dts.getTimeSeries(new TimeSeriesIdImpl.Builder("EUR-USD")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1d")
				 .build()), "close",1),
				 
				 Differencing.differencing(dts.getTimeSeries(new TimeSeriesIdImpl.Builder("EUR-USD")
						 .startInstant(startInstant)
						 .endInstant(endInstant)
						 .interval("1d")
						 .build()), "close",1),
				 
				 "value",100); 
		
		System.out.println();
		
	}

}
