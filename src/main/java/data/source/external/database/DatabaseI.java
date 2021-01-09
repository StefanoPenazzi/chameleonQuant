/**
 * 
 */
package data.source.external.database;

import java.util.List;
import java.util.Map;

/**
 * @author stefanopenazzi
 *
 */
public interface DatabaseI {

	public void connect();
	
	public boolean ping();
	
	public void update(String database,  String series , Class<? extends MirrorI> mirror ,List<Map<String,String>> csvMap);
	
	public List<? extends MirrorI>  select(String query,String database,Class<? extends MirrorI> cl);
	
	public void close();
	
}
