/**
 * 
 */
package data.source.internal.dataset.timeseries.point;

import java.time.Instant;
import java.util.Map;

/**
 * @author stefanopenazzi
 *
 */
public interface InternalTimeSeriesPoint {
	
	
	public Instant getTime();
	public Map<String,Object> getTagsMap();
	public String getString();
	

}
