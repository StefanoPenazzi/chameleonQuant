/**
 * 
 */
package data.source.internal.dataset.timeseries.standard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import data.source.internal.dataset.timeseries.InternalTimeSeriesAbstract;
import data.source.internal.dataset.timeseries.InternalTimeSeriesIdAbstract;
import data.source.internal.dataset.timeseries.InternalTimeSeriesIdI;
import data.source.internal.dataset.timeseries.cleaning.TimeSeriesCleanerI;
import data.source.internal.dataset.timeseries.datastructure.TimeSeriesDataStructureI;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPointI;

/**
 * @author stefanopenazzi
 *
 */


public class InternalTimeSeriesImpl <T extends InternalTimeSeriesPointI> extends InternalTimeSeriesAbstract<T> {
	
//	 @Inject private Map<String,TimeSeriesCleanerI> cleaners;
	
	/**
	 * @param tsd
	 */
	public InternalTimeSeriesImpl( TimeSeriesDataStructureI<T> tsd,InternalTimeSeriesIdAbstract itsq,List<? extends TimeSeriesCleanerI<T>> newCleaners) {
		super(tsd,itsq,newCleaners);
		
	}
	
	public InternalTimeSeriesImpl( TimeSeriesDataStructureI<T> tsd,InternalTimeSeriesIdAbstract itsq) {
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
	

