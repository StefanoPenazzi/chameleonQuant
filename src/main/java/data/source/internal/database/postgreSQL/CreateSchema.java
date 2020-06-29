/**
 * 
 */
package data.source.internal.database.postgreSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author stefanopenazzi
 *
 */
public class CreateSchema {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		createSchema(args[0],args[1],args[2],args[3],args[4]);
	}
	
	public static void createSchema(String urlPar,String userPar,String passwordPar,String authorPar,String queryPar) {
		
		String url = urlPar;
        String user = userPar;
        String password = passwordPar;
        String author = authorPar;
        String query = queryPar;

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {
            
            pst.executeUpdate();
            con.close();

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(CreateSchema.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
	}

}
