/**
 * 
 */
package data.source.internal.timeseries;

import java.util.List;

import data.source.SourceI;
import data.source.internal.timeseries.point.TimeSeriesPointI;


/**
 * @author stefanopenazzi
 *
 */
public interface TimeSeriesRequestI {
	public List<? extends TimeSeriesPointI> getTimeSeries(TimeSeriesRequestIdI iq, SourceI source);
	public TimeSeriesPointI getLastPoint(TimeSeriesRequestIdI iqp,SourceI source);
	public List<? extends TimeSeriesPointI> getTimeSeries(TimeSeriesRequestIdI iq);
	public TimeSeriesPointI getLastPoint(TimeSeriesRequestIdI iqp);
}
