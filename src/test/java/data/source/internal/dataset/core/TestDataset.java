/**
 * 
 */
package data.source.internal.dataset.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import controller.Controller;
import data.source.external.database.influxdb.TimeSeriesRequestIdInfluxdb;
import data.source.external.database.influxdb.mirrors.FOREXEODTimeSeriesPointInfluxdb;
import data.source.external.database.influxdb.mirrors.StockEODTimeSeriesPointInfluxdb;
import data.source.external.financialdatavendors.alphavantage.TimeSeriesRequestIdAlphaVantage;
import data.source.external.financialdatavendors.alphavantage.mirrors.StockIDTimeSeriesPointAlphaVantage;
import data.source.external.financialdatavendors.alphavantage.parameters.functions.Function;
import data.source.external.financialdatavendors.alphavantage.parameters.intradaytimeseries.Interval;
import data.source.external.financialdatavendors.alphavantage.parameters.output.OutputSize;
import data.source.internal.dataset.DatasetI;
import data.source.internal.timeseries.TimeSeriesI;
import data.source.internal.timeseries.TimeSeriesRequestIdI;
import data.source.internal.timeseries.point.TimeSeriesPointI;
import data.source.internal.timeseries.standard.TimeSeriesIdImpl;


/**
 * @author stefanopenazzi
 *
 */
class TestDataset {

		
	@Test
	void testDatasetInfluxFactory() throws ParseException {
		
		Controller.run();
		
         List<String> stocks = Arrays.asList("AACG","AACQ","AACQU","AACQW","AAL");
		
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Instant startInstant = (sdf.parse("2020-10-19 00:00:00")).toInstant();
		 Instant endInstant = null;
		 String market = "NASDAQ_EOD";
		 String inter = "1d";
		 
		  List<TimeSeriesRequestIdI> listQueries = new ArrayList<>();
		 
		 for(String stock: stocks) {
			 listQueries.add(new TimeSeriesRequestIdInfluxdb.Builder(new TimeSeriesIdImpl.Builder(stock)
					 .startInstant(startInstant)
					 .endInstant(endInstant)
					 .interval(inter)
					 .build())
					 .build());
		 }
		 listQueries.add(new TimeSeriesRequestIdInfluxdb.Builder(new TimeSeriesIdImpl.Builder("EUR-USD")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval(inter)
				 .build())
				 .build());
		 
		 listQueries.add(new TimeSeriesRequestIdInfluxdb.Builder(new TimeSeriesIdImpl.Builder("AMZN")
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval("1h")
				 .build())
				 .build());	 
		 
		 DatasetI dts = Controller.getDataset();
		 dts.addTimeSeries(listQueries);
		 
		 System.out.println();
		
	}
	
	@Test
	void testDatasetAlphaVantageFactory() throws ParseException {
		Controller.run();
		TimeSeriesRequestIdAlphaVantage tr = new TimeSeriesRequestIdAlphaVantage.Builder()
				.id("AMZN")
				.exchange(Function.TIME_SERIES_INTRADAY)
				.outputSize(OutputSize.COMPACT)
				.interval(Interval.ONE_MIN)
				.timeSeriesPoint(StockIDTimeSeriesPointAlphaVantage.class)
				.build();
				
		DatasetI dts = Controller.getDataset();
		dts.addTimeSeries(tr);	
		System.out.println();
	}
	
	//TODO
	//3) creare un datasetfactory che si prenda anche dati da csv o cmq file locali
	//4) sistemare il cleaner

}
