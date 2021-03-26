/**
 * 
 */
package strategies;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import data.source.internal.timeseries.TimeSeriesI;
import data.source.internal.timeseries.point.TimeSeriesPointI;
import strategies.Position.PositionType;
import strategies.positionsizing.PositionSizingI;


/**
 * @author stefanopenazzi
 *
 */
public class TripleMovingAverageCrossoverStrategy extends StrategyAbstract  {

	/**
	 * @param positionSizing
	 */
	public TripleMovingAverageCrossoverStrategy(PositionSizingI positionSizing) {
		super(positionSizing);
	}

	private static final String strategyName = "TripleMovingAverageCrossoverStrategy";
	
	protected TimeSeriesI itsRef;
	protected TimeSeriesI stma;
	protected TimeSeriesI mtma;
	protected TimeSeriesI ltma;
	protected String source;
	protected double targetRange;
	
	enum CrossValue{
		UP,
		DOWN,
		NEUTRAL
	}
	
	enum CrossKey{
		SHORTXLONG,
		SHORTXMEDIUM,
		MEDIUMXLONG
	}
	
	private class CrossPair {
		
		public CrossKey key;
	    public CrossValue value;
		public CrossPair(CrossKey key,CrossValue value) {
			this.key = key;
			this.value = value;
		}
	}
	
	private List<CrossPair> crossList = new ArrayList<CrossPair>();
	
	protected static abstract class Builder
    <T extends TripleMovingAverageCrossoverStrategy, B extends Builder<T, B>> extends StrategyAbstract.Builder<T, B> {}
	
	@Override
	public String getStrategyName() {
		return this.strategyName;
	}
	
