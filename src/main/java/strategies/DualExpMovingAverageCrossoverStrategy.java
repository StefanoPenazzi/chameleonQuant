/**
 * 
 */
package strategies;

import data.source.internal.timeseries.TimeSeriesI;
import indicators.movingAverage.ExponentialMovingAverage;
import strategies.positionsizing.FixedMoneyAmount;
import strategies.positionsizing.PositionSizingI;

/**
 * @author stefanopenazzi
 *
 */
public class DualExpMovingAverageCrossoverStrategy extends DualMovingAverageCrossoverStrategy  {
	

	public final static class Builder extends DualMovingAverageCrossoverStrategy.Builder<DualExpMovingAverageCrossoverStrategy,Builder> {
		
		private TimeSeriesI ts;
		private int lengthStma = 7;
		private int lengthLtma = 14;
		private String source = "close";
		private double smoothingStma = 2;
		private double smoothingLtma = 2;
		private PositionSizingI ps = new FixedMoneyAmount.Builder()
				.fixedMoneyAmount(10000)
				.build();
		
		public Builder(TimeSeriesI ts) {
	        this.ts = ts;
	    }
		public Builder lengthShortTermMA(int length){
            this.lengthStma = length;
            return this;
        }
		public Builder lengthLongTermMA(int length){
            this.lengthLtma = length;
            return this;
        }
		public Builder source(String source){
           this.source = source; 
            return this;
        }
		public Builder smoothingShortTermMA(double smoothingStma){
            this.smoothingStma = smoothingStma;
            return this;
        }
		public Builder smoothingLongTermMA(double smoothingLtma){
            this.smoothingLtma = smoothingLtma;
            return this;
        }
		public Builder positionSizing(PositionSizingI ps){
            this.ps = ps;
            return this;
        }
		 public DualExpMovingAverageCrossoverStrategy build() throws Exception{
			 DualExpMovingAverageCrossoverStrategy  demac = new DualExpMovingAverageCrossoverStrategy (this.ts,
					 this.lengthStma,
					 this.lengthLtma,
					 this.source,
					 this.smoothingStma,
					 this.smoothingLtma,
					 this.ps); 
            return demac;
		}		
	}
	
	public DualExpMovingAverageCrossoverStrategy(TimeSeriesI ts,
			int lengthShortTermMA,
			int lengthLongTermMA,
			String source,
			double smoothingShortTermMA,
			double smoothingLongTermMA,
			PositionSizingI positionSizing) {
		super(positionSizing);
		
		this.itsRef = ts;
		this.source = source;
		try {
			this.stma = new ExponentialMovingAverage.Builder(ts)
					.length(lengthShortTermMA)
					.source(source)
					.smoothing(smoothingShortTermMA)
					.build()
					.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			this.ltma = new ExponentialMovingAverage.Builder(ts)
					.length(lengthLongTermMA)
					.source(source)
					.smoothing(smoothingLongTermMA)
					.build()
					.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
