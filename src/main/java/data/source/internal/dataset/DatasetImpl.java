/**
 * 
 */
package data.source.internal.dataset;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import data.source.internal.timeseries.TimeSeriesAbstract;
import data.source.internal.timeseries.TimeSeriesI;
import data.source.internal.timeseries.TimeSeriesIdAbstract;
import data.source.internal.timeseries.point.TimeSeriesPointI;


/**
 * @author stefanopenazzi
 *
 */
public class DatasetImpl implements DatasetI {

	private Map<TimeSeriesIdAbstract,TimeSeriesAbstract<? extends TimeSeriesPointI>> datasetMap = new HashMap<TimeSeriesIdAbstract,TimeSeriesAbstract<? extends TimeSeriesPointI>>();
	
	@Override
	public void addTimeSeries(TimeSeriesAbstract<? extends TimeSeriesPointI> its) {
		datasetMap.put(its.getQuery(), its);
	}

	@Override
	public boolean removeTimeSeries(TimeSeriesIdAbstract its) {
		if(datasetMap.remove(its) == null) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public TimeSeriesAbstract<? extends TimeSeriesPointI> getTimeSeries(TimeSeriesIdAbstract its) {
		
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
	public Iterator<TimeSeriesAbstract<? extends TimeSeriesPointI>> iterator() {
		return datasetMap.values().iterator();
	}

	@Override
	public void merge(DatasetI tail) {
		Iterator<TimeSeriesAbstract<? extends TimeSeriesPointI>> tailIterator = (Iterator<TimeSeriesAbstract<? extends TimeSeriesPointI>>) tail.iterator();
		while(tailIterator.hasNext()) {
			//TODO what happens if the id of a new internal time series is the same of one already in the map???
			this.addTimeSeries(tailIterator.next());
		}
	}
}
