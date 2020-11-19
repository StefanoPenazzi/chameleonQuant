/**
 * 
 */
package data.source.internal.dataset.timeseries;

import java.util.Date;

import data.source.internal.dataset.timeseries.datastructure.TimeSeriesDataStructureI;
import data.source.internal.dataset.timeseries.point.TimeSeriesPoint;

/**
 * @author stefanopenazzi
 *
 */
public interface TimeSeriesI {
	
	public TimeSeriesDataStructureI getRange(Date start, Date end);
	public TimeSeriesPoint getPoint(Date date);
	public TimeSeriesPoint getUpwardsPoint(Date date);
	public TimeSeriesPoint getDownwardsPoint(Date date);

}
