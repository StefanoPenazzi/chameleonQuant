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
public class DualExpMovingAverageCrossoverStrategy extends DualMovingAverageCrossoverStrategy  {
	
    public final static class Builder extends DualMovingAverageCrossoverStrategy.Builder<DualExpMovingAverageCrossoverStrategy,Builder> {
		
		private TimeSeriesI ts;
		private int lengthStma = 7;
		private int lengthLtma = 14;
		private String source = "close";
		private double smoothingStma = 2;
		private double smoothingLtma = 2;
		
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
		public Builder offsetShortTermMA(double smoothingStma){
            this.smoothingStma = smoothingStma;
            return this;
        }
		public Builder offsetLongTermMA(double smoothingLtma){
            this.smoothingLtma = smoothingLtma;
            return this;
        }
		 public DualExpMovingAverageCrossoverStrategy build() throws Exception{
			 DualExpMovingAverageCrossoverStrategy  demac = new DualExpMovingAverageCrossoverStrategy (); 
			 demac.itsRef = this.ts;
			 demac.source = this.source;
			 demac.stma = new ExponentialMovingAverage.Builder(this.ts)
					.length(this.lengthStma)
					.source(this.source)
					.smoothing(this.smoothingStma)
					.build()
					.run();
			 demac.ltma = new ExponentialMovingAverage.Builder(this.ts)
					.length(this.lengthLtma)
					.source(this.source)
					.smoothing(this.smoothingLtma)
					.build()
					.run();
			
            return demac;
		}		
	}
}
