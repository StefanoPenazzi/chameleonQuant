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
import data.source.external.database.influxdb.TimeSeriesRequestInfluxdb;
import data.source.external.database.influxdb.mirrors.StockEODTimeSeriesPointInfluxdb;
import data.source.internal.timeseries.TimeSeriesRequestI;
import data.source.internal.timeseries.TimeSeriesRequestIdI;
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
	public DatasetI create(List< TimeSeriesRequestIdI> listOfId) {
		DatasetImpl dts = new DatasetImpl();
		for(TimeSeriesRequestIdI id : listOfId) {
			TimeSeriesFactoryImpl itsf  = Controller.getInjector().getInstance(new Key<TimeSeriesFactoryImpl>() {});
			TimeSeriesRequestI itsq = mapTimeSeriesRequest.get(id.getSource());
			dts.addTimeSeries(itsf.createTimeSeriesQueryRequest(new ArrayList<String>(){{add("NULL_INFLUXDB");}},itsq,id));
		}
	    return dts;
	}
}
