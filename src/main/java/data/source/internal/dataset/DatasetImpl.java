/**
 * 
 */
package data.source.internal.dataset;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import data.source.internal.timeseries.TimeSeriesAbstract;
import data.source.internal.timeseries.TimeSeriesI;
import data.source.internal.timeseries.TimeSeriesIdAbstract;
import data.source.internal.timeseries.TimeSeriesIdI;
import data.source.internal.timeseries.point.TimeSeriesPointI;


/**
 * @author stefanopenazzi
 *
 */
public class DatasetImpl implements DatasetI {

	private Map<TimeSeriesIdI,TimeSeriesI> datasetMap = new ConcurrentHashMap<TimeSeriesIdI,TimeSeriesI>();
	
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
}