	@Override
	public void run() {
		Instant from = this.ltma.getFirstInstant();
		Instant to = this.ltma.getLastInstant();
		
		List<TimeSeriesPointI> stmaCopy = this.stma.getListFromTo(from,to);
		List<TimeSeriesPointI> ltmaCopy = this.ltma.getListFromTo(from,to);
		List<TimeSeriesPointI> mtmaCopy = this.mtma.getListFromTo(from,to);
		List<TimeSeriesPointI> itsRefCopy = this.itsRef.getListFromTo(from,to);
		
		
		boolean inLong = false;
		boolean inShort = false;
		
		CrossValue shortXlong = (double)stmaCopy.get(0).getTagValue("value") < (double)ltmaCopy.get(0).getTagValue("value") ? CrossValue.DOWN : CrossValue.UP;
		CrossValue shortXmedium = (double)stmaCopy.get(0).getTagValue("value") < (double)mtmaCopy.get(0).getTagValue("value") ? CrossValue.DOWN : CrossValue.UP;
		CrossValue mediumXlong = (double)mtmaCopy.get(0).getTagValue("value") < (double)ltmaCopy.get(0).getTagValue("value") ? CrossValue.DOWN : CrossValue.UP;
		
		crossList.add(new CrossPair(CrossKey.SHORTXLONG,CrossValue.NEUTRAL));
		crossList.add(new CrossPair(CrossKey.SHORTXMEDIUM,CrossValue.NEUTRAL));
		crossList.add(new CrossPair(CrossKey.MEDIUMXLONG,CrossValue.NEUTRAL));
		
		double candleRange = 0;
		double targetPrice = 0;
		double stopLoss = 0;
		
		
		
		String secId = this.itsRef.getQuery().getId();
		
		
		for(int i = 1;i<ltmaCopy.size();i++) {
			
			//order is important
			if(shortXmedium != CrossValue.UP && (double)stmaCopy.get(i).getTagValue("value") > (double)mtmaCopy.get(i).getTagValue("value")) {
				shortXmedium = CrossValue.UP;
				crossList.add(new CrossPair(CrossKey.SHORTXMEDIUM,shortXmedium));
			}
			
			if(shortXmedium != CrossValue.DOWN && (double)stmaCopy.get(i).getTagValue("value") < (double)mtmaCopy.get(i).getTagValue("value")) {
				shortXmedium = CrossValue.DOWN;
				crossList.add(new CrossPair(CrossKey.SHORTXMEDIUM,shortXmedium));
			}
			
			if(shortXlong != CrossValue.UP && (double)stmaCopy.get(i).getTagValue("value") > (double)ltmaCopy.get(i).getTagValue("value")) {
				shortXlong = CrossValue.UP;
				crossList.add(new CrossPair(CrossKey.SHORTXLONG,shortXlong));
			}
			
			if(shortXlong != CrossValue.DOWN && (double)stmaCopy.get(i).getTagValue("value") < (double)ltmaCopy.get(i).getTagValue("value")) {
				shortXlong = CrossValue.DOWN;
				crossList.add(new CrossPair(CrossKey.SHORTXLONG,shortXlong));
			}
			
			if(mediumXlong != CrossValue.UP && (double)mtmaCopy.get(i).getTagValue("value") > (double)ltmaCopy.get(i).getTagValue("value")) {
				mediumXlong = CrossValue.UP;
				crossList.add(new CrossPair(CrossKey.MEDIUMXLONG,mediumXlong ));
			}
			
			if(mediumXlong != CrossValue.DOWN && (double)mtmaCopy.get(i).getTagValue("value") < (double)ltmaCopy.get(i).getTagValue("value")) {
				mediumXlong = CrossValue.DOWN;
				crossList.add(new CrossPair(CrossKey.MEDIUMXLONG,mediumXlong ));
			}
			
			int crossListSize = crossList.size();
			CrossPair crossPairFirst = crossList.get(crossListSize - 3);
			CrossPair crossPairSecond = crossList.get(crossListSize - 2);
			CrossPair crossPairThird = crossList.get(crossListSize - 1);
			
			
			if(!inLong && !inShort) {
				if(crossPairFirst.key == CrossKey.SHORTXMEDIUM && crossPairFirst.value == CrossValue.UP &&
						crossPairSecond.key == CrossKey.SHORTXLONG && crossPairSecond.value == CrossValue.UP &&
						crossPairThird.key == CrossKey.MEDIUMXLONG && crossPairThird.value == CrossValue.UP) {
					inLong = true;
					double price = (double)itsRefCopy.get(i).getTagValue(this.source);
					Position position = new Position.Builder(PositionType.LONG)
							.securityId(secId)
							.price(price)
							.initialVolume(this.positionSizing.getSize(this, price))
							.openInstant(itsRefCopy.get(i).getTime())
							.build();
					positions.add(position);
					
					candleRange = (double)itsRefCopy.get(i).getTagValue("high") - (double)itsRefCopy.get(i).getTagValue("low");
					targetPrice = (double)itsRefCopy.get(i).getTagValue("high") + (candleRange * this.targetRange);
					stopLoss = (double)itsRefCopy.get(i).getTagValue("low");
				}
				if(crossPairFirst.key == CrossKey.SHORTXMEDIUM && crossPairFirst.value == CrossValue.DOWN &&
						crossPairSecond.key == CrossKey.SHORTXLONG && crossPairSecond.value == CrossValue.DOWN &&
						crossPairThird.key == CrossKey.MEDIUMXLONG && crossPairThird.value == CrossValue.DOWN) {
					inShort = true;
					double price = (double)itsRefCopy.get(i).getTagValue(this.source);
					Position position = new Position.Builder(PositionType.SHORT)
							.securityId(secId)
							.price(price)
							.initialVolume(this.positionSizing.getSize(this, price))
							.openInstant(itsRefCopy.get(i).getTime())
							.build();
					positions.add(position);
					
					candleRange = (double)itsRefCopy.get(i).getTagValue("high") - (double)itsRefCopy.get(i).getTagValue("low");
					targetPrice = (double)itsRefCopy.get(i).getTagValue("high") - (candleRange * this.targetRange);
					stopLoss = (double)itsRefCopy.get(i).getTagValue("high");
				}
			}
			else if(inLong && !inShort) {
				if((double)itsRefCopy.get(i).getTagValue(this.source) > targetPrice ||
						((double)itsRefCopy.get(i).getTagValue(this.source) < stopLoss)) {
					int volume  = positions.get(positions.size()-1).getCurrVolume();
					positions.get(positions.size()-1).addNewSignal((double)itsRefCopy.get(i).getTagValue(this.source), volume, itsRefCopy.get(i).getTime());
					inLong = false;
					crossList.add(new CrossPair(CrossKey.SHORTXMEDIUM,CrossValue.NEUTRAL));
					crossList.add(new CrossPair(CrossKey.SHORTXLONG,CrossValue.NEUTRAL));
					crossList.add(new CrossPair(CrossKey.MEDIUMXLONG,CrossValue.NEUTRAL));
				}
			}
            else if(!inLong && inShort) {
            	if((double)itsRefCopy.get(i).getTagValue(this.source) < targetPrice ||
						((double)itsRefCopy.get(i).getTagValue(this.source) > stopLoss)) {
            		int volume  = positions.get(positions.size()-1).getCurrVolume();
            		positions.get(positions.size()-1).addNewSignal((double)itsRefCopy.get(i).getTagValue(this.source), volume, itsRefCopy.get(i).getTime());
					inShort = false;
					crossList.add(new CrossPair(CrossKey.SHORTXMEDIUM,CrossValue.NEUTRAL));
					crossList.add(new CrossPair(CrossKey.SHORTXLONG,CrossValue.NEUTRAL));
					crossList.add(new CrossPair(CrossKey.MEDIUMXLONG,CrossValue.NEUTRAL));
				}
			}
            else {
            	//run exception
            }
		}
	}


	
}

