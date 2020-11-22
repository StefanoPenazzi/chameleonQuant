/**
 * 
 */
package data.source.internal.dataset.timeseries.cleaning;

import data.source.internal.dataset.timeseries.datastructure.TimeSeriesDataStructureI;

/**
 * @author stefanopenazzi
 *
 */
public interface TimeSeriesCleanerI {

	public TimeSeriesDataStructureI clean(TimeSeriesDataStructureI tsd);
	
}
