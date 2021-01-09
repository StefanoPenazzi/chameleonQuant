/**
 * 
 */
package data.source.internal.timeseries.structure;

import java.time.Instant;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import data.source.internal.timeseries.point.TimeSeriesPointI;

/**
 * @author stefanopenazzi
 *
 */

/**
 * http://www.it.uu.se/research/group/udbl/Theses/HenrikAndreJonssonPhD.pdf
 *
 */

public class BTree implements TimeSeriesDataStructureI {

	@Override
	public TimeSeriesDataStructureI getRange(Instant startTime, Instant endTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TimeSeriesPointI getPoint(Instant time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TimeSeriesPointI getCeilingPoint(Instant time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TimeSeriesPointI getFloorPoint(Instant time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPoint(TimeSeriesPointI tsp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePoint(TimeSeriesPointI tsp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TimeSeriesPointI getFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TimeSeriesPointI getLast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<TimeSeriesPointI> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TimeSeriesPointI getPointByIndex(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getList() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
