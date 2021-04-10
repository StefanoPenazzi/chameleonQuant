/**
 * 
 */
package strategies;

import data.source.internal.timeseries.TimeSeriesI;
import indicators.movingAverage.SimpleMovingAverage;
import strategies.StrategiesAnnotations.StrategyVariable;
import strategies.positionsizing.FixedMoneyAmount;
import strategies.positionsizing.PositionSizingI;


/**
 * @author stefanopenazzi
 *
 */
public final class SingleSimpleMovingAverageStrategy extends SingleMovingAverageCrossoverStrategy  {
	
	 public static final class Builder extends SingleMovingAverageCrossoverStrategy.Builder<SingleSimpleMovingAverageStrategy,Builder> {
		    private TimeSeriesI ts;
			private int length = 50;
			private String source = "close";
			private int offset = 0;
			private PositionSizingI ps = new FixedMoneyAmount.Builder()
					.fixedMoneyAmount(10000)
					.build();
			
			public Builder(TimeSeriesI ts) {
		        this.ts = ts;
		    }
			@StrategyVariable(name = "length")
			public Builder length(int length){
	            this.length = length;
	            return this;
	        }
			public Builder source(String source){
	           this.source = source; 
	            return this;
	        }
			public Builder offset(int offset){
	            this.offset = offset;
	            return this;
	        }
			public Builder positionSizing(PositionSizingI ps){
	            this.ps = ps;
	            return this;
	        }
			 public SingleSimpleMovingAverageStrategy build(){
				SingleSimpleMovingAverageStrategy  smas = new SingleSimpleMovingAverageStrategy (this.ts,this.source,this.length,this.offset,this.ps); 
	            return smas;
			}		
	  }
	 
	 
	 public SingleSimpleMovingAverageStrategy(TimeSeriesI ts,String source,int length,int offset, PositionSizingI ps) {
		    super(ps);
		    this.itsRef = ts;
		    this.source = source;
		    try {
				this.ma = new SimpleMovingAverage.Builder(ts)
						.length(length)
						.source(source)
						.offset(offset)
						.build()
						.run();
			} catch (Exception e) {
				e.printStackTrace();
			}
	 }

}
