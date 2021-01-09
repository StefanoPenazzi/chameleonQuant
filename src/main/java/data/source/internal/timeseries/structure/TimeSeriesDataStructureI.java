/**
 * 
 */
package data.source.internal.timeseries.structure;

import java.time.Instant;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import data.source.internal.timeseries.point.TimeSeriesPointI;

/**
 * @author stefanopenazzi
 *
 */
public interface TimeSeriesDataStructureI<T extends TimeSeriesPointI> extends Iterable<T> {
	
	public TimeSeriesDataStructureI getRange(Instant startTime, Instant endTime);
	public T getPoint(Instant time);
	public T getFirst();
	public T getLast();
	public T getCeilingPoint(Instant time);
	public T getFloorPoint(Instant time);
	public void addPoint(T tsp);
	public void removePoint(T tsp);
	public T getPointByIndex(int i);
	public Iterator<T> iterator();
	public List<T> getList();

}
