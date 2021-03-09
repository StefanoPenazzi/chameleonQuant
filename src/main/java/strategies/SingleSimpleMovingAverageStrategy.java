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
public final class SingleSimpleMovingAverageStrategy extends SingleMovingAverageCrossoverStrategy  {
	
	 public static final class Builder extends SingleMovingAverageCrossoverStrategy.Builder<SingleSimpleMovingAverageStrategy,Builder> {
		 private TimeSeriesI ts;
			private int length = 9;
			private String source = "close";
			private int offset = 0;
			
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
			public Builder offset(int offset){
	            this.offset = offset;
	            return this;
	        }
			 public SingleSimpleMovingAverageStrategy build(){
				 SingleSimpleMovingAverageStrategy  smas = new SingleSimpleMovingAverageStrategy (); 
				smas.itsRef = this.ts;
				smas.source = this.source;
				try {
					smas.ma = new SimpleMovingAverage.Builder(this.ts)
							.length(this.length)
							.source(this.source)
							.offset(this.offset)
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
