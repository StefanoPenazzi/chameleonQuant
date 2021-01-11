/**
 * 
 */
package data.source.external.financialdatavendors.alphavantage;

import data.source.internal.timeseries.TimeSeriesIdI;
import data.source.internal.timeseries.TimeSeriesRequestIdAbstract;
import data.source.internal.timeseries.point.TimeSeriesPointI;

/**
 * @author stefanopenazzi
 *
 */
public class TimeSeriesRequestIdAlphaVantage extends TimeSeriesRequestIdAbstract {

	
	private final String source;
	private final TimeSeriesIdI timeSeriesId;
	private final Class<? extends TimeSeriesPointI> tsp;
	private final String exchange;
	private final String ticker;
	
	private final String id;
	private final String startTime;
	private final String endTime;
	private final String interval;

	public TimeSeriesRequestIdAlphaVantage(String source,String exchange,String ticker,TimeSeriesIdI timeSeriesId, Class<? extends TimeSeriesPointI> tsp) {
		this.source = source;
		this.timeSeriesId = timeSeriesId;
		this.tsp = tsp;
		this.exchange = exchange;
		this.ticker = ticker;
		this.id = convertId(this.timeSeriesId.getId());
		this.interval = convertInterval(this.timeSeriesId.getInterval());
		this.startTime = convertStartTime(this.timeSeriesId.getStartInstant());
		this.endTime =  convertEndTime(this.timeSeriesId.getEndInstant());
	}
	
	@Override
	public String getSource() {
		return this.source;
	}

	@Override
	public TimeSeriesIdI getTimeSeriesId() {
		return this.timeSeriesId;
	}

	@Override
	public Class<? extends TimeSeriesPointI> getTimeSeriesPoint() {
		return this.tsp;
	}

	@Override
	public Object getId() {
		return this.id;
	}

	@Override
	public Object getStartTime() {
		return this.startTime;
	}

	@Override
	public Object getEndTime() {
		return this.endTime;
	}

	@Override
	public Object getInterval() {
		return this.interval;
	}

	@Override
	protected String convertId(Object Id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String convertInterval(Object interval) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String convertStartTime(Object startTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String convertEndTime(Object endTime) {
		// TODO Auto-generated method stub
		return null;
	}

}
