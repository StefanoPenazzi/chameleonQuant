/**
 * 
 */
package strategies;

import java.time.Instant;
import java.util.List;

import data.source.internal.timeseries.TimeSeriesI;
import data.source.internal.timeseries.point.TimeSeriesPointI;
import indicators.movingAverage.SimpleMovingAverage;
import strategies.Position.PositionType;

/**
 * @author stefanopenazzi
 *
 */
public class TripleSimpleMovingAverageCrossoverStrategy extends StrategyAbstract  {

	private TimeSeriesI itsRef;
	private TimeSeriesI stma;
	private TimeSeriesI mtma;
	private TimeSeriesI ltma;
	private String source;
	private double targetRange;
	
	enum Cross{
		UP,
		DOWN,
		NEUTRAL
	}
	
    public static class Builder {
		
		private TimeSeriesI ts;
		private int lengthStma = 7;
		private int lengthMtma = 14;
		private int lengthLtma = 14;
		private String source = "close";
		private int offsetStma = 0;
		private int offsetMtma = 0;
		private int offsetLtma = 0;
		
		private double targetRange = 1;
		
		public Builder(TimeSeriesI ts) {
	        this.ts = ts;
	    }
		public Builder lengthShortTermMA(int length){
            this.lengthStma = length;
            return this;
        }
		public Builder lengthMediumTermMA(int length){
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
		
		public Builder targetRange(double targetRange){
            this.targetRange = targetRange;
            return this;
        }
		
		 public TripleSimpleMovingAverageCrossoverStrategy build() throws Exception{
			TripleSimpleMovingAverageCrossoverStrategy  smas = new TripleSimpleMovingAverageCrossoverStrategy (); 
			smas.itsRef = this.ts;
			smas.source = this.source;
			smas.targetRange = this.targetRange;
			smas.stma = new SimpleMovingAverage.Builder(this.ts)
					.length(this.lengthStma)
					.source(this.source)
					.offset(this.offsetStma)
					.build()
					.run();
			smas.mtma = new SimpleMovingAverage.Builder(this.ts)
					.length(this.lengthMtma)
					.source(this.source)
					.offset(this.offsetMtma)
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
		List<TimeSeriesPointI> mtmaCopy = this.mtma.getListFromTo(from,to);
		List<TimeSeriesPointI> itsRefCopy = this.itsRef.getListFromTo(from,to);
		
		
		boolean inLong = false;
		boolean inShort = false;
		
		Cross shortXlong = Cross.NEUTRAL;
		Cross shortXmedium = Cross.NEUTRAL;
		Cross mediumXlong = (double)mtmaCopy.get(0).getTagValue("value") < (double)ltmaCopy.get(0).getTagValue("value") ? Cross.DOWN : Cross.UP ;
		
		double candleRange = 0;
		double targetPrice = 0;
		double stopLoss = 0;
		
		int volume = 100;
		
		String secId = this.itsRef.getQuery().getId();
		
		
		for(int i = 1;i<stmaCopy.size();i++) {
			
			if(shortXlong != Cross.UP && (double)stmaCopy.get(i).getTagValue("value") > (double)ltmaCopy.get(i).getTagValue("value")) {
				shortXlong = Cross.UP;
			}
			if(shortXmedium != Cross.UP && (double)stmaCopy.get(i).getTagValue("value") > (double)mtmaCopy.get(i).getTagValue("value")) {
				shortXmedium = Cross.UP;
			}
			if(mediumXlong != Cross.UP && (double)mtmaCopy.get(i).getTagValue("value") > (double)ltmaCopy.get(i).getTagValue("value")) {
				mediumXlong = Cross.UP;
			}
			if(shortXlong != Cross.DOWN && (double)stmaCopy.get(i).getTagValue("value") < (double)ltmaCopy.get(i).getTagValue("value")) {
				shortXlong = Cross.DOWN;
			}
			if(shortXmedium != Cross.DOWN && (double)stmaCopy.get(i).getTagValue("value") < (double)mtmaCopy.get(i).getTagValue("value")) {
				shortXmedium = Cross.DOWN;
			}
			if(mediumXlong != Cross.DOWN && (double)mtmaCopy.get(i).getTagValue("value") < (double)ltmaCopy.get(i).getTagValue("value")) {
				mediumXlong = Cross.DOWN;
			}
			
			
			if(!inLong && !inShort) {
				if(shortXlong == Cross.UP && shortXmedium == Cross.UP && mediumXlong == Cross.DOWN) {
					inLong = true;
					
					Position position = new Position.Builder(PositionType.LONG)
							.securityId(secId)
							.price((double)itsRefCopy.get(i).getTagValue(this.source))
							.initialVolume(volume)
							.openInstant(itsRefCopy.get(i).getTime())
							.build();
					positions.add(position);
					
					candleRange = (double)itsRefCopy.get(i).getTagValue("high") - (double)itsRefCopy.get(i).getTagValue("low");
					targetPrice = (double)itsRefCopy.get(i).getTagValue("high") + (candleRange * this.targetRange);
					stopLoss = (double)itsRefCopy.get(i).getTagValue("low");
				}
				if(shortXlong == Cross.DOWN && shortXmedium == Cross.DOWN && mediumXlong == Cross.UP) {
					inShort = true;
					
					Position position = new Position.Builder(PositionType.SHORT)
							.securityId(secId)
							.price((double)itsRefCopy.get(i).getTagValue(this.source))
							.initialVolume(volume)
							.openInstant(itsRefCopy.get(i).getTime())
							.build();
					positions.add(position);
					
					candleRange = (double)itsRefCopy.get(i).getTagValue("high") - (double)itsRefCopy.get(i).getTagValue("low");
					targetPrice = (double)itsRefCopy.get(i).getTagValue("high") - (candleRange * this.targetRange);
					stopLoss = (double)itsRefCopy.get(i).getTagValue("high");
				}
			}
			else if(inLong && !inShort) {
				if((mediumXlong == Cross.UP && (double)itsRefCopy.get(i).getTagValue(this.source) > targetPrice) ||
						((double)itsRefCopy.get(i).getTagValue(this.source) < stopLoss)) {
					
					positions.get(positions.size()-1).addNewSignal((double)itsRefCopy.get(i).getTagValue(this.source), volume, itsRefCopy.get(i).getTime());

					inLong = false;
					shortXlong = Cross.NEUTRAL;
					shortXmedium = Cross.NEUTRAL;
				}
			}
            else if(!inLong && inShort) {
            	if((mediumXlong == Cross.DOWN && (double)itsRefCopy.get(i).getTagValue(this.source) < targetPrice) ||
						((double)itsRefCopy.get(i).getTagValue(this.source) > stopLoss)) {
					
            		positions.get(positions.size()-1).addNewSignal((double)itsRefCopy.get(i).getTagValue(this.source), volume, itsRefCopy.get(i).getTime());
            		
					inShort = false;
					shortXlong = Cross.NEUTRAL;
					shortXmedium = Cross.NEUTRAL;
				}
			}
            else {
            	//run exception
            }
		}
	}
}

