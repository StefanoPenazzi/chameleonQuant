/**
 * 
 */
package data.source.internal.dataset.timeseries;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.Duration;
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
	public InternalTimeSeriesPoint getCeilingPoint(Instant time);
	public InternalTimeSeriesPoint getFloorPoint(Instant time);
	
	public String getId();
	public boolean getSingleInterval();
	public Duration getInterval();
	public Instant getFirstInstant();
	public Instant getLastInstant();
	public int hashCode();

}
