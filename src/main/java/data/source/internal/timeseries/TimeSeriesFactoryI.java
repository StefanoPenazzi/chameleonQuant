/**
 * 
 */
package data.source.internal.timeseries;

import java.util.List;

import data.source.external.database.influxdb.TimeSeriesId;
import data.source.internal.timeseries.point.TimeSeriesPointI;
import data.source.internal.timeseries.structure.TimeSeriesDataStructureI;

/**
 * @author stefanopenazzi
 *
 */
public interface TimeSeriesFactoryI<T extends TimeSeriesPointI> {
	
	public TimeSeriesAbstract<T> createTimeSeriesQueryRequest(List<String> cleanersId, TimeSeriesRequestI itsReq ,TimeSeriesId iq);
	public TimeSeriesAbstract<T> createTimeSeries(TimeSeriesDataStructureI<T> tsd,TimeSeriesId iq);
	public TimeSeriesAbstract<T> createTimeSeries(TimeSeriesDataStructureI<T> tsd,TimeSeriesId iq,List<String> cleanersId);

}
