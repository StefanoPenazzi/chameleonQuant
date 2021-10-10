/**
 * 
 */
package data.source.external.database;

import java.util.List;
import java.util.Map;

import data.source.SourceI;
import data.source.internal.timeseries.point.TimeSeriesPointI;

/**
 * @author stefanopenazzi
 *
 */
public interface DatabaseI extends SourceI{
	
	public boolean ping();
	
	public void update(String database,  String series , Class<? extends TimeSeriesPointI> mirror ,List<Map<String,String>> csvMap);
	
	public List<? extends TimeSeriesPointI>  select(String query,String database,Class<? extends TimeSeriesPointI> cl);
	
}
