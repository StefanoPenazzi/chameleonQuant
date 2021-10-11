/**
 * 
 */
package data.source.external.financialdatavendors.alphavantage;

import org.jetbrains.annotations.NotNull;

import data.source.external.financialdatavendors.alphavantage.parameters.functions.Function;
import data.source.external.financialdatavendors.alphavantage.parameters.intradaytimeseries.Interval;
import data.source.external.financialdatavendors.alphavantage.parameters.intradaytimeseries.Slice;
import data.source.external.financialdatavendors.alphavantage.parameters.output.OutputSize;
import data.source.internal.timeseries.TimeSeriesIdI;
import data.source.internal.timeseries.TimeSeriesRequestIdAbstract;
import data.source.internal.timeseries.TimeSeriesRequestIdI;
import data.source.internal.timeseries.point.TimeSeriesPointI;
import data.source.internal.timeseries.standard.TimeSeriesIdImpl;

/**
 * @author stefanopenazzi
 *
 */
public class TimeSeriesRequestIdAlphaVantage implements TimeSeriesRequestIdI {

	
	private final String SOURCE = "alphavantage";
	private TimeSeriesIdI timeSeriesId;
	private final Class<? extends TimeSeriesPointI> tsp;
	private final Function exchange;
	private final OutputSize outputsize;
	private final Interval interval;
	private final Slice slice;
	private final String id;
	
	
	
	public static class Builder {
		
		private String id;
		private Function function;
		private OutputSize outputsize = OutputSize.COMPACT;
		private Interval interval = null;
		private Slice slice = null;
		private Class<? extends TimeSeriesPointI> tsp = null;
		
		public Builder() {
	    }
		public Builder id(String id){
            this.id = id;
            return this;
        }
		public Builder exchange(Function function){
            this.function = function;
            return this;
        }
		public Builder outputSize(OutputSize outputsize){
            this.outputsize = outputsize;
            return this;
        }
		public Builder interval(Interval interval){
            this.interval = interval;
            return this;
        }
		public Builder slice(Slice slice){
            this.slice = slice;
            return this;
        }
		public Builder timeSeriesPoint(Class<? extends TimeSeriesPointI> tsp){
            this.tsp = tsp;
            return this;
        }
		
		 public TimeSeriesRequestIdAlphaVantage build(){
			 return new TimeSeriesRequestIdAlphaVantage(this.function,
					 this.id,this.interval,this.slice,this.outputsize,this.tsp);
		}		
	}
	

	public TimeSeriesRequestIdAlphaVantage(Function exchange,String id,
			Interval interval,Slice slice,OutputSize outputsize,@NotNull Class<? extends TimeSeriesPointI> tsp) {
		this.outputsize = outputsize;
		this.tsp = tsp;
		this.exchange = exchange;
		this.id = id;
		this.interval = interval;
		this.slice = slice;
		this.timeSeriesId = new TimeSeriesIdImpl.Builder(id)
				 .interval(this.interval.getValue())
				 .build();
	}
	
	@Override
	public String getSource() {
		return this.SOURCE;
	}
	
	
	public Function getExchange() {
		return this.exchange;
	}
	
	public OutputSize getOutputsize() {
		return this.outputsize;
	}
	public Slice getSlice() {
		return this.slice;
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
		return null;
	}

	@Override
	public Object getEndTime() {
		return null;
	}

	@Override
	public Interval getInterval() {
		return this.interval;
	}

}
