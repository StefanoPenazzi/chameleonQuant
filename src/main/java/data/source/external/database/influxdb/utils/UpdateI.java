/**
 * 
 */
package data.source.external.database.influxdb.utils;

import java.util.List;

/**
 * @author stefanopenazzi
 *
 */
public interface UpdateI {
	
	public void run(final List<String> series,final String database);

}
