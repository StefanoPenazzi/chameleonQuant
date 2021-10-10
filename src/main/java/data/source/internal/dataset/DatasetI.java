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
	
	public void addTimeSeries(TimeSeriesI its);
	public boolean removeTimeSeries(TimeSeriesI itq);
	public TimeSeriesI getTimeSeries(TimeSeriesIdAbstract itq);
	public Iterator<? extends TimeSeriesI> iterator();
	public void merge(DatasetI tail);
	public void update();
	
	

}
