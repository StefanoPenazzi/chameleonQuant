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
	
	public AbstractInternalTimeSeries createTimeSeries(List<String> cleanersId, InternalTimeSeriesQueryRequest itsReq ,InternalTimeSeriesQueryI iq);

}
