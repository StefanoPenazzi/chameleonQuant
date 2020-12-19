/**
 * 
 */
package data.source.external.database.tickers;

import java.util.List;

/**
 * @author stefanopenazzi
 *
 */
public interface TickersSetI {
	
	public List<String> getTickersSet();
	public String getName();
	public List<String> getSubTickersSet(char c);
	public boolean contains(String s);

}
