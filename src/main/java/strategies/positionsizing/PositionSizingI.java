/**
 * 
 */
package strategies.positionsizing;

import strategies.StrategyI;

/**
 * @author stefanopenazzi
 *
 */
public interface PositionSizingI {
	
	public int getSize(StrategyI strategy, double price);

}
