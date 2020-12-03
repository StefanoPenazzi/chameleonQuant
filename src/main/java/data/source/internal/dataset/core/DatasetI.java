/**
 * 
 */
package data.source.internal.dataset.core;

import data.source.internal.dataset.timeseries.InternalTimeSeriesI;
import data.source.internal.dataset.timeseries.InternalTimeSeriesQueryAbstract;
import data.source.internal.dataset.timeseries.InternalTimeSeriesQueryI;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPoint;

/**
 * @author stefanopenazzi
 *
 */
public interface DatasetI {
	
	public void addTimeSeries(InternalTimeSeriesI<? extends InternalTimeSeriesPoint> its);
	public boolean removeTimeSeries(InternalTimeSeriesQueryAbstract itq);
	public InternalTimeSeriesI<?> getTimeSeries(InternalTimeSeriesQueryAbstract itq);

}
