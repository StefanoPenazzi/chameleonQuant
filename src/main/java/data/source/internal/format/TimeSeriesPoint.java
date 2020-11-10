/**
 * 
 */
package data.source.internal.format;

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
