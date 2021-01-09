/**
 * 
 */
package data.source.external.database.influxdb;

import java.time.Instant;
import java.util.Date;

import data.source.annotations.InternalQueryAnnotation.InternalQueryInfo;
import data.source.internal.timeseries.TimeSeriesIdAbstract;
import data.source.internal.timeseries.point.TimeSeriesPointAbstract;
import data.source.internal.timeseries.point.TimeSeriesPointI;

/**
 * @author stefanopenazzi
 *
 */
public class TimeSeriesId  extends TimeSeriesIdAbstract {

	private final Instant startInstant;
	private final Instant endInstant;
	private final String exchange;
	private final String ticker;
	private final String inter;
	private final Class<? extends TimeSeriesPointI> tsp;
	
	public TimeSeriesId(Instant startInstant, Instant endInstant, String exchange, String ticker, String inter,Class<? extends TimeSeriesPointI> tsp) {
        this.startInstant = startInstant;
        this.endInstant = endInstant;
        this.exchange = exchange;
        this.ticker = ticker;
        this.inter = inter;
        this.tsp = tsp;
		// TODO Auto-generated constructor stub
	}
	
	
	@InternalQueryInfo(name="database")
	public String getExchange() {
		return this.exchange;
	}
	
	@InternalQueryInfo(name="series")
	public String getTicker() {
		return this.ticker;
	}
	
	public Instant getStartInstant() {
		return this.startInstant;
	}
	
	public Instant getEndInstant() {
		return this.endInstant;
	}
	
	public String getInterval() {
		return this.inter;
	}

	public String getId() {
		return this.ticker;
	}

	public Class<? extends TimeSeriesPointI> getTimeSeriesPoint() {
		return this.tsp;
	}
}