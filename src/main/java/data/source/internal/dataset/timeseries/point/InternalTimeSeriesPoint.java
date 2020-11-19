/**
 * 
 */
package data.source.internal.dataset.timeseries.point;

import java.time.Instant;

/**
 * @author stefanopenazzi
 *
 */
public interface InternalTimeSeriesPoint {
	
	
	public Instant getTime();
	public Object[] getValues();
	

}
