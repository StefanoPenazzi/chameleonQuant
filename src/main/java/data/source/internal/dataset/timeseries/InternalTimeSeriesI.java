/**
 * 
 */
package data.source.internal.dataset.timeseries;

import java.time.Duration;
import java.time.Instant;

import data.source.internal.dataset.timeseries.datastructure.TimeSeriesDataStructureI;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPoint;

/**
 * 
 * 
 * @author stefanopenazzi
 *
 */
public interface InternalTimeSeriesI<T extends InternalTimeSeriesPoint> {
	
	public TimeSeriesDataStructureI<T> getRange(Instant timeStart, Instant timeEnd);
	
	
	public T getPoint(Instant time);
	
	
	public T getCeilingPoint(Instant time);
	
	
	public T getFloorPoint(Instant time);
	
	
	public boolean getSingleInterval();
	
	
	public Duration getInterval();
	public Instant getFirstInstant();
	public Instant getLastInstant();
	public int hashCode();
	
	/**
	 * 
	 * 
	 *
	 */
	
	public InternalTimeSeriesQueryI getQuery();

}
