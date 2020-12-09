/**
 * 
 */
package data.source.internal.dataset.core;

import java.util.Iterator;

import data.source.internal.dataset.timeseries.InternalTimeSeriesAbstract;
import data.source.internal.dataset.timeseries.InternalTimeSeriesI;
import data.source.internal.dataset.timeseries.InternalTimeSeriesIdAbstract;
import data.source.internal.dataset.timeseries.InternalTimeSeriesIdI;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPoint;

/**
 * @author stefanopenazzi
 *
 */
public interface DatasetI {
	
	public void addTimeSeries(InternalTimeSeriesAbstract<? extends InternalTimeSeriesPoint> its);
	public boolean removeTimeSeries(InternalTimeSeriesIdAbstract itq);
	public InternalTimeSeriesAbstract<? extends InternalTimeSeriesPoint> getTimeSeries(InternalTimeSeriesIdAbstract itq);
	public Iterator<InternalTimeSeriesAbstract<? extends InternalTimeSeriesPoint>> iterator();
	

}
