/**
 * 
 */
package data.source.internal.timeseries;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import data.source.internal.timeseries.point.TimeSeriesPointI;
import data.source.internal.timeseries.structure.TimeSeriesDataStructureI;

/**
 * 
 * 
 * @author stefanopenazzi
 *
 */
public interface TimeSeriesI<T extends TimeSeriesPointI> {
	
	public TimeSeriesDataStructureI<T> getRange(Instant timeStart, Instant timeEnd);
	
	
	public T getPoint(Instant time);
	
	
	public T getCeilingPoint(Instant time);
	
	
	public T getFloorPoint(Instant time);
	
	
	public boolean getSingleInterval();
	
	public Duration getInterval();
	public Instant getFirstInstant();
	public Instant getLastInstant();
	public int hashCode();
	
	public TimeSeriesIdAbstract getQuery();
	
	public Iterator<T> iterator();
	
	public List<T> getList();
	public String getString();
	
	public Method getTagMethod(String tagName);
}
