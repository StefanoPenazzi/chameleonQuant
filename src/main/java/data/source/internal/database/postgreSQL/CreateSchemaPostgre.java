/**
 * 
 */
package data.source.internal.database.postgreSQL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import data.source.internal.database.ConnectionData;

/**
 * @author stefanopenazzi
 *
 */
public class CreateSchemaPostgre {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		 ConnectionDataPostgre cdp = new ConnectionDataPostgre(args[0],args[1],args[2],args[3],args[4]);
		 File file = new File(args[5]);  
		 BufferedReader br = new BufferedReader(new FileReader(file)); 
		 String st; 
		 while ((st = br.readLine()) != null) { 
		 } 
		 createSchema(cdp,st);
	}
	
    public static void createSchema(ConnectionDataPostgre cd,String query) {
    	PreparedStatementPostgre psp = new PreparedStatementPostgre(cd,query);
	}

}
