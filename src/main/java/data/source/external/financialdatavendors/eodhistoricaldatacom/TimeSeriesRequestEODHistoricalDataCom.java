/**
 * 
 */
package data.source.external.financialdatavendors.eodhistoricaldatacom;

import java.util.List;

import data.source.SourceI;
import data.source.internal.timeseries.TimeSeriesRequestI;
import data.source.internal.timeseries.TimeSeriesRequestIdI;
import data.source.internal.timeseries.point.TimeSeriesPointAbstract;
import data.source.internal.timeseries.point.TimeSeriesPointI;

/**
 * @author stefanopenazzi
 *
 */
public class TimeSeriesRequestEODHistoricalDataCom implements TimeSeriesRequestI {

	@Override
	public List<TimeSeriesPointI> getTimeSeries(TimeSeriesRequestIdI iq,SourceI source) {
		return null;
	}

	@Override
	public TimeSeriesPointI getLastPoint(TimeSeriesRequestIdI iqp,SourceI source) {
		return null;
	}

	@Override
	public List<? extends TimeSeriesPointI> getTimeSeries(TimeSeriesRequestIdI iq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TimeSeriesPointI getLastPoint(TimeSeriesRequestIdI iqp) {
		// TODO Auto-generated method stub
		return null;
	}

	



}
