/**
 * 
 */
package data.source.internal.database.postgreSQL;

/**
 * @author stefanopenazzi
 *
 */
public interface ConnectionData {
	
	public String getIp();
	public String getPort();
	public String getUser();
	public String getPassword();
	public String getDatabase();
	public String getUrl();

}
