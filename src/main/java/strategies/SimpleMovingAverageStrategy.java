/**
 * 
 */
package strategies;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import data.source.internal.timeseries.TimeSeriesI;
import data.source.internal.timeseries.point.TimeSeriesPointI;
import indicators.movingAverage.SimpleMovingAverage;
import strategies.Position.PositionType;

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
		
		boolean up = (double)itsRefCopy.get(0).getTagValue(this.source) >= (double)smaCopy.get(0).getTagValue("value") ? false: true;
		
		int volume = 100;
		
		Method methodItsRef = itsRefCopy.get(0).getTagMethod(this.source);
		Method methodSma = smaCopy.get(0).getTagMethod("value");
		String secId = this.itsRef.getQuery().getId();
		
		for(int i = 1;i<smaCopy.size();i++) {
			double refVal = 0;
			double smaVal = 0;
			try {
				refVal = (double) methodItsRef.invoke(itsRefCopy.get(i));
				smaVal = (double) methodSma.invoke(smaCopy.get(i));
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(up) {
				if(refVal < smaVal) {
					up = false;
					if(positions.size()>=1) {
						positions.get(positions.size()-1).addNewSignal(refVal, volume, itsRefCopy.get(i).getTime());
					}
					Position position = new Position.Builder(PositionType.SHORT)
							.securityId(secId)
							.price(refVal)
							.initialVolume(volume)
							.openInstant(itsRefCopy.get(i).getTime())
							.build();
					positions.add(position);
				}
			}
			else {
				if(refVal > smaVal) {
					up = true;
					if(positions.size()>=1) {
						positions.get(positions.size()-1).addNewSignal(refVal, volume, itsRefCopy.get(i).getTime());
					}
					Position position = new Position.Builder(PositionType.LONG)
							.securityId(secId)
							.price(refVal)
							.initialVolume(volume)
							.openInstant(itsRefCopy.get(i).getTime())
							.build();
					positions.add(position);
				}
			}
		}
		
	}

}
