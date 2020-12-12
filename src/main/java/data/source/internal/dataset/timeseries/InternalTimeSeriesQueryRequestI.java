/**
 * 
 */
package data.source.internal.dataset.timeseries;

import java.util.List;

import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPointI;

/**
 * @author stefanopenazzi
 *
 */
public interface InternalTimeSeriesQueryRequestI<T extends InternalTimeSeriesPointI> {
	public List<T> getResult(InternalTimeSeriesIdI iq);
}
