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
public interface TimeSeriesI {
	
	public TimeSeriesDataStructureI getRange(Instant timeStart, Instant timeEnd);
	public TimeSeriesPointI getPoint(Instant time);
	public TimeSeriesPointI getCeilingPoint(Instant time);
	public TimeSeriesPointI getFloorPoint(Instant time);
	public boolean getSingleInterval();
	public Duration getInterval();
	public Instant getFirstInstant();
	public Instant getLastInstant();
	public int hashCode();
	public TimeSeriesIdI getQuery();
	public Iterator<TimeSeriesPointI> iterator();
	public List<TimeSeriesPointI> getList();
	public List<TimeSeriesPointI> getListFrom(Instant time);
	public List<TimeSeriesPointI> getListTo(Instant time);
	public List<TimeSeriesPointI> getListFromTo(Instant timeFrom,Instant timeTo);
	public List<TimeSeriesPointI> getComparableList(TimeSeriesI ts);
	public List<TimeSeriesPointI> getComparableList(List<TimeSeriesPointI> ts);
	public void add(TimeSeriesPointI ts);
	public String getString();
	public Method getTagMethod(String tagName);
	
}
