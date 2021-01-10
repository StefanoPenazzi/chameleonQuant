/**
 * 
 */
package data.source.internal.timeseries;

import java.util.List;
import data.source.internal.timeseries.point.TimeSeriesPointI;


/**
 * @author stefanopenazzi
 *
 */
public interface TimeSeriesRequestI {
	public List<? extends TimeSeriesPointI> getTimeSeries(TimeSeriesRequestIdI iq);
}
