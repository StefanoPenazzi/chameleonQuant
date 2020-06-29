/**
 * 
 */
package data.source.internal.database.postgreSQL;

import data.source.internal.database.ConnectionData;

/**
 * @author stefanopenazzi
 *
 */
public class ConnectionDataPostgre implements ConnectionData {

	private String ip;
	private String port;
	private String user;
	private String password;
	private String dataBase;
	
	public ConnectionDataPostgre(String user,String password,String ip,String port,String database) {
		this.ip = ip;
		this.port = port;
		this.user = user;
		this.password = password;
		this.dataBase = database;
	}
	
	public String getIp() {
		return this.ip;
	}
	public String getPort() {
		return this.port;
	}
	public String getUser() {
		return this.user;
	}
	public String getPassword() {
		return this.password;
	}
	public String getDatabase() {
		return this.dataBase;
	}
	public String getUrl() {
		String s ="jdbc:postgresql://" +this.ip+":"+this.port +"/"+ this.dataBase;
		return s;
	}
	
}
