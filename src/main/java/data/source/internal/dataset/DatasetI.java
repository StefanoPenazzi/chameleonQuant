/**
 * 
 */
package data.source.internal.dataset;

import java.util.Iterator;

import data.source.internal.timeseries.TimeSeriesAbstract;
import data.source.internal.timeseries.TimeSeriesI;
import data.source.internal.timeseries.TimeSeriesIdAbstract;
import data.source.internal.timeseries.TimeSeriesIdI;
import data.source.internal.timeseries.point.TimeSeriesPointI;

/**
 * @author stefanopenazzi
 *
 */
public interface DatasetI {
	
	public void addTimeSeries(TimeSeriesAbstract<? extends TimeSeriesPointI> its);
	public boolean removeTimeSeries(TimeSeriesIdAbstract itq);
	public TimeSeriesAbstract<? extends TimeSeriesPointI> getTimeSeries(TimeSeriesIdAbstract itq);
	public Iterator<? extends TimeSeriesAbstract<? extends TimeSeriesPointI>> iterator();
	public void merge(DatasetI tail);
	
	

}
