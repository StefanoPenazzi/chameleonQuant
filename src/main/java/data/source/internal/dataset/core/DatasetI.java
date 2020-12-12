/**
 * 
 */
package data.source.internal.dataset.core;

import java.util.Iterator;

import data.source.internal.dataset.timeseries.InternalTimeSeriesAbstract;
import data.source.internal.dataset.timeseries.InternalTimeSeriesI;
import data.source.internal.dataset.timeseries.InternalTimeSeriesIdAbstract;
import data.source.internal.dataset.timeseries.InternalTimeSeriesIdI;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPointI;

/**
 * @author stefanopenazzi
 *
 */
public interface DatasetI {
	
	public void addTimeSeries(InternalTimeSeriesAbstract<? extends InternalTimeSeriesPointI> its);
	public boolean removeTimeSeries(InternalTimeSeriesIdAbstract itq);
	public InternalTimeSeriesAbstract<? extends InternalTimeSeriesPointI> getTimeSeries(InternalTimeSeriesIdAbstract itq);
	public Iterator<? extends InternalTimeSeriesAbstract<? extends InternalTimeSeriesPointI>> iterator();
	

}
