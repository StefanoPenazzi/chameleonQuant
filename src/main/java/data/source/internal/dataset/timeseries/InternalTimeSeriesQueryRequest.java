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
public interface InternalTimeSeriesQueryRequest {
	public List<? extends InternalTimeSeriesPoint> getResult(InternalTimeSeriesQueryI iq);
}
