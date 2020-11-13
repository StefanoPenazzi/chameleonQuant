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
	
	public Date getStartDate();
	public Date getCentralDate();
	public Date getEndDate();
	public Object[] getValue();
	

}
