/**
 * 
 */
package data.source.internal.dataset.timeseries.datastructure;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPoint;

/**
 * @author stefanopenazzi
 *
 */
public class RBTree implements TimeSeriesDataStructureI {

	private TreeMap<Instant,InternalTimeSeriesPoint> map;
	
	
	public RBTree(List<? extends InternalTimeSeriesPoint> input) {
		initialize(input);
	}
	
	private void initialize(List<? extends InternalTimeSeriesPoint> input) {
		map = new TreeMap<Instant,InternalTimeSeriesPoint>();
		for(InternalTimeSeriesPoint tsp: input) {
			map.put(tsp.getTime(), tsp);
		}
	}
	
	@Override
	public TimeSeriesDataStructureI getRange(Instant startTime, Instant endTime) {
		Instant aStart = map.floorEntry(startTime).getKey();
		Instant aEnd = map.ceilingEntry(endTime).getKey();
		NavigableMap<Instant, InternalTimeSeriesPoint> subMap = map.subMap(aStart, true,aEnd,true);
		return new RBTree((List<InternalTimeSeriesPoint>) subMap.values());
	}

	@Override
	public InternalTimeSeriesPoint getPoint(Instant time) {
		return map.get(time);
	}

	@Override
	public InternalTimeSeriesPoint getCeilingPoint(Instant time) {
		return map.ceilingEntry(time).getValue();
	}

	@Override
	public InternalTimeSeriesPoint getFloorPoint(Instant time) {
		return map.floorEntry(time).getValue();
	}

	@Override
	public synchronized void addPoint(InternalTimeSeriesPoint tsp) {
		map.put(tsp.getTime(), tsp);
	}

	@Override
	public synchronized void removePoint(InternalTimeSeriesPoint tsp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InternalTimeSeriesPoint getFirst() {
		return map.firstEntry().getValue();
	}

	@Override
	public InternalTimeSeriesPoint getLast() {
		map.lastEntry().getValue();
		return null;
	}

	@Override
	public Iterator<InternalTimeSeriesPoint> iterator() {
		return map.values().iterator();
	}

}
