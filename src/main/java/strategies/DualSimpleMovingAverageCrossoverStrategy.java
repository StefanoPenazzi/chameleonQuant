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
public final class DualSimpleMovingAverageCrossoverStrategy extends DualMovingAverageCrossoverStrategy  {
	
    public final static class Builder extends DualMovingAverageCrossoverStrategy.Builder<DualSimpleMovingAverageCrossoverStrategy,Builder> {
		
		private TimeSeriesI ts;
		private int lengthStma = 7;
		private int lengthLtma = 14;
		private String source = "close";
		private int offsetStma = 0;
		private int offsetLtma = 0;
		
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
		 public DualSimpleMovingAverageCrossoverStrategy build() throws Exception{
			 DualSimpleMovingAverageCrossoverStrategy  smas = new DualSimpleMovingAverageCrossoverStrategy (); 
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
}

