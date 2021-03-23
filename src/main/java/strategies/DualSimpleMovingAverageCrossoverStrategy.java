/**
 * 
 */
package strategies;

import data.source.internal.timeseries.TimeSeriesI;
import indicators.movingAverage.SimpleMovingAverage;
import strategies.SingleExpMovingAverageStrategy.Builder;
import strategies.positionsizing.FixedMoneyAmount;
import strategies.positionsizing.PositionSizingI;


/**
 * @author stefanopenazzi
 *
 */
public final class DualSimpleMovingAverageCrossoverStrategy extends DualMovingAverageCrossoverStrategy  {


	public final static class Builder extends DualMovingAverageCrossoverStrategy.Builder<DualSimpleMovingAverageCrossoverStrategy,Builder> {
		
		private TimeSeriesI ts;
		private int lengthStma = 7;
		private int lengthLtma = 14;
		private String source = "close";
		private int offsetStma = 0;
		private int offsetLtma = 0;
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
		public Builder offsetShortTermMA(int offset){
            this.offsetStma = offset;
            return this;
        }
		public Builder offsetLongTermMA(int offset){
            this.offsetLtma = offset;
            return this;
        }
		public Builder positionSizing(PositionSizingI ps){
            this.ps = ps;
            return this;
        }
		 public DualSimpleMovingAverageCrossoverStrategy build() throws Exception{
			 DualSimpleMovingAverageCrossoverStrategy  smas = new DualSimpleMovingAverageCrossoverStrategy (this.ts,
						this.lengthStma,
						this.lengthLtma,
						this.source,
						this.offsetStma,
						this.offsetLtma,
						this.ps); 
			smas.itsRef = this.ts;
			smas.source = this.source;
			smas.stma = new SimpleMovingAverage.Builder(this.ts)
					.length(this.lengthStma)
					.source(this.source)
					.offset(this.offsetStma)
					.build()
					.run();
			smas.ltma = new SimpleMovingAverage.Builder(this.ts)
					.length(this.lengthLtma)
					.source(this.source)
					.offset(this.offsetLtma)
					.build()
					.run();
			
            return smas;
		}		
	}
	
	public DualSimpleMovingAverageCrossoverStrategy(TimeSeriesI ts,
			int lengthShortTermMA,
			int lengthLongTermMA,
			String source,
			int offsetShortTermMA,
			int offsetLongTermMA,
			PositionSizingI positionSizing) {
		super(positionSizing);
		this.itsRef = ts;
		this.source = source;
		try {
			this.stma = new SimpleMovingAverage.Builder(ts)
					.length(lengthShortTermMA)
					.source(source)
					.offset(offsetShortTermMA)
					.build()
					.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			this.ltma = new SimpleMovingAverage.Builder(ts)
					.length(lengthLongTermMA)
					.source(source)
					.offset(offsetLongTermMA)
					.build()
					.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}

