/**
 * 
 */
package data.source.external.database.tickers;

import java.util.List;

/**
 * @author stefanopenazzi
 *
 */
public class TickersSetImpl implements TickersSetI {

    private final List<String> tickersSet;
    private final String name;
    
    public TickersSetImpl(String name, List<String> tickersSet) {
    	this.tickersSet = tickersSet;
    	this.name = name;
    }
	
	@Override
	public List<String> getTickersSet() {
	    //TODO this has to be un unmodifiable list
		return this.tickersSet;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public List<String> getSubTickersSet(char c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(String s) {
		// TODO Auto-generated method stub
		return false;
	}

}
