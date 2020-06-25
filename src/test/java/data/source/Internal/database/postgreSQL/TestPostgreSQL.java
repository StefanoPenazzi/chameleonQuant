/**
 * 
 */
package data.source.Internal.database.postgreSQL;

import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author stefanopenazzi
 *
 */
class TestPostgreSQL {

	@Test
	void testConnection() {
	    String url = "jdbc:postgresql://127.0.0.1:5432/testdb";
        String user = "user1";
        String password = "user1";

        try (Connection con = DriverManager.getConnection(url, user, password);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT VERSION()")) {

            if (rs.next()) {
                System.out.println(rs.getString(1));
            }

        } catch (SQLException ex) {
        
            Logger lgr = Logger.getLogger(TestPostgreSQL.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
	}

}
