/**
 * 
 */
package strategies;

import java.time.Instant;
import java.util.List;
import data.source.internal.timeseries.TimeSeriesI;
import data.source.internal.timeseries.point.TimeSeriesPointI;
import strategies.Position.PositionType;
import strategies.positionsizing.PositionSizingI;


/**
 * @author stefanopenazzi
 *
 */
public class SingleMovingAverageCrossoverStrategy extends StrategyAbstract  {

	/**
	 * @param positionSizing
	 */
	public SingleMovingAverageCrossoverStrategy(PositionSizingI positionSizing) {
		super(positionSizing);
	}

	private static final String strategyName = "SingleMovingAverageCrossoverStrategy";
	
	protected TimeSeriesI itsRef;
	protected TimeSeriesI ma;
	protected String source;
	
	protected static abstract class Builder
	    <T extends SingleMovingAverageCrossoverStrategy, B extends Builder<T, B>> extends StrategyAbstract.Builder<T, B> {
	}
	
	@Override
	public String getStrategyName() {
		return this.strategyName;
	}
	
	@Override
	public void run() {
		Instant from = this.itsRef.getFirstInstant().compareTo(this.ma.getFirstInstant()) > 0 ? this.itsRef.getFirstInstant() : this.ma.getFirstInstant();
		Instant to = this.itsRef.getLastInstant().compareTo(this.ma.getLastInstant()) > 0 ? this.ma.getLastInstant() : this.itsRef.getLastInstant();
		
		List<TimeSeriesPointI> itsRefCopy = this.itsRef.getListFromTo(from,to);
		List<TimeSeriesPointI> smaCopy = this.ma.getListFromTo(from,to);
		
		boolean up = (double)itsRefCopy.get(0).getTagValue(this.source) <= (double)smaCopy.get(0).getTagValue("value") ? true: false;
		
		String secId = this.itsRef.getQuery().getId();
		
		for(int i = 1;i<smaCopy.size();++i) {
			double refVal = 0;
			double smaVal = 0;
			
			refVal = (double) itsRefCopy.get(i).getTagValue(this.source);
			smaVal = (double) smaCopy.get(i).getTagValue("value");
			
			if(up) {
				if(smaVal<refVal) {
					up = false;
					if(positions.size()>=1) {
						positions.get(positions.size()-1).addNewSignal(refVal, this.positionSizing.getSize(this, refVal), itsRefCopy.get(i).getTime());
					}
					Position position = new Position.Builder(PositionType.SHORT)
							.securityId(secId)
							.price(refVal)
							.initialVolume(this.positionSizing.getSize(this, refVal))
							.openInstant(itsRefCopy.get(i).getTime())
							.build();
					positions.add(position);
				}
			}
			else {
				if(smaVal > refVal) {
					up = true;
					if(positions.size()>=1) {
						positions.get(positions.size()-1).addNewSignal(refVal, this.positionSizing.getSize(this, refVal), itsRefCopy.get(i).getTime());
					}
					Position position = new Position.Builder(PositionType.LONG)
							.securityId(secId)
							.price(refVal)
							.initialVolume(this.positionSizing.getSize(this, refVal))
							.openInstant(itsRefCopy.get(i).getTime())
							.build();
					positions.add(position);
				}
			}
		}
		
	}

}

