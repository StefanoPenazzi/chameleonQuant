/**
 * 
 */
package strategies;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import data.source.internal.timeseries.TimeSeriesI;
import data.source.internal.timeseries.point.TimeSeriesPointI;
import indicators.movingAverage.SimpleMovingAverage;
import strategies.Signal.Action;

/**
 * @author stefanopenazzi
 *
 */
public class SimpleMovingAverageStrategy extends StrategyAbstract  {

	private TimeSeriesI itsRef;
	private TimeSeriesI sma;
	private String source;
	
	
    public static class Builder {
		
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
		 public SimpleMovingAverageStrategy build() throws Exception{
			 SimpleMovingAverageStrategy  smas = new SimpleMovingAverageStrategy (); 
			smas.itsRef = this.ts;
			smas.source = this.source;
			smas.sma = new SimpleMovingAverage.Builder(this.ts)
					.length(this.length)
					.source(this.source)
					.offset(this.offset)
					.build()
					.run();
			
            return smas;
		}		
	}
	
	@Override
	public void run() {
		Instant from = this.itsRef.getFirstInstant().compareTo(this.sma.getFirstInstant()) > 0 ? this.itsRef.getFirstInstant() : this.sma.getFirstInstant();
		Instant to = this.itsRef.getLastInstant().compareTo(this.sma.getLastInstant()) > 0 ? this.sma.getLastInstant() : this.itsRef.getLastInstant();
		
		List<TimeSeriesPointI> itsRefCopy = this.itsRef.getListFromTo(from,to);
		List<TimeSeriesPointI> smaCopy = this.sma.getListFromTo(from,to);
		
//		itsRefCopy.forEach( (TimeSeriesPointI) -> System.out.println(TimeSeriesPointI.getString()) ); 
//		System.out.println("MA");
//		smaCopy.forEach( (TimeSeriesPointI) -> System.out.println(TimeSeriesPointI.getString()) ); 
		
		boolean up = (double)itsRefCopy.get(0).getTagValue(this.source) >= (double)smaCopy.get(0).getTagValue("value") ? false: true;
		
		double volume = 100;
		
		for(int i = 1;i<smaCopy.size();i++) {
			if(up) {
				if((double)itsRefCopy.get(i).getTagValue(this.source) < (double)smaCopy.get(i).getTagValue("value")) {
					up = false;
					Signal signal = new Signal(Action.SELL,(double)itsRefCopy.get(i).getTagValue(this.source),volume,itsRefCopy.get(i).getTime());
					signals.add(signal);
				}
			}
			else {
				if((double)itsRefCopy.get(i).getTagValue(this.source) < (double)smaCopy.get(i).getTagValue("value")) {
					up = true;
					Signal signal = new Signal(Action.BUY,(double)itsRefCopy.get(i).getTagValue(this.source),volume,itsRefCopy.get(i).getTime());
					signals.add(signal);
				}
			}
		}
		
	}

}
