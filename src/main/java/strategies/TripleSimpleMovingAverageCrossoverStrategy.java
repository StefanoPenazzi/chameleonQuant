/**
 * 
 */
package strategies;

import data.source.internal.timeseries.TimeSeriesI;
import indicators.movingAverage.SimpleMovingAverage;

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
		
		private double targetRange = 1;
		
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
		
		 public TripleSimpleMovingAverageCrossoverStrategy build() throws Exception{
			TripleSimpleMovingAverageCrossoverStrategy  smas = new TripleSimpleMovingAverageCrossoverStrategy (); 
			
			smas.itsRef = this.ts;
			smas.source = this.source;
			smas.targetRange = this.targetRange;
			smas.stma = new SimpleMovingAverage.Builder(this.ts)
					.length(this.lengthStma)
					.source(this.source)
					.offset(this.offsetStma)
					.build()
					.run();
			smas.mtma = new SimpleMovingAverage.Builder(this.ts)
					.length(this.lengthMtma)
					.source(this.source)
					.offset(this.offsetMtma)
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
	
}

