/**
 * 
 */
package strategies;

import data.source.internal.timeseries.TimeSeriesI;
import indicators.movingAverage.ExponentialMovingAverage;
import indicators.movingAverage.SimpleMovingAverage;
import strategies.SingleSimpleMovingAverageStrategy.Builder;

/**
 * @author stefanopenazzi
 *
 */
public final class SingleExpMovingAverageStrategy extends SingleMovingAverageCrossoverStrategy  {
	
	 public static final class Builder extends SingleMovingAverageCrossoverStrategy.Builder<SingleExpMovingAverageStrategy,Builder> {
		 private TimeSeriesI ts;
			private int length = 9;
			private String source = "close";
			private double smoothing = 2;
			
			public Builder(TimeSeriesI ts) {
		        this.ts = ts;
		    }
			public Builder length(int length){
	            this.length = length;
	            return this;
	        }
			public Builder source(String source){
	           this.source = source; 
	            return this;
	        }
			public Builder smoothing(double smoothing){
	            this.smoothing = smoothing;
	            return this;
	        }
			 public SingleExpMovingAverageStrategy build(){
				 SingleExpMovingAverageStrategy  smas = new SingleExpMovingAverageStrategy (); 
				smas.itsRef = this.ts;
				smas.source = this.source;
				try {
					smas.ma = new ExponentialMovingAverage.Builder(this.ts)
							.length(this.length)
							.source(this.source)
							.smoothing(this.smoothing)
							.build()
							.run();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	            return smas;
			}		
	  }

}
