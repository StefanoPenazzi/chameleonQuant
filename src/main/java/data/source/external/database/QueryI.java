/**
 * 
 */
package data.source.external.database;

import java.util.List;

import data.source.internal.timeseries.TimeSeriesIdI;
import data.source.internal.timeseries.point.TimeSeriesPointI;

/**
 * @author stefanopenazzi
 *
 */
public interface QueryI {
	public List<? extends TimeSeriesPointI> select(TimeSeriesIdI iq);
}
