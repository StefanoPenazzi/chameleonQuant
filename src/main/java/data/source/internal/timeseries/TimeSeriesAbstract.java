/**
 * 
 */
package data.source.internal.timeseries;

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
import java.util.stream.Collectors;

import data.source.internal.timeseries.cleaning.TimeSeriesCleanerI;
import data.source.internal.timeseries.point.TimeSeriesPointI;
import data.source.internal.timeseries.structure.TimeSeriesDataStructureI;

/**
 * @author stefanopenazzi
 *
 */
public abstract class TimeSeriesAbstract implements TimeSeriesI {
	
	private final TimeSeriesDataStructureI tsd;
	private final TimeSeriesIdI itsq;
	private boolean singleInterval;
	
	public TimeSeriesAbstract(TimeSeriesDataStructureI tsd,TimeSeriesIdI itsq, List<? extends TimeSeriesCleanerI> cleaners) {
		
		this.itsq = itsq;
		this.tsd = initialize(tsd,cleaners);
	}
	
	//data cleaning and general rules should be provided externally (how to manage null values, gaps, etc)
	public TimeSeriesDataStructureI initialize(TimeSeriesDataStructureI tsd,List<? extends TimeSeriesCleanerI> cleaners) {
		
		TimeSeriesDataStructureI tsdInter = tsd;
		tsdInter = firstTimeSeriesAdjustment(tsdInter);
		
		for(TimeSeriesCleanerI c: cleaners) {
			tsdInter = c.clean(tsdInter);
		}
		tsdInter = lastTimeSeriesAdjustment(tsdInter);
		return tsdInter;
	}
	
	public abstract TimeSeriesDataStructureI firstTimeSeriesAdjustment(TimeSeriesDataStructureI tsd);
	
	public abstract TimeSeriesDataStructureI lastTimeSeriesAdjustment(TimeSeriesDataStructureI tsd);
	
	@Override
	public TimeSeriesDataStructureI getRange(Instant startTime, Instant endTime) {
		return this.tsd.getRange(startTime, endTime);
	}
	
	@Override
	public TimeSeriesPointI getPoint(Instant time) {
		return this.tsd.getPoint(time);
	}

	@Override
	public TimeSeriesPointI getCeilingPoint(Instant time) {
		return this.tsd.getCeilingPoint(time);
	}

	@Override
	public TimeSeriesPointI getFloorPoint(Instant time) {
		return this.tsd.getFloorPoint(time);
	}
	
	public TimeSeriesDataStructureI getRange(Date startDate, Date endDate) {
		return this.tsd.getRange(startDate.toInstant(), endDate.toInstant());
	}
	
	
	public TimeSeriesPointI getPoint(Date date) {
		return this.tsd.getPoint(date.toInstant());
	}

	
	public TimeSeriesPointI getCeilingPoint(Date date) {
		return this.tsd.getCeilingPoint(date.toInstant());
	}

	
	public TimeSeriesPointI getFloorPoint(Date date) {
		return this.tsd.getFloorPoint(date.toInstant());
	}

	@Override
	public TimeSeriesIdI getQuery() {
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
	public Iterator<TimeSeriesPointI> iterator() {
		return tsd.iterator();
	}
	
	@Override
	public int hashCode() {
		//TODO get the has code of the query
		return Objects.hash(getInterval(),getFirstInstant(),getLastInstant());
	}
	
	@Override
	public List<TimeSeriesPointI> getList() {
		return tsd.getList();
	}
	
	@Override
	public List<TimeSeriesPointI> getListFrom(Instant time){
		List<TimeSeriesPointI> res = tsd.getList().stream().filter(c -> c.getTime().compareTo(time) >= 0).collect(Collectors.toList());
		return res;
	}
	
	@Override
	public List<TimeSeriesPointI> getListTo(Instant time){
		List<TimeSeriesPointI> res = tsd.getList().stream().filter(c -> c.getTime().compareTo(time) <= 0).collect(Collectors.toList());
		return res;
	}
	
	@Override
	public List<TimeSeriesPointI> getListFromTo(Instant timeFrom, Instant timeTo){
		List<TimeSeriesPointI> res = tsd.getList().stream().filter(c -> c.getTime().compareTo(timeFrom) >= 0 && c.getTime().compareTo(timeTo) <= 0).collect(Collectors.toList());
		return res;
	}
	
	@Override
	public List<TimeSeriesPointI> getComparableList(TimeSeriesI ts){
		List<TimeSeriesPointI> _ts = ts.getList();
		return getComparableList(_ts);
	}
	
	@Override
	public List<TimeSeriesPointI> getComparableList(List<TimeSeriesPointI> _ts){
		List<TimeSeriesPointI> th = getListFromTo(_ts.get(0).getTime(),_ts.get(_ts.size()-1).getTime());
		List<TimeSeriesPointI> res = new ArrayList<>();
		
		int j = 0;
		boolean pass = true;
		for(int i = 0;i<_ts.size();i++) {
			boolean find = false;
			Instant ins = _ts.get(i).getTime();
			while(!find && j < th.size()) {
				if(ins.compareTo(th.get(j).getTime())==0) {
					find = true;
					res.add(th.get(j));
					j++;
					break;
				}
				j++;
			}
			if(!find) {
				pass = false;
				break;
			}
		}
		return pass == true? res:null;
	}
	
	@Override
	public String getString() {
		String s = itsq.getString() + "\n";
		for(TimeSeriesPointI point: getList()) {
			s = s + point.getString() + "\n";
		}
		return s;
	}
	
	@Override 
	public Method getTagMethod(String tagName) {
		return tsd.getFirst().getTagMethod(tagName);
	}
}
