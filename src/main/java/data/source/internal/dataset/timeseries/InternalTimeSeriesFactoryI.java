/**
 * 
 */
package data.source.internal.dataset.timeseries;

import java.util.List;

import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPoint;

/**
 * @author stefanopenazzi
 *
 */
public interface InternalTimeSeriesFactoryI<T extends InternalTimeSeriesPoint> {
	
	public InternalTimeSeriesAbstract<T> createTimeSeries(List<String> cleanersId, InternalTimeSeriesQueryRequestI<T> itsReq ,InternalTimeSeriesQueryAbstract iq);

}
