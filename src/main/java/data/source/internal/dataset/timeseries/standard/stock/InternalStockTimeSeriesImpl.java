/**
 * 
 */
package data.source.internal.dataset.timeseries.standard.stock;

import java.util.List;

import data.source.internal.dataset.timeseries.InternalTimeSeriesAbstract;
import data.source.internal.dataset.timeseries.cleaning.TimeSeriesCleanerI;
import data.source.internal.dataset.timeseries.datastructure.TimeSeriesDataStructureI;

/**
 * @author stefanopenazzi
 *
 */


public class InternalStockTimeSeriesImpl <T extends TimeSeriesDataStructureI> extends InternalTimeSeriesAbstract<T> {
	
//	 @Inject private Map<String,TimeSeriesCleanerI> cleaners;
	
	/**
	 * @param tsd
	 */
	public InternalStockTimeSeriesImpl( T tsd,String id,List<? extends TimeSeriesCleanerI> newCleaners) {
		super(tsd,id,newCleaners);
		
	}

	@Override
	public T firstTimeSeriesAdjustment(T tsd) {
		return tsd;
	}

	@Override
	public T lastTimeSeriesAdjustment(T tsd) {
		return tsd;
	}
	
}
	

