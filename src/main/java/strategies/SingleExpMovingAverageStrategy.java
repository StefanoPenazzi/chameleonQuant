/**
 * 
 */
package strategies;

import data.source.internal.timeseries.TimeSeriesI;
import indicators.movingAverage.ExponentialMovingAverage;
import indicators.movingAverage.SimpleMovingAverage;
import strategies.SingleSimpleMovingAverageStrategy.Builder;
import strategies.positionsizing.FixedMoneyAmount;
import strategies.positionsizing.PositionSizingI;

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
			private PositionSizingI ps = new FixedMoneyAmount.Builder()
					.fixedMoneyAmount(10000)
					.build();
			
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
			public Builder positionSizing(PositionSizingI ps){
	            this.ps = ps;
	            return this;
	        }
			 public SingleExpMovingAverageStrategy build(){
				 SingleExpMovingAverageStrategy  smas = new SingleExpMovingAverageStrategy (this.ts,this.source,this.length,this.smoothing,this.ps); 
	            return smas;
			}		
	  }
	 
	 public SingleExpMovingAverageStrategy(TimeSeriesI ts,String source,int length,double smoothing, PositionSizingI ps) {
		    super(ps);
		    this.itsRef = ts;
		    this.source = source;
		    try {
				this.ma = new ExponentialMovingAverage.Builder(ts)
						.length(length)
						.source(source)
						.smoothing(smoothing)
						.build()
						.run();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }

}
