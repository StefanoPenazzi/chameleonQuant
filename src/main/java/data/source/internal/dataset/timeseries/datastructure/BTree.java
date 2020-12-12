/**
 * 
 */
package data.source.internal.dataset.timeseries.datastructure;

import java.time.Instant;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPointI;

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
	public InternalTimeSeriesPointI getPoint(Instant time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InternalTimeSeriesPointI getCeilingPoint(Instant time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InternalTimeSeriesPointI getFloorPoint(Instant time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPoint(InternalTimeSeriesPointI tsp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePoint(InternalTimeSeriesPointI tsp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InternalTimeSeriesPointI getFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InternalTimeSeriesPointI getLast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<InternalTimeSeriesPointI> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InternalTimeSeriesPointI getPointByIndex(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getList() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
