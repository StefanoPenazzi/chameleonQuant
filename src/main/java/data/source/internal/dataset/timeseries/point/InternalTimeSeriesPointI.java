/**
 * 
 */
package data.source.internal.dataset.timeseries.point;

import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Map;

/**
 * @author stefanopenazzi
 *
 */
public interface InternalTimeSeriesPointI {
	
	public Instant getTime();
	public String getString();
	public Method getTagMethod(String tagName);
	public Object getTagValue(String tagName);
	

}
