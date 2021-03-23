/**
 * 
 */
package strategies.positionsizing;

import strategies.StrategyI;

/**
 * @author stefanopenazzi
 *
 */
public final class FixedMoneyAmount implements PositionSizingI {
	
	private double fm;
	
	public final static class Builder{
		
		private double fm = 0;
		
		public Builder fixedMoneyAmount(double fm){
            this.fm = fm;
            return this;
        }
		 public FixedMoneyAmount build(){
			FixedMoneyAmount ps = new FixedMoneyAmount(this.fm); 
			return ps;
		}
		
	}
	
	@Deprecated
	public FixedMoneyAmount(double fm) {
		this.fm = fm;
	}

	@Override
	public int getSize(StrategyI strategy, double price) {
		return (int)Math.floor(this.fm/price);
	}

}

