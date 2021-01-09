/**
 * 
 */
package data.source.internal.timeseries;

import java.time.Instant;
import java.util.Date;

import data.source.internal.timeseries.point.TimeSeriesPointAbstract;
import data.source.internal.timeseries.point.TimeSeriesPointI;

/**
 * @author stefanopenazzi
 *
 */
public interface TimeSeriesIdI {
	
	public String getId();
	public Instant getStartInstant();
	public Instant getEndInstant(); 
	//TODO this doesn t work for indicators gdp etc. Reflections are prob better 
	public String getInterval(); 
	public String getString();
	public Class<? extends TimeSeriesPointI> getTimeSeriesPoint();

}
