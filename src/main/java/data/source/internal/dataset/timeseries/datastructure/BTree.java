/**
 * 
 */
package data.source.internal.dataset.timeseries.datastructure;

import java.time.Instant;
import java.util.Date;

import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPoint;

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

	

}
