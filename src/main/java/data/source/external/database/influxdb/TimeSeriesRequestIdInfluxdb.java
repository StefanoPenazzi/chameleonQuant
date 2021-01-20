/**
 * 
 */
package data.source.external.database.influxdb;

import java.time.LocalDateTime;
import java.util.ArrayList;
import data.source.internal.timeseries.TimeSeriesIdI;
import data.source.internal.timeseries.TimeSeriesRequestIdI;
import data.source.internal.timeseries.point.TimeSeriesPointI;

/**
 * @author stefanopenazzi
 *
 */
public class TimeSeriesRequestIdInfluxdb implements TimeSeriesRequestIdI{
	
	private final String SOURCE = "influxdb";
	private TimeSeriesIdI timeSeriesId;
	private Class<? extends TimeSeriesPointI> tspc;
	
	private final ArrayList<String> influxIntervalChar = new ArrayList<String>() {{add("s");add("m");add("h");add("d");add("w");add("mo");add("y");}};
	
    public static class Builder {
    	
    	private TimeSeriesIdI timeSeriesId;
    	private Class<? extends TimeSeriesPointI> tspc;
		
		public Builder(TimeSeriesIdI timeSeriesId) {
	        this.timeSeriesId = timeSeriesId;
	    }
		
		public Builder setTimeSeriesPointClass (Class<? extends TimeSeriesPointI> tspc) {
	        this.tspc = tspc;
	        return this;
	    }
		
		public TimeSeriesRequestIdInfluxdb build(){
			TimeSeriesRequestIdInfluxdb tsIdInflux = new TimeSeriesRequestIdInfluxdb(); 
			tsIdInflux.timeSeriesId = this.timeSeriesId; 
			tsIdInflux.tspc = this.tspc;
            return tsIdInflux;
		}		
	}
	
	public TimeSeriesIdI timeSeriesId() {
		return this.timeSeriesId;
	}
	
	@Override
	public String getSource() {
		return this.SOURCE;
	}

	@Override
	public TimeSeriesIdI getTimeSeriesId() {
		return this.timeSeriesId;
	}

	@Override
	public Class<? extends TimeSeriesPointI> getTimeSeriesPoint() {
		return this.tspc;
	}

	@Override
	public String getId() {
		return this.timeSeriesId.getId();
	}

	@Override
	public String getInterval() {
		String interval = timeSeriesId.getInterval().toString();
		//check if the interval is compatible with influx
		String[] intervalParts = interval.split("(?<=\\d)(?=\\D)");
		if(!influxIntervalChar.contains(intervalParts[1].toString())) {
			throw new IllegalArgumentException("The interval is not valid");
		}
		return interval.toString();
	}

	@Override
	public String getStartTime() {
		TimeSeriesPointI tspIst = null;
		try {
			tspIst = this.tspc.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String st = timeSeriesId.getStartInstant() == null? "1990-01-01 00:00:00": tspIst.getTimeFormat().format(timeSeriesId.getStartInstant()).toString(); 
		return st;
	}

	@Override
	public String getEndTime() {
		TimeSeriesPointI tspIst = null;
		try {
			tspIst = this.tspc.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String et = timeSeriesId.getEndInstant() == null? LocalDateTime.now().format(tspIst.getTimeFormat()):tspIst.getTimeFormat().format(timeSeriesId.getEndInstant()).toString();
		return et;
	}

}
