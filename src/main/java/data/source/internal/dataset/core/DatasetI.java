/**
 * 
 */
package data.source.internal.dataset.core;

import data.source.internal.dataset.timeseries.InternalTimeSeriesI;
import data.source.internal.dataset.timeseries.InternalTimeSeriesQueryI;

/**
 * @author stefanopenazzi
 *
 */
public interface DatasetI {
	
	public void addTimeSeries(InternalTimeSeriesI its);
	public boolean removeTimeSeries(InternalTimeSeriesQueryI itq);
	public InternalTimeSeriesI getTimeSeries(InternalTimeSeriesQueryI itq);

}
