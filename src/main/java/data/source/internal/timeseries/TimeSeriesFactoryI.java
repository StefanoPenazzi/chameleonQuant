/**
 * 
 */
package data.source.internal.timeseries;

import java.util.List;

import data.source.internal.timeseries.point.TimeSeriesPointI;
import data.source.internal.timeseries.structure.TimeSeriesDataStructureI;

/**
 * @author stefanopenazzi
 *
 */
public interface TimeSeriesFactoryI {
	
	public TimeSeriesI createTimeSeriesQueryRequest(List<String> cleanersId,TimeSeriesRequestIdI iq);
	public TimeSeriesI createTimeSeries(TimeSeriesDataStructureI tsd,TimeSeriesIdI iq);
	public TimeSeriesI createTimeSeries(TimeSeriesDataStructureI tsd,TimeSeriesIdI iq,List<String> cleanersId);

}
