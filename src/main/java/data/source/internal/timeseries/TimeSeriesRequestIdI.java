/**
 * 
 */
package data.source.internal.timeseries;

import java.time.Instant;

import data.source.internal.timeseries.point.TimeSeriesPointI;

/**
 * @author stefanopenazzi
 *
 */
public interface TimeSeriesRequestIdI {
	
	public String getSource();
	public TimeSeriesIdI getTimeSeriesId();
	public Class<? extends TimeSeriesPointI> getTimeSeriesPoint();
	
	public Object getId();
	public Object getStartTime();
	public Object getEndTime();  
	public Object getInterval();

}
