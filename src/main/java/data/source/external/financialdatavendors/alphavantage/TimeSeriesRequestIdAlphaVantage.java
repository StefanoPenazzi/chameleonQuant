/**
 * 
 */
package data.source.external.financialdatavendors.alphavantage;

import java.time.Instant;

import data.source.external.financialdatavendors.alphavantage.parameters.functions.Function;
import data.source.internal.timeseries.TimeSeriesIdI;
import data.source.internal.timeseries.TimeSeriesRequestIdAbstract;
import data.source.internal.timeseries.point.TimeSeriesPointI;
import data.source.internal.timeseries.standard.TimeSeriesIdImpl;
import data.source.internal.timeseries.standard.TimeSeriesIdImpl.Builder;

/**
 * @author stefanopenazzi
 *
 */
public class TimeSeriesRequestIdAlphaVantage extends TimeSeriesRequestIdAbstract {

	
	private final String SOURCE = "alphavantage";
	private final TimeSeriesIdI timeSeriesId;
	private final Class<? extends TimeSeriesPointI> tsp;
	private final Function exchange;
	private final String ticker;
	
	private final String id;
	private final String startTime;
	private final String endTime;
	private final String interval;
	
	
	public static class Builder {
		
		private TimeSeriesIdI timeSeriesId;
		private Function function;
		private boolean adjusted = true;
		
		public Builder(TimeSeriesIdI timeSeriesId) {
	        this.timeSeriesId = timeSeriesId;
	    }
		public Builder exchange(Function function){
            this.function = function;
            return this;
        }
		public Builder adjusted(boolean adjusted){
            this.adjusted = adjusted;
            return this;
        }
		
		 public TimeSeriesRequestIdAlphaVantage build(){
			 return null;
		}		
	}
	

	public TimeSeriesRequestIdAlphaVantage(Function exchange,String ticker,TimeSeriesIdI timeSeriesId, Class<? extends TimeSeriesPointI> tsp) {
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
		return this.SOURCE;
	}
	
	
	public Function getExchange() {
		return this.exchange;
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
	public String getId() {
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
	protected String convertId(Object id) {
		return id.toString();
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
