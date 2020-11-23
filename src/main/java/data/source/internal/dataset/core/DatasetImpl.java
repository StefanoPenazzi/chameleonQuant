/**
 * 
 */
package data.source.internal.dataset.core;

import java.util.HashMap;
import java.util.Map;

import data.source.internal.dataset.timeseries.InternalTimeSeriesI;
import data.source.internal.dataset.timeseries.InternalTimeSeriesQueryI;

/**
 * @author stefanopenazzi
 *
 */
public class DatasetImpl implements DatasetI {

	private Map<InternalTimeSeriesQueryI,InternalTimeSeriesI> datasetMap = new HashMap<>();
	
	@Override
	public void addTimeSeries(InternalTimeSeriesI its) {
		datasetMap.put(its.getQuery(), its);
	}

	@Override
	public boolean removeTimeSeries(InternalTimeSeriesQueryI its) {
		return false;
	}

	@Override
	public InternalTimeSeriesI getTimeSeries(InternalTimeSeriesQueryI its) {
		return null;
	}

	
	
}
