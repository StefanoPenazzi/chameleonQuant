/**
 * 
 */
package data.source.external.database.influxdb.utils.update;

import java.util.List;

/**
 * @author stefanopenazzi
 *
 */
public interface UpdateI {
	
	/**
	 * @param series List of tickers. The tickers must be present in the database.  
	 * @param database Name of the database in which save the tickers values.
	 */
	public void run(final List<String> series,final String database);

}
