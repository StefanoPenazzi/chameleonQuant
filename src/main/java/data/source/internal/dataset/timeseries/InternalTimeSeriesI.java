/**
 * 
 */
package data.source.internal.dataset.timeseries;

import java.time.Instant;

import data.source.internal.dataset.timeseries.datastructure.TimeSeriesDataStructureI;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPoint;

/**
 * @author stefanopenazzi
 *
 */
public interface InternalTimeSeriesI {
	
	public TimeSeriesDataStructureI getRange(Instant timeStart, Instant timeEnd);
	public InternalTimeSeriesPoint getPoint(Instant time);
	public InternalTimeSeriesPoint getUpwardsPoint(Instant time);
	public InternalTimeSeriesPoint getDownwardsPoint(Instant time);

}
