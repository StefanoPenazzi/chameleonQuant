/**
 * 
 */
package data.source.internal.dataset.timeseries;

import java.util.List;

/**
 * @author stefanopenazzi
 *
 */
public interface InternalTimeSeriesFactoryI {
	
	public InternalTimeSeriesAbstract createTimeSeries(List<String> cleanersId, InternalTimeSeriesQueryRequestI itsReq ,InternalTimeSeriesQueryI iq);

}
