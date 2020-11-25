/**
 * 
 */
package data.source.internal.dataset.timeseries.cleaning;

import data.source.internal.dataset.timeseries.datastructure.TimeSeriesDataStructureI;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPoint;

/**
 * @author stefanopenazzi
 *
 */
public interface TimeSeriesCleanerI<T extends InternalTimeSeriesPoint> {

	public TimeSeriesDataStructureI<T> clean(TimeSeriesDataStructureI<T> tsd);
	
}
