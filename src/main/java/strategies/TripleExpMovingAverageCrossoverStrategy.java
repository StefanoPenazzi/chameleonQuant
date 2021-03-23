/**
 * 
 */
package strategies;

import data.source.internal.timeseries.TimeSeriesI;
import indicators.movingAverage.ExponentialMovingAverage;
import indicators.movingAverage.SimpleMovingAverage;
import strategies.DualExpMovingAverageCrossoverStrategy.Builder;
import strategies.positionsizing.FixedMoneyAmount;
import strategies.positionsizing.PositionSizingI;


/**
 * @author stefanopenazzi
 *
 */
public class TripleExpMovingAverageCrossoverStrategy extends TripleMovingAverageCrossoverStrategy  {

	public static class Builder extends TripleMovingAverageCrossoverStrategy.Builder<TripleExpMovingAverageCrossoverStrategy,Builder> {
		
		private TimeSeriesI ts;
		private int lengthStma = 7;
		private int lengthMtma = 14;
		private int lengthLtma = 28;
		private String source = "close";
		private double smoothingStma = 2;
		private double smoothingMtma = 2;
		private double smoothingLtma = 2;
		
		private double targetRange = 1;
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
		public Builder lengthMediumTermMA(int length){
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
		public Builder smoothingMediumTermMA(double smoothingMtma){
            this.smoothingMtma = smoothingMtma;
            return this;
        }
		public Builder smoothingLongTermMA(double smoothingLtma){
            this.smoothingLtma = smoothingLtma;
            return this;
        }
		
		public Builder targetRange(double targetRange){
            this.targetRange = targetRange;
            return this;
        }
		public Builder positionSizing(PositionSizingI ps){
            this.ps = ps;
            return this;
        }
		
    	
		 public TripleExpMovingAverageCrossoverStrategy build() throws Exception{
			 TripleExpMovingAverageCrossoverStrategy smas = new TripleExpMovingAverageCrossoverStrategy(this.ts,
						this.lengthStma,
						this.lengthMtma,
						this.lengthLtma,
						this.source,
						this.smoothingStma,
						this.smoothingMtma,
						this.smoothingLtma,
						this.targetRange,
						this.ps);
            return smas;
		}		
	}
	
	public TripleExpMovingAverageCrossoverStrategy(TimeSeriesI ts,
			int lengthShortTermMA,
			int lengthMediumTermMA,
			int lengthLongTermMA,
			String source,
			double smoothingShortTermMA,
			double smoothingMediumTermMA,
			double smoothingLongTermMA,
			double targetRange,
			PositionSizingI positionSizing) throws Exception {
		super(positionSizing);
		
		this.itsRef = ts;
		this.source = source;
		this.targetRange = targetRange;
		this.stma = new ExponentialMovingAverage.Builder(ts)
				.length(lengthShortTermMA)
				.source(source)
				.smoothing(smoothingShortTermMA)
				.build()
				.run();
		this.mtma = new ExponentialMovingAverage.Builder(ts)
				.length(lengthMediumTermMA)
				.source(source)
				.smoothing(smoothingMediumTermMA)
				.build()
				.run();
		this.ltma = new ExponentialMovingAverage.Builder(ts)
				.length(lengthLongTermMA)
				.source(source)
				.smoothing(smoothingLongTermMA)
				.build()
				.run();
		
	}
	
}

