/**
 * 
 */
package strategies.positionsizing;

import strategies.StrategyI;


/**
 * @author stefanopenazzi
 *
 */
public class InitialMoneyAmount implements PositionSizingI {
	
	private double fm;
	
	public final static class Builder{
		
		private double fm = 0;
		
		public Builder initialMoneyAmount(double fm){
            this.fm = fm;
            return this;
        }
		 public InitialMoneyAmount build(){
			 InitialMoneyAmount ps = new InitialMoneyAmount(this.fm); 
			return ps;
		}
		
	}
	
	@Deprecated
	public InitialMoneyAmount (double fm) {
		this.fm = fm;
	}

	@Override
	public int getSize(StrategyI strategy, double price) {
		return (int)Math.floor((this.fm+strategy.getTotNetProfit())/price);
	}

}