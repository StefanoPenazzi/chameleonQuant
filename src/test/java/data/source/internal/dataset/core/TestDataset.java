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
import data.source.external.financialdatavendors.alphavantage.mirrors.StockEODTimeSeriesPointAlphaVantage;
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
		
         List<String> stocks = Arrays.asList("AACG","AACQ","AACQU","AACQW","AAL");
		
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Instant startInstant = (sdf.parse("2020-10-19 00:00:00")).toInstant();
		 Instant endInstant = null;
		 String market = "NASDAQ_EOD";
		 String inter = "1d";
		 
		  List<TimeSeriesRequestIdI> listQueries = new ArrayList<>();
		 
		 for(String stock: stocks) {
			 listQueries.add(new TimeSeriesRequestIdInfluxdb("influxdb","NASDAQ_EOD",stock,new TimeSeriesIdImpl (startInstant,endInstant,stock,inter),StockEODTimeSeriesPointInfluxdb.class));
		 }
		 listQueries.add(new TimeSeriesRequestIdInfluxdb("influxdb","FOREX_EOD","EUR-USD",new TimeSeriesIdImpl (startInstant,endInstant,"EUR-USD",inter),FOREXEODTimeSeriesPointInfluxdb.class));
		 listQueries.add(new TimeSeriesRequestIdAlphaVantage("alphavantage","","C",new TimeSeriesIdImpl (startInstant,endInstant,"C",inter),StockEODTimeSeriesPointAlphaVantage.class));
		 
		 Controller.run();
		 DatasetI dts = Controller.getDatasetFactory().create(listQueries);
		 TimeSeriesI its = dts.getTimeSeries(new TimeSeriesIdImpl (startInstant,endInstant,"C",inter));
		 System.out.println();
		
	}
	
	@Test
	void testDatasetAlphaVantageFactory() throws ParseException {
		
         List<String> stocks = Arrays.asList("AACG");
		
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Instant startInstant = (sdf.parse("2020-10-19 00:00:00")).toInstant();
		 Instant endInstant = null;
		 String market = "NASDAQ_EOD";
		 String inter = "1d";
		 
		  List<TimeSeriesRequestIdI> listQueries = new ArrayList<>();
		 
		 for(String stock: stocks) {
			 //listQueries.add(new TimeSeriesRequestIdAlphaVantage("alphavantage","NASDAQ_EOD",stock,new TimeSeriesId (startInstant,endInstant,market,stock,inter,StockEODTimeSeriesPointInfluxdb.class),StockEODTimeSeriesPointInfluxdb.class));
				
			 //listQueries.add(new TimeSeriesId (startInstant,endInstant,stock,inter));
		 }
		
		 Controller.run();
		 //DatasetI dts = Controller.getDatasetFactory().create(listQueries);
		 //TimeSeriesI<? extends TimeSeriesPointI> its = dts.getTimeSeries(new TimeSeriesId (startInstant,endInstant,market,"AACG",inter,StockEODTimeSeriesPointAlphaVantage.class));
		 System.out.println();
		
	}
	
	//TODO
	//3) creare un datasetfactory che si prenda anche dati da csv o cmq file locali
	//4) sistemare il cleaner

}
