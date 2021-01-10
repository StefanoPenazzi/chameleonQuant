/**
 * 
 */
package data.source.internal.timeseries;

/**
 * @author stefanopenazzi
 *
 */
public abstract class TimeSeriesRequestIdAbstract implements TimeSeriesRequestIdI {
	
	protected abstract Object convertId(Object Id);
	protected abstract Object convertInterval(Object interval);
	protected abstract Object convertStartTime(Object startTime);
	protected abstract Object convertEndTime(Object endTime);

}
