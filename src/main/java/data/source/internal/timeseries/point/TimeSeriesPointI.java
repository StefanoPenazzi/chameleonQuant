/**
 * 
 */
package data.source.internal.timeseries.point;

import java.lang.reflect.Method;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @author stefanopenazzi
 *
 */
public interface TimeSeriesPointI {
	
	public Instant getTime();
	public String getString();
	public Method getTagMethod(String tagName);
	public Object getTagValue(String tagName);
	public DateTimeFormatter getTimeFormat();
	

}
