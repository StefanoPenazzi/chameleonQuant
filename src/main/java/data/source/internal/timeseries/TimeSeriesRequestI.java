/**
 * 
 */
package data.source.internal.timeseries;

import java.util.List;

import data.source.external.database.influxdb.TimeSeriesId;
import data.source.internal.timeseries.point.TimeSeriesPointAbstract;
import data.source.internal.timeseries.point.TimeSeriesPointI;


/**
 * @author stefanopenazzi
 *
 */
public interface TimeSeriesRequestI {
	public List<? extends TimeSeriesPointI> getTimeSeries(TimeSeriesId iq);
}
