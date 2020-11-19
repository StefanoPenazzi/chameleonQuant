/**
 * 
 */
package data.source.internal.dataset.timeseries.point;

import java.util.Date;

/**
 * @author stefanopenazzi
 *
 */
public interface TimeSeriesPoint {
	
	
	public Date getDate();
	public Object[] getValues();
	

}
