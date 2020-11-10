/**
 * 
 */
package data.source.external.database;

import java.util.List;

/**
 * @author stefanopenazzi
 *
 */
public interface Database {

	public void connect(String... con);
	
	public boolean pingServer();
	
	public boolean writingBatchFromCsvFile(String dbName, String table ,String csvFile, Class<? extends Mirror> mirror ,Object... options);
	
	public boolean writingBatchFromCsvString(String dbName, String csv, Class<? extends Mirror> mirror,Object... options );
	
	public List<? extends Mirror>  performQuery(String query,String databse,Class<? extends Mirror> cl);
	
	public void close();
	
}
