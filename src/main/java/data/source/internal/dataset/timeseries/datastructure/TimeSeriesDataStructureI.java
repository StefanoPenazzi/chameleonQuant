/**
 * 
 */
package data.source.internal.dataset.timeseries.datastructure;

import java.util.Date;

import data.source.internal.dataset.timeseries.point.TimeSeriesPoint;

/**
 * @author stefanopenazzi
 *
 */
public interface TimeSeriesDataStructureI {
	
	public TimeSeriesDataStructureI getRange(Date start, Date end);
	public TimeSeriesPoint getPoint(Date date);
	public TimeSeriesPoint getUpwardsPoint(Date date);
	public TimeSeriesPoint getDownwardsPoint(Date date);

}
