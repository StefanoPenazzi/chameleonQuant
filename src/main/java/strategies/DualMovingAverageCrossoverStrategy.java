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
public class DualMovingAverageCrossoverStrategy extends StrategyAbstract  {

	/**
	 * @param positionSizing
	 */
	public DualMovingAverageCrossoverStrategy(PositionSizingI positionSizing) {
		super(positionSizing);
	}

	private static final String strategyName = "DualMovingAverageCrossoverStrategy";
	
	protected TimeSeriesI itsRef;
	protected TimeSeriesI stma;
	protected TimeSeriesI ltma;
	protected String source;
	
	
	protected static abstract class Builder
    <T extends DualMovingAverageCrossoverStrategy, B extends Builder<T, B>> extends StrategyAbstract.Builder<T, B> {}
	
	@Override
	public String getStrategyName() {
		return this.strategyName;
	}
	
	@Override
	public void run() {
		Instant from = this.stma.getFirstInstant().compareTo(this.ltma.getFirstInstant()) > 0 ? this.stma.getFirstInstant() : this.ltma.getFirstInstant();
		Instant to = this.stma.getLastInstant().compareTo(this.ltma.getLastInstant()) > 0 ? this.ltma.getLastInstant() : this.stma.getLastInstant();
		
		List<TimeSeriesPointI> stmaCopy = this.stma.getListFromTo(from,to);
		List<TimeSeriesPointI> ltmaCopy = this.ltma.getListFromTo(from,to);
		List<TimeSeriesPointI> itsRefCopy = this.itsRef.getListFromTo(from,to);
		
		
		boolean up = (double)stmaCopy.get(0).getTagValue("value") >= (double)ltmaCopy.get(0).getTagValue("value") ? true: false;
		
		int volume = 100;
		
		String secId = this.itsRef.getQuery().getId();
		
		for(int i = 1;i<stmaCopy.size();i++) {
			if(up) {
				if((double)stmaCopy.get(i).getTagValue("value") < (double)ltmaCopy.get(i).getTagValue("value")) {
					up = false;
					double price = (double)itsRefCopy.get(i).getTagValue(this.source);
					if(positions.size()>=1) {
						positions.get(positions.size()-1).addNewSignal((double)itsRefCopy.get(i).getTagValue(this.source), positions.get(positions.size()-1).getInitVolume(), itsRefCopy.get(i).getTime());
					}
					Position position = new Position.Builder(PositionType.SHORT)
							.securityId(secId )
							.price(price)
							.initialVolume(this.positionSizing.getSize(this, price))
							.openInstant(itsRefCopy.get(i).getTime())
							.build();
					positions.add(position);
				}
			}
			else {
				if((double)ltmaCopy.get(i).getTagValue("value") < (double)stmaCopy.get(i).getTagValue("value")) {
					up = true;
					double price = (double)itsRefCopy.get(i).getTagValue(this.source);
					if(positions.size()>=1) {
						positions.get(positions.size()-1).addNewSignal((double)itsRefCopy.get(i).getTagValue(this.source),positions.get(positions.size()-1).getInitVolume(), itsRefCopy.get(i).getTime());
					}
					
					Position position = new Position.Builder(PositionType.LONG)
							.securityId(secId )
							.price(price)
							.initialVolume(this.positionSizing.getSize(this, price))
							.openInstant(itsRefCopy.get(i).getTime())
							.build();
					positions.add(position);
				}
			}
		}
		
	}

	
}