/**
 * 
 */
package data.source.internal.timeseries.standard;

import java.time.Instant;
import data.source.internal.timeseries.TimeSeriesIdAbstract;

/**
 * @author stefanopenazzi
 *
 */
public class TimeSeriesIdImpl extends TimeSeriesIdAbstract {

	
	private Instant startInstant;
	private Instant endInstant;
	private String id;
	private String inter;
	
	public static class Builder {
		private Instant startInstant = null;
		private Instant endInstant = null;
		private String id = null;
		private String interval = null;
		public Builder(String id) {
	        this.id = id;
	    }
		public Builder interval(String interval){
            this.interval = interval;
            return this;
        }
		public Builder startInstant(Instant startInstant){
            this.startInstant = startInstant;
            return this;
        }
		public Builder endInstant(Instant endInstant){
            this.endInstant = endInstant;
            return this;
        }
		 public TimeSeriesIdImpl build(){
			TimeSeriesIdImpl tsId = new TimeSeriesIdImpl(); 
			tsId.id = this.id;
			tsId.inter = this.interval;
			tsId.startInstant = this.startInstant;
			tsId.endInstant = this.endInstant;
            return tsId;
		}		
	}
	
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public Instant getStartInstant() {
		return this.startInstant;
	}

	@Override
	public Instant getEndInstant() {
		return this.endInstant;
	}

	@Override
	public String getInterval() {
		return this.inter;
	}
}
