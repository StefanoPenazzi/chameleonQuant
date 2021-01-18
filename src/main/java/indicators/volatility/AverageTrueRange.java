/**
 * 
 */
package indicators.volatility;

import data.source.internal.timeseries.TimeSeriesI;
import data.source.internal.timeseries.standard.TimeSeriesImpl;
import indicators.IndicatorI;
import indicators.movingAverage.SimpleMovingAverage;

/**
 * @author stefanopenazzi
 *
 */
public class AverageTrueRange implements IndicatorI {

	private int length;
	private TimeSeriesI itsRef;
	
	 public static class Builder {
			
			private TimeSeriesI ts;
			private int len = 9;
			 
			public Builder(TimeSeriesI ts) {
		        this.ts = ts;
		    }
			
			public Builder length(int len) {
				this.len = len;
				return this;
			}
			
			 public AverageTrueRange build(){
				AverageTrueRange atr = new  AverageTrueRange();
				atr.itsRef = this.ts;
				atr.length = this.len;
	            return atr;
			}		
	}
	
	public TimeSeriesImpl run() throws Exception {
	
		TrueRange tr = new TrueRange.Builder(this.itsRef).build();	
		TimeSeriesImpl trRes = tr.run();
		SimpleMovingAverage sma = new SimpleMovingAverage.Builder(trRes)
				.source("value")
				.length(this.length)
				.build();
		return sma.run();
	}
}
