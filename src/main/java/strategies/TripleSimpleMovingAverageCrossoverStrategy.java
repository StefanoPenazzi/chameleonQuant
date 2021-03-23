/**
 * 
 */
package strategies;

import data.source.internal.timeseries.TimeSeriesI;
import indicators.movingAverage.SimpleMovingAverage;
import strategies.DualExpMovingAverageCrossoverStrategy.Builder;
import strategies.positionsizing.FixedMoneyAmount;
import strategies.positionsizing.PositionSizingI;

/**
 * @author stefanopenazzi
 *
 */
public class TripleSimpleMovingAverageCrossoverStrategy extends TripleMovingAverageCrossoverStrategy  {
	
   

	public static class Builder extends TripleMovingAverageCrossoverStrategy.Builder<TripleSimpleMovingAverageCrossoverStrategy,Builder> {
		
		private TimeSeriesI ts;
		private int lengthStma = 7;
		private int lengthMtma = 14;
		private int lengthLtma = 14;
		private String source = "close";
		private int offsetStma = 0;
		private int offsetMtma = 0;
		private int offsetLtma = 0;
		private PositionSizingI ps = new FixedMoneyAmount.Builder()
				.fixedMoneyAmount(10000)
				.build();
		
		private double targetRange = 1;
		
		public Builder(TimeSeriesI ts) {
	        this.ts = ts;
	    }
		public Builder lengthShortTermMA(int length){
            this.lengthStma = length;
            return this;
        }
		public Builder lengthMediumTermMA(int length){
            this.lengthMtma = length;
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
		public Builder offsetShortTermMA(int offset){
            this.offsetStma = offset;
            return this;
        }
		public Builder offsetMediumTermMA(int offset){
            this.offsetMtma = offset;
            return this;
        }
		public Builder offsetLongTermMA(int offset){
            this.offsetLtma = offset;
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
		
		 public TripleSimpleMovingAverageCrossoverStrategy build() throws Exception{
			TripleSimpleMovingAverageCrossoverStrategy  smas = new TripleSimpleMovingAverageCrossoverStrategy (this.ts,
					this.lengthStma,
					this.lengthMtma,
					this.lengthLtma,
					this.source,
					this.offsetStma,
					this.offsetMtma,
					this.offsetLtma,
					this.targetRange,
					this.ps); 
            return smas;
		}		
	}
	
	
	public TripleSimpleMovingAverageCrossoverStrategy(TimeSeriesI ts,
			int lengthShortTermMA,
			int lengthMediumTermMA,
			int lengthLongTermMA,
			String source,
			int offsetShortTermMA,
			int offsetMediumTermMA,
			int offsetLongTermMA,
			double targetRange,
			PositionSizingI positionSizing) throws Exception {
		super(positionSizing);
		this.itsRef = ts;
		this.source = source;
		this.targetRange = targetRange;
		this.stma = new SimpleMovingAverage.Builder(ts)
				.length(lengthShortTermMA)
				.source(source)
				.offset(offsetShortTermMA)
				.build()
				.run();
		this.mtma = new SimpleMovingAverage.Builder(ts)
				.length(lengthMediumTermMA)
				.source(source)
				.offset(offsetMediumTermMA)
				.build()
				.run();
		this.ltma = new SimpleMovingAverage.Builder(ts)
				.length(lengthLongTermMA)
				.source(source)
				.offset(offsetLongTermMA)
				.build()
				.run();
		
	}

}

