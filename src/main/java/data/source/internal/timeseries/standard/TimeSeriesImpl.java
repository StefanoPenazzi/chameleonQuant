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


public class TimeSeriesImpl extends TimeSeriesAbstract {
	
//	 @Inject private Map<String,TimeSeriesCleanerI> cleaners;
	
	/**
	 * @param tsd
	 */
	public TimeSeriesImpl( TimeSeriesDataStructureI tsd,TimeSeriesIdI itsq,List<? extends TimeSeriesCleanerI> newCleaners) {
		super(tsd,itsq,newCleaners);
		
	}
	
	public TimeSeriesImpl( TimeSeriesDataStructureI tsd,TimeSeriesIdI itsq) {
		super(tsd,itsq,new ArrayList<>());
		
	}

	@Override
	public TimeSeriesDataStructureI firstTimeSeriesAdjustment(TimeSeriesDataStructureI tsd) {
		// TODO Auto-generated method stub
		return tsd;
	}

	@Override
	public TimeSeriesDataStructureI lastTimeSeriesAdjustment(TimeSeriesDataStructureI tsd) {
		// TODO Auto-generated method stub
		return tsd;
	}

	
}
	

