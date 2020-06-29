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

import data.source.internal.database.ConnectionData;
import data.source.internal.database.PreparedStatementInterface;

/**
 * @author stefanopenazzi
 *
 */
public class PreparedStatementPostgre implements PreparedStatementInterface {

	public PreparedStatementPostgre(ConnectionData cd,String query) {
		String url = cd.getUrl();
        String user = cd.getUser();
        String password = cd.getPassword();

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {
            
            pst.executeUpdate();
            con.close();

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(PreparedStatementPostgre.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
	}
}
