/**
 * 
 */
package data.source.external.database;

import java.util.List;

import data.source.internal.dataset.timeseries.InternalTimeSeriesQueryI;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPoint;

/**
 * @author stefanopenazzi
 *
 */
public interface QueryDbI {
	public List<? extends InternalTimeSeriesPoint> getResult(InternalTimeSeriesQueryI iq);

}
