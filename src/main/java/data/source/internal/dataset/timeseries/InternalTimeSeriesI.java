/**
 * 
 */
package data.source.internal.dataset.timeseries;

import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import data.source.internal.dataset.timeseries.datastructure.TimeSeriesDataStructureI;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPointI;

/**
 * 
 * 
 * @author stefanopenazzi
 *
 */
public interface InternalTimeSeriesI<T extends InternalTimeSeriesPointI> {
	
	public TimeSeriesDataStructureI<T> getRange(Instant timeStart, Instant timeEnd);
	
	
	public T getPoint(Instant time);
	
	
	public T getCeilingPoint(Instant time);
	
	
	public T getFloorPoint(Instant time);
	
	
	public boolean getSingleInterval();
	
	public Duration getInterval();
	public Instant getFirstInstant();
	public Instant getLastInstant();
	public int hashCode();
	
	public InternalTimeSeriesIdAbstract getQuery();
	
	public Iterator<T> iterator();
	
	public List<T> getList();
	public String getString();
}
