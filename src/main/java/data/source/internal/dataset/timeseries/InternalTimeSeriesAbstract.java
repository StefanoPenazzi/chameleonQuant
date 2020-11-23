/**
 * 
 */
package data.source.internal.dataset.timeseries;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import data.source.internal.dataset.timeseries.cleaning.TimeSeriesCleanerI;
import data.source.internal.dataset.timeseries.datastructure.TimeSeriesDataStructureI;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPoint;

/**
 * @author stefanopenazzi
 *
 */
public abstract class InternalTimeSeriesAbstract <T extends TimeSeriesDataStructureI> implements InternalTimeSeriesI {
	
	private final T tsd;
	private final InternalTimeSeriesQueryI itsq;
	private boolean singleInterval;
	
	public InternalTimeSeriesAbstract(T tsd,InternalTimeSeriesQueryI itsq, List<? extends TimeSeriesCleanerI> cleaners) {
		
		this.itsq = itsq;
		this.tsd = initialize(tsd,cleaners);
	}
	
	//data cleaning and general rules should be provided externally (how to manage null values, gaps, etc)
	public T initialize(T tsd,List<? extends TimeSeriesCleanerI> cleaners) {
		
		T tsdInter = tsd;
		tsdInter = firstTimeSeriesAdjustment(tsdInter);
		
		for(TimeSeriesCleanerI c: cleaners) {
			tsdInter = (T) c.clean(tsdInter);
		}
		tsdInter = lastTimeSeriesAdjustment(tsdInter);
		return tsdInter;
	}
	
	public abstract T firstTimeSeriesAdjustment(T tsd);
	
	public abstract T lastTimeSeriesAdjustment(T tsd);
	
	@Override
	public T getRange(Instant startTime, Instant endTime) {
		return (T) this.tsd.getRange(startTime, endTime);
	}
	
	@Override
	public InternalTimeSeriesPoint getPoint(Instant time) {
		return this.tsd.getPoint(time);
	}

	@Override
	public InternalTimeSeriesPoint getCeilingPoint(Instant time) {
		return this.tsd.getCeilingPoint(time);
	}

	@Override
	public InternalTimeSeriesPoint getFloorPoint(Instant time) {
		return this.tsd.getFloorPoint(time);
	}
	
	public T getRange(Date startDate, Date endDate) {
		return (T) this.tsd.getRange(startDate.toInstant(), endDate.toInstant());
	}
	
	
	public InternalTimeSeriesPoint getPoint(Date date) {
		return this.tsd.getPoint(date.toInstant());
	}

	
	public InternalTimeSeriesPoint getCeilingPoint(Date date) {
		return this.tsd.getCeilingPoint(date.toInstant());
	}

	
	public InternalTimeSeriesPoint getFloorPoint(Date date) {
		return this.tsd.getFloorPoint(date.toInstant());
	}

	@Override
	public InternalTimeSeriesQueryI getQuery() {
		return this.itsq;
	}

	@Override
	public boolean getSingleInterval() {
		return false;
	}

	@Override
	public Duration getInterval() {
		if (singleInterval) {
			Instant first = tsd.iterator().next().getTime();
			Instant second = tsd.iterator().next().getTime();
			return Duration.between(first,second);
		}else {
			return null;
		}
	}

	@Override
	public Instant getFirstInstant() {
		return tsd.getFirst().getTime() ;
	}

	@Override
	public Instant getLastInstant() {
		return  tsd.getLast().getTime();
	}
	
	public Date getFirstDate() {
		return Date.from(tsd.getFirst().getTime());
	}

	public Date getLastDate() {
		return Date.from(tsd.getLast().getTime());
	}
	
	@Override
	public int hashCode() {
		//TODO get the has code of the query
		return Objects.hash(getInterval(),getFirstInstant(),getLastInstant());
	}
	
}
