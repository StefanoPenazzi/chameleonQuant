/**
 * 
 */
package data.source.internal.dataset.timeseries;

import java.time.Instant;
import java.util.Date;

/**
 * @author stefanopenazzi
 *
 */
public interface InternalTimeSeriesIdI {
	
	public String getId();
	public Instant getStartInstant();
	public Instant getEndInstant(); 
	//TODO this doesn t work for indicators gdp etc. Reflections are prob better 
	public String getInterval(); 
	public String getString();

}
