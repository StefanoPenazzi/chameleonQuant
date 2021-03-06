/**
 * 
 */
package strategies;

import java.time.Instant;
import java.util.List;

import data.source.internal.timeseries.TimeSeriesI;
import data.source.internal.timeseries.point.TimeSeriesPointI;
import indicators.movingAverage.SimpleMovingAverage;
import strategies.Signal.Action;
import strategies.SimpleMovingAverageStrategy.Builder;

/**
 * @author stefanopenazzi
 *
 */
public class DualSimpleMovingAverageCrossoverStrategy extends StrategyAbstract  {

	private TimeSeriesI itsRef;
	private TimeSeriesI stma;
	private TimeSeriesI ltma;
	private String source;
	
	
    public static class Builder {
		
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
	
	@Override
	public void run() {
		Instant from = this.stma.getFirstInstant().compareTo(this.ltma.getFirstInstant()) > 0 ? this.stma.getFirstInstant() : this.ltma.getFirstInstant();
		Instant to = this.stma.getLastInstant().compareTo(this.ltma.getLastInstant()) > 0 ? this.ltma.getLastInstant() : this.stma.getLastInstant();
		
		List<TimeSeriesPointI> stmaCopy = this.stma.getListFromTo(from,to);
		List<TimeSeriesPointI> ltmaCopy = this.ltma.getListFromTo(from,to);
		List<TimeSeriesPointI> itsRefCopy = this.itsRef.getListFromTo(from,to);
		
		
		boolean up = (double)stmaCopy.get(0).getTagValue("value") >= (double)ltmaCopy.get(0).getTagValue("value") ? false: true;
		
		
		for(int i = 1;i<stmaCopy.size();i++) {
			if(up) {
				if((double)stmaCopy.get(i).getTagValue("value") < (double)ltmaCopy.get(i).getTagValue("value")) {
					up = false;
					Signal signal = new Signal(Action.SELL,(double)itsRefCopy.get(i).getTagValue(this.source),1,itsRefCopy.get(i).getTime());
					signals.add(signal);
				}
			}
			else {
				if((double)ltmaCopy.get(i).getTagValue("value") < (double)stmaCopy.get(i).getTagValue("value")) {
					up = true;
					Signal signal = new Signal(Action.BUY,(double)itsRefCopy.get(i).getTagValue(this.source),1,itsRefCopy.get(i).getTime());
					signals.add(signal);
				}
			}
		}
		
	}

}

