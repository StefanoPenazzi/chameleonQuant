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
public interface TimeSeriesDataStructureI extends Iterable<TimeSeriesPointI> {
	
	public TimeSeriesDataStructureI getRange(Instant startTime, Instant endTime);
	public TimeSeriesPointI getPoint(Instant time);
	public TimeSeriesPointI getFirst();
	public TimeSeriesPointI getLast();
	public TimeSeriesPointI getCeilingPoint(Instant time);
	public TimeSeriesPointI getFloorPoint(Instant time);
	public void addPoint(TimeSeriesPointI tsp);
	public void removePoint(TimeSeriesPointI tsp);
	public TimeSeriesPointI getPointByIndex(int i);
	public Iterator<TimeSeriesPointI> iterator();
	public List<TimeSeriesPointI> getList();

}
