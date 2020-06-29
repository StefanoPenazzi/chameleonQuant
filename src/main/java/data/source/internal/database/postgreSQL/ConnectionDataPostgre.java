/**
 * 
 */
package data.source.internal.database.postgreSQL;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.System.Logger.Level;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Logger;


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
	private String url;
	
	public ConnectionDataPostgre(String user,String password,String ip,String port,String database) {
		this.ip = ip;
		this.port = port;
		this.user = user;
		this.password = password;
		this.dataBase = database;
		this.url = "jdbc:postgresql://" +this.ip+":"+this.port +"/"+ this.dataBase;
	}
	
	public ConnectionDataPostgre(String path) {
		
		Properties props = new Properties();
        Path myPath = Paths.get("path");

        try {
            BufferedReader bf = Files.newBufferedReader(myPath, 
                StandardCharsets.UTF_8);

            props.load(bf);
            
            this.ip = "only in url";
    		this.port = "only in url";
    		this.user = props.getProperty("db.user");;
    		this.password = props.getProperty("db.password");;
    		this.dataBase = "only in url";
    		this.url = props.getProperty("db.url");;
            
            
        } catch (IOException ex) {
        	Logger lgr = Logger.getLogger(PreparedStatementPostgre.class.getName());
            lgr.log(java.util.logging.Level.SEVERE, ex.getMessage(), ex);
        }
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
		return this.url;
	}
	
}
