/**
 * 
 */
package data.source.internal.dataset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Key;
import com.google.inject.multibindings.MapBinder;

import controller.Controller;
import data.source.external.database.influxdb.TimeSeriesId;
import data.source.external.database.influxdb.TimeSeriesRequestInfluxdb;
import data.source.external.database.influxdb.mirrors.StockEODTimeSeriesPointInfluxdb;
import data.source.internal.timeseries.TimeSeriesRequestI;
import data.source.internal.timeseries.cleaning.TimeSeriesCleanerI;
import data.source.internal.timeseries.point.TimeSeriesPointAbstract;
import data.source.internal.timeseries.point.TimeSeriesPointI;
import data.source.internal.timeseries.standard.TimeSeriesFactoryImpl;

/**
 * @author stefanopenazzi
 *
 */
public class DatasetFactoryImpl implements DatasetFactoryI {
	
	private final Map<String,TimeSeriesRequestI> mapTimeSeriesRequest;
	
	@Inject
	public DatasetFactoryImpl(Map<String,TimeSeriesRequestI> mapTimeSeriesRequest) {
		this.mapTimeSeriesRequest = mapTimeSeriesRequest;
	}

	@Override
	public DatasetI create(List<TimeSeriesId> listOfId) {
		DatasetImpl dts = new DatasetImpl();
		for(TimeSeriesId id : listOfId) {
			TimeSeriesFactoryImpl<StockEODTimeSeriesPointInfluxdb> itsf  = Controller.getInjector().getInstance(new Key<TimeSeriesFactoryImpl<StockEODTimeSeriesPointInfluxdb>>() {});
			TimeSeriesRequestI itsq = mapTimeSeriesRequest.get("alphavantage");
			//TimeSeriesRequestInfluxdb<StockEODTimeSeriesPointInfluxdb> itsq = new TimeSeriesRequestInfluxdb<StockEODTimeSeriesPointInfluxdb>(StockEODTimeSeriesPointInfluxdb.class);
			dts.addTimeSeries(itsf.createTimeSeriesQueryRequest(new ArrayList<String>(){{add("NULL_INFLUXDB");}},itsq,id));
		}
	    return dts;
		//return null;
	}

}
