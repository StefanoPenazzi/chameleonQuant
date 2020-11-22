/**
 * 
 */
package data.source.internal.dataset.timeseries.datastructure;

import java.time.Instant;
import java.util.Date;
import java.util.Iterator;

import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPoint;

/**
 * @author stefanopenazzi
 *
 */
public interface TimeSeriesDataStructureI {
	
	public TimeSeriesDataStructureI getRange(Instant startTime, Instant endTime);
	public InternalTimeSeriesPoint getPoint(Instant time);
	public InternalTimeSeriesPoint getFirst();
	public InternalTimeSeriesPoint getLast();
	public InternalTimeSeriesPoint getCeilingPoint(Instant time);
	public InternalTimeSeriesPoint getFloorPoint(Instant time);
	public void addPoint(InternalTimeSeriesPoint tsp);
	public void removePoint(InternalTimeSeriesPoint tsp);
	public Iterator<InternalTimeSeriesPoint> iterator();

}
