/**
 * 
 */
package data.source.external.database.influxdb;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import data.source.annotations.InternalQueryAnnotation.InternalQueryInfo;
import data.source.internal.timeseries.TimeSeriesIdI;
import data.source.internal.timeseries.TimeSeriesRequestIdAbstract;
import data.source.internal.timeseries.point.TimeSeriesPointI;

/**
 * @author stefanopenazzi
 *
 */
public class TimeSeriesRequestIdInfluxdb extends TimeSeriesRequestIdAbstract{
	
	private final String source = "influxdb";
	private final TimeSeriesIdI timeSeriesId;
	private final Class<? extends TimeSeriesPointI> tsp;
	private final String id;
	private final String startTime;
	private final String endTime;
	private final String interval;
	private final ArrayList<String> influxIntervalChar = new ArrayList<String>() {{add("s");add("m");add("h");add("d");add("w");add("mo");add("y");}};

	public TimeSeriesRequestIdInfluxdb(TimeSeriesIdI timeSeriesId, Class<? extends TimeSeriesPointI> tsp) {
		this.timeSeriesId = timeSeriesId;
		this.tsp = tsp;
		this.id = convertId(this.timeSeriesId.getId());
		this.interval = convertInterval(this.timeSeriesId.getInterval());
		this.startTime = convertStartTime(this.timeSeriesId.getStartInstant());
		this.endTime =  convertEndTime(this.timeSeriesId.getEndInstant());
	}
	
	public TimeSeriesRequestIdInfluxdb(TimeSeriesIdI timeSeriesId) {
		this.timeSeriesId = timeSeriesId;
		this.tsp = null;
		this.id = null;
		this.interval = null;
		this.startTime = null;
		this.endTime =  null;
	}
	
	public TimeSeriesIdI timeSeriesId() {
		return this.timeSeriesId;
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
	public String getId() {
		return this.id;
	}

	@Override
	public String getInterval() {
		return this.interval;
	}

	@Override
	public String getStartTime() {
		return this.startTime;
	}

	@Override
	public String getEndTime() {
		return this.endTime;
	}

	@Override
	protected String convertId(Object iid) {
		return iid.toString();
	}

	@Override
	protected String convertInterval(Object intervalInput) {
		String interval = intervalInput.toString();
		//check if the interval is compatible with influx
		String[] intervalParts = interval.split("(?<=\\d)(?=\\D)");
		if(!influxIntervalChar.contains(intervalParts[1].toString())) {
			throw new IllegalArgumentException("The interval is not valid");
		}
		return interval.toString();
	}

	@Override
	protected String convertStartTime(Object startTime) {
		TimeSeriesPointI tspIst = null;
		try {
			tspIst = this.tsp.newInstance();
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
	protected String convertEndTime(Object endTime) {
		TimeSeriesPointI tspIst = null;
		try {
			tspIst = this.tsp.newInstance();
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
