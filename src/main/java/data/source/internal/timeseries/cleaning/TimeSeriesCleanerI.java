/**
 * 
 */
package data.source.internal.timeseries.cleaning;

import data.source.internal.timeseries.point.TimeSeriesPointI;
import data.source.internal.timeseries.structure.TimeSeriesDataStructureI;

/**
 * @author stefanopenazzi
 *
 */
public interface TimeSeriesCleanerI<T extends TimeSeriesPointI> {

	public TimeSeriesDataStructureI<T> clean(TimeSeriesDataStructureI<T> tsd);
	
}
