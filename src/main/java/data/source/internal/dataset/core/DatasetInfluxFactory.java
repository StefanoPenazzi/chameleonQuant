/**
 * 
 */
package data.source.internal.dataset.core;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Key;

import controller.Controller;
import data.source.external.database.influxdb.InternalStockTimeSeriesQueryInfluxdb;
import data.source.external.database.influxdb.InternalTimeSeriesQueryRequestInfluxdb;
import data.source.external.database.influxdb.mirrors.alphaVantage.StockEODTimeSeriesPointInfluxdb;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPointAbstract;
import data.source.internal.dataset.timeseries.standard.InternalTimeSeriesFactoryImpl;

/**
 * @author stefanopenazzi
 *
 */
public class DatasetInfluxFactory implements DatasetFactoryI {
	
	private final List<InternalStockTimeSeriesQueryInfluxdb> listOfId;
	
	
	public DatasetInfluxFactory(List<InternalStockTimeSeriesQueryInfluxdb> listOfId) {
		this.listOfId = listOfId;
	}

	@Override
	public DatasetI create() {
		DatasetImpl dts = new DatasetImpl();
		for(InternalStockTimeSeriesQueryInfluxdb id : listOfId) {
			InternalTimeSeriesFactoryImpl<StockEODTimeSeriesPointInfluxdb> itsf  = Controller.getInjector().getInstance(new Key<InternalTimeSeriesFactoryImpl<StockEODTimeSeriesPointInfluxdb>>() {});
			InternalTimeSeriesQueryRequestInfluxdb<StockEODTimeSeriesPointInfluxdb> itsq = new InternalTimeSeriesQueryRequestInfluxdb<StockEODTimeSeriesPointInfluxdb>(StockEODTimeSeriesPointInfluxdb.class);
			dts.addTimeSeries(itsf.createTimeSeriesQueryRequest(new ArrayList<String>(){{add("NULL_INFLUXDB");}},itsq,id));
		}
	    return dts;
		//return null;
	}

}
