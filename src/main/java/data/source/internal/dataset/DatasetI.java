/**
 * 
 */
package data.source.internal.dataset;

import java.util.Iterator;
import java.util.List;
import data.source.internal.timeseries.TimeSeriesI;
import data.source.internal.timeseries.TimeSeriesIdAbstract;
import data.source.internal.timeseries.TimeSeriesRequestIdI;


/**
 * @author stefanopenazzi
 *
 */
public interface DatasetI {
	public void addTimeSeries(TimeSeriesI its);
	public void addTimeSeries(List<TimeSeriesRequestIdI> listOfId);
	public void addTimeSeries(TimeSeriesRequestIdI listOfId);
	public boolean removeTimeSeries(TimeSeriesI itq);
	public TimeSeriesI getTimeSeries(TimeSeriesIdAbstract itq);
	public Iterator<? extends TimeSeriesI> iterator();
	public void merge(DatasetI tail);
}
