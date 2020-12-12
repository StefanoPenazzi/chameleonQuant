/**
 * 
 */
package data.source.internal.dataset.timeseries;

import java.util.Date;

/**
 * @author stefanopenazzi
 *
 */
public interface InternalTimeSeriesIdI {
	
	public String getId();
	public Date getStartDate();
	public Date getEndDate(); 
	//TODO this doesn t work for indicators gdp etc. Reflections are prob better 
	public String getInterval(); 
	public String getString();

}
