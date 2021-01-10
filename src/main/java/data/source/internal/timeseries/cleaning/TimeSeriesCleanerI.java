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
public interface TimeSeriesCleanerI {

	public TimeSeriesDataStructureI clean(TimeSeriesDataStructureI tsd);
	
}
