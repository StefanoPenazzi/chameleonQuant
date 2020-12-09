/**
 * 
 */
package data.source.internal.dataset.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import data.source.internal.dataset.timeseries.InternalTimeSeriesAbstract;
import data.source.internal.dataset.timeseries.InternalTimeSeriesI;
import data.source.internal.dataset.timeseries.InternalTimeSeriesIdAbstract;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPoint;


/**
 * @author stefanopenazzi
 *
 */
public class DatasetImpl implements DatasetI {

	private Map<InternalTimeSeriesIdAbstract,InternalTimeSeriesAbstract<? extends InternalTimeSeriesPoint>> datasetMap = new HashMap<InternalTimeSeriesIdAbstract,InternalTimeSeriesAbstract<? extends InternalTimeSeriesPoint>>();
	
	@Override
	public void addTimeSeries(InternalTimeSeriesAbstract<? extends InternalTimeSeriesPoint> its) {
		datasetMap.put(its.getQuery(), its);
	}

	@Override
	public boolean removeTimeSeries(InternalTimeSeriesIdAbstract its) {
		if(datasetMap.remove(its) == null) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public InternalTimeSeriesAbstract<? extends InternalTimeSeriesPoint> getTimeSeries(InternalTimeSeriesIdAbstract its) {
		
//		Iterator it = datasetMap.entrySet().iterator();
//	    while (it.hasNext()) {
//	        Map.Entry pair = (Map.Entry)it.next();
//	        System.out.println(pair.getKey().hashCode());
//	    }
//	    
//	    System.out.println("-----------------");
//	    System.out.println(its.hashCode());
	    
		
		return datasetMap.get(its);
	}

	@Override
	public Iterator<InternalTimeSeriesAbstract<? extends InternalTimeSeriesPoint>> iterator() {
		return datasetMap.values().iterator();
	}
}
