/**
 * 
 */
package strategies;

import data.source.internal.timeseries.TimeSeriesI;
import indicators.movingAverage.ExponentialMovingAverage;


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
    	
		 public TripleExpMovingAverageCrossoverStrategy build() throws Exception{
			 TripleExpMovingAverageCrossoverStrategy smas = new TripleExpMovingAverageCrossoverStrategy(); 
			 smas.itsRef = this.ts;
				smas.source = this.source;
				smas.targetRange = this.targetRange;
				smas.stma = new ExponentialMovingAverage.Builder(this.ts)
						.length(this.lengthStma)
						.source(this.source)
						.smoothing(this.smoothingStma)
						.build()
						.run();
				smas.mtma = new ExponentialMovingAverage.Builder(this.ts)
						.length(this.lengthMtma)
						.source(this.source)
						.smoothing(this.smoothingMtma)
						.build()
						.run();
				smas.ltma = new ExponentialMovingAverage.Builder(this.ts)
						.length(this.lengthLtma)
						.source(this.source)
						.smoothing(this.smoothingLtma)
						.build()
						.run();
			
            return smas;
		}		
	}
	
}

