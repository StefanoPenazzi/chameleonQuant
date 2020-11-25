/**
 * 
 */
package data.source.internal.dataset.timeseries;

import java.util.List;

import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPoint;

/**
 * @author stefanopenazzi
 *
 */
public interface InternalTimeSeriesQueryRequestI<T extends InternalTimeSeriesPoint> {
	public List<T> getResult(InternalTimeSeriesQueryI iq);
}
