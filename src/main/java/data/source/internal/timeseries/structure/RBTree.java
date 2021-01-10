/**
 * 
 */
package data.source.internal.timeseries.structure;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import data.source.internal.timeseries.point.TimeSeriesPointAbstract;
import data.source.internal.timeseries.point.TimeSeriesPointI;

/**
 * @author stefanopenazzi
 *
 */
public class RBTree implements TimeSeriesDataStructureI {

	private TreeMap<Instant,TimeSeriesPointI> map;
	
	public RBTree(List<? extends TimeSeriesPointI> input) {
		initialize(input);
	}
	
	private void initialize(List<? extends TimeSeriesPointI> input) {
		map = new TreeMap<Instant,TimeSeriesPointI>();
		for(TimeSeriesPointI tsp: input) {
			map.put(tsp.getTime(), tsp);
		}
	}
	
	@Override
	public TimeSeriesDataStructureI getRange(Instant startTime, Instant endTime) {
		Instant aStart = map.floorEntry(startTime).getKey();
		Instant aEnd = map.ceilingEntry(endTime).getKey();
		NavigableMap<Instant, TimeSeriesPointI> subMap = map.subMap(aStart, true,aEnd,true);
		return new RBTree((List<? extends TimeSeriesPointI>) subMap.values());
	}

	@Override
	public TimeSeriesPointI getPoint(Instant time) {
		return map.get(time);
	}

	@Override
	public TimeSeriesPointI getCeilingPoint(Instant time) {
		return map.ceilingEntry(time).getValue();
	}

	@Override
	public TimeSeriesPointI getFloorPoint(Instant time) {
		return map.floorEntry(time).getValue();
	}

	@Override
	public synchronized void addPoint(TimeSeriesPointI tsp) {
		map.put(tsp.getTime(), tsp);
	}

	@Override
	public synchronized void removePoint(TimeSeriesPointI tsp) {
		map.remove(tsp.getTime());
		
	}

	@Override
	public TimeSeriesPointI getFirst() {
		return map.firstEntry().getValue();
	}

	@Override
	public TimeSeriesPointI getLast() {
		return map.lastEntry().getValue();
	}

	@Override
	public Iterator<TimeSeriesPointI> iterator() {
		return (Iterator<TimeSeriesPointI>) map.values().iterator();
	}

	@Override
	public List<TimeSeriesPointI> getList() {
		List<TimeSeriesPointI> l = new ArrayList<TimeSeriesPointI>(this.map.values());
		Collections.sort(l, new Comparator<TimeSeriesPointI>() {
			@Override
			public int compare(TimeSeriesPointI arg0, TimeSeriesPointI arg1) {
				 return arg0.getTime().compareTo(arg1.getTime());
			}
	    });
		return Collections.unmodifiableList(l);
	}

	@Override
	public TimeSeriesPointI getPointByIndex(int i) {
		return null;
	}

}
