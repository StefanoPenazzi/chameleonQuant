/**
 * 
 */
package data.source.internal.dataset;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.inject.Inject;
import com.google.inject.Key;

import controller.Controller;
import data.source.internal.timeseries.TimeSeriesI;
import data.source.internal.timeseries.TimeSeriesIdAbstract;
import data.source.internal.timeseries.TimeSeriesIdI;
import data.source.internal.timeseries.TimeSeriesRequestI;
import data.source.internal.timeseries.TimeSeriesRequestIdI;
import data.source.internal.timeseries.standard.TimeSeriesFactoryImpl;


/**
 * @author stefanopenazzi
 *
 */
public class DatasetHistoricalImpl implements DatasetI {

	private Map<TimeSeriesIdI,TimeSeriesI> datasetMap = new ConcurrentHashMap<TimeSeriesIdI,TimeSeriesI>();
	
	@Inject
	public DatasetHistoricalImpl() {
	}
    
	@Override
	public void addTimeSeries(TimeSeriesI its) {
		datasetMap.put(its.getQuery(), its);
	}

	@Override
	public boolean removeTimeSeries(TimeSeriesI its) {
		if(datasetMap.remove(its) == null) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public TimeSeriesI getTimeSeries(TimeSeriesIdAbstract its) {
		return datasetMap.get(its);
	}

	@Override
	public Iterator<? extends TimeSeriesI> iterator() {
		return datasetMap.values().iterator();
	}

	@Override
	public void merge(DatasetI tail) {
		Iterator<TimeSeriesI> tailIterator = (Iterator<TimeSeriesI>) tail.iterator();
		while(tailIterator.hasNext()) {
			//TODO what happens if the id of a new internal time series is the same of one already in the map???
			this.addTimeSeries(tailIterator.next());
		}
	}

	@Override
	public void addTimeSeries(List<TimeSeriesRequestIdI> listOfId) {
		for(TimeSeriesRequestIdI id : listOfId) {
			addTimeSeries(id);
		}
	}

	@Override
	public void addTimeSeries(TimeSeriesRequestIdI id) {
		TimeSeriesFactoryImpl itsf  = Controller.getInjector().getInstance(new Key<TimeSeriesFactoryImpl>() {});
		this.addTimeSeries(itsf.createTimeSeriesQueryRequest(new ArrayList<String>(){{add("NULL_INFLUXDB");}},id));
	}

}
