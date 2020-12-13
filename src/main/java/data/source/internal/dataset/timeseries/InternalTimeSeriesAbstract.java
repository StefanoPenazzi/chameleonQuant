/**
 * 
 */
package data.source.internal.dataset.timeseries;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import data.source.internal.dataset.timeseries.cleaning.TimeSeriesCleanerI;
import data.source.internal.dataset.timeseries.datastructure.TimeSeriesDataStructureI;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPointI;

/**
 * @author stefanopenazzi
 *
 */
public abstract class InternalTimeSeriesAbstract <T extends InternalTimeSeriesPointI> implements InternalTimeSeriesI<T> {
	
	private final TimeSeriesDataStructureI<T> tsd;
	private final InternalTimeSeriesIdAbstract itsq;
	private boolean singleInterval;
	
	public InternalTimeSeriesAbstract(TimeSeriesDataStructureI<T> tsd,InternalTimeSeriesIdAbstract itsq, List<? extends TimeSeriesCleanerI<T>> cleaners) {
		
		this.itsq = itsq;
		this.tsd = initialize(tsd,cleaners);
	}
	
	//data cleaning and general rules should be provided externally (how to manage null values, gaps, etc)
	public TimeSeriesDataStructureI<T> initialize(TimeSeriesDataStructureI<T> tsd,List<? extends TimeSeriesCleanerI<T>> cleaners) {
		
		TimeSeriesDataStructureI<T> tsdInter = tsd;
		tsdInter = firstTimeSeriesAdjustment(tsdInter);
		
		for(TimeSeriesCleanerI c: cleaners) {
			tsdInter = c.clean(tsdInter);
		}
		tsdInter = lastTimeSeriesAdjustment(tsdInter);
		return tsdInter;
	}
	
	public abstract TimeSeriesDataStructureI<T> firstTimeSeriesAdjustment(TimeSeriesDataStructureI<T> tsd);
	
	public abstract TimeSeriesDataStructureI<T> lastTimeSeriesAdjustment(TimeSeriesDataStructureI<T> tsd);
	
	@Override
	public TimeSeriesDataStructureI<T> getRange(Instant startTime, Instant endTime) {
		return this.tsd.getRange(startTime, endTime);
	}
	
	@Override
	public T getPoint(Instant time) {
		return (T) this.tsd.getPoint(time);
	}

	@Override
	public T getCeilingPoint(Instant time) {
		return (T) this.tsd.getCeilingPoint(time);
	}

	@Override
	public T getFloorPoint(Instant time) {
		return (T) this.tsd.getFloorPoint(time);
	}
	
	public T getRange(Date startDate, Date endDate) {
		return (T) this.tsd.getRange(startDate.toInstant(), endDate.toInstant());
	}
	
	
	public InternalTimeSeriesPointI getPoint(Date date) {
		return this.tsd.getPoint(date.toInstant());
	}

	
	public InternalTimeSeriesPointI getCeilingPoint(Date date) {
		return this.tsd.getCeilingPoint(date.toInstant());
	}

	
	public InternalTimeSeriesPointI getFloorPoint(Date date) {
		return this.tsd.getFloorPoint(date.toInstant());
	}

	@Override
	public InternalTimeSeriesIdAbstract getQuery() {
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
	public Iterator<T> iterator() {
		return tsd.iterator();
	}
	
	@Override
	public int hashCode() {
		//TODO get the has code of the query
		return Objects.hash(getInterval(),getFirstInstant(),getLastInstant());
	}
	
	@Override
	public List<T> getList() {
		return tsd.getList();
	}
	
	@Override
	public String getString() {
		String s = itsq.getString() + "\n";
		for(T point: getList()) {
			s = s + point.getString() + "\n";
		}
		return s;
	}
	
	@Override 
	public Method getTagMethod(String tagName) {
		return tsd.getFirst().getTagMethod(tagName);
	}
}
