/**
 * 
 */
package data.source.external.database.influxdb;

import java.time.LocalDateTime;
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
	private final String exchange;
	private final String ticker;
	
	private final String id;
	private final String startTime;
	private final String endTime;
	private final String interval;

	public TimeSeriesRequestIdInfluxdb(String exchange,String ticker,TimeSeriesIdI timeSeriesId, Class<? extends TimeSeriesPointI> tsp) {
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
	
	@InternalQueryInfo(name="database")
	public String getExchange() {
		return this.exchange;
	}
	
	@InternalQueryInfo(name="series")
	public String getTicker() {
		return this.ticker;
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
	protected String convertInterval(Object interval) {
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
