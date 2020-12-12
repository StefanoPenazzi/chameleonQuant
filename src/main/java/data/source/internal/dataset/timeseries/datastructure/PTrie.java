/**
 * 
 */
package data.source.internal.dataset.timeseries.datastructure;

import java.time.Instant;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPoint;

/**
 * @author stefanopenazzi
 *
 */
public class PTrie implements TimeSeriesDataStructureI {

	@Override
	public TimeSeriesDataStructureI getRange(Instant startTime, Instant endTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InternalTimeSeriesPoint getPoint(Instant time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InternalTimeSeriesPoint getCeilingPoint(Instant time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InternalTimeSeriesPoint getFloorPoint(Instant time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPoint(InternalTimeSeriesPoint tsp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePoint(InternalTimeSeriesPoint tsp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InternalTimeSeriesPoint getFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InternalTimeSeriesPoint getLast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<InternalTimeSeriesPoint> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public InternalTimeSeriesPoint getPointByIndex(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getList() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
