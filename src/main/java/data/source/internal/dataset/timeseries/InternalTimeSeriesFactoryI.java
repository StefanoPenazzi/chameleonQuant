/**
 * 
 */
package data.source.internal.dataset.timeseries;

import java.util.List;

import data.source.internal.dataset.timeseries.datastructure.TimeSeriesDataStructureI;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPoint;

/**
 * @author stefanopenazzi
 *
 */
public interface InternalTimeSeriesFactoryI<T extends InternalTimeSeriesPoint> {
	
	public InternalTimeSeriesAbstract<T> createTimeSeriesQueryRequest(List<String> cleanersId, InternalTimeSeriesQueryRequestI<T> itsReq ,InternalTimeSeriesIdAbstract iq);
	public InternalTimeSeriesAbstract<T> createTimeSeries(TimeSeriesDataStructureI<T> tsd,InternalTimeSeriesIdAbstract iq);
	public InternalTimeSeriesAbstract<T> createTimeSeries(TimeSeriesDataStructureI<T> tsd,InternalTimeSeriesIdAbstract iq,List<String> cleanersId);

}
