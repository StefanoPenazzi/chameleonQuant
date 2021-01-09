/**
 * 
 */
package data.source.internal.timeseries.standard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import data.source.internal.timeseries.TimeSeriesAbstract;
import data.source.internal.timeseries.TimeSeriesIdAbstract;
import data.source.internal.timeseries.TimeSeriesIdI;
import data.source.internal.timeseries.cleaning.TimeSeriesCleanerI;
import data.source.internal.timeseries.point.TimeSeriesPointI;
import data.source.internal.timeseries.structure.TimeSeriesDataStructureI;

/**
 * @author stefanopenazzi
 *
 */


public class TimeSeriesImpl <T extends TimeSeriesPointI> extends TimeSeriesAbstract<T> {
	
//	 @Inject private Map<String,TimeSeriesCleanerI> cleaners;
	
	/**
	 * @param tsd
	 */
	public TimeSeriesImpl( TimeSeriesDataStructureI<T> tsd,TimeSeriesIdAbstract itsq,List<? extends TimeSeriesCleanerI<T>> newCleaners) {
		super(tsd,itsq,newCleaners);
		
	}
	
	public TimeSeriesImpl( TimeSeriesDataStructureI<T> tsd,TimeSeriesIdAbstract itsq) {
		super(tsd,itsq,new ArrayList<>());
		
	}

	@Override
	public TimeSeriesDataStructureI<T> firstTimeSeriesAdjustment(TimeSeriesDataStructureI<T> tsd) {
		// TODO Auto-generated method stub
		return tsd;
	}

	@Override
	public TimeSeriesDataStructureI<T> lastTimeSeriesAdjustment(TimeSeriesDataStructureI<T> tsd) {
		// TODO Auto-generated method stub
		return tsd;
	}

	
}
	

