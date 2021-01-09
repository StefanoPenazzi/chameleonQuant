/**
 * 
 */
package data.source.external.financialdatavendors.alphavantage.mirrors;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import data.source.annotations.TimeSeriesAnnotations.Column;
import data.source.annotations.TimeSeriesAnnotations.TagName;
import data.source.internal.timeseries.point.TimeSeriesPointAbstract;

/**
 * @author stefanopenazzi
 *
 */
public class StockEODTimeSeriesPointAlphaVantage extends TimeSeriesPointAbstract  {

	@Column(name = "time")
    private Instant time;
	
	@Column(name = "open")
    private Double open;
	
	@Column(name = "close")
    private Double close;
	
	@Column(name = "high")
    private Double high;
	
	@Column(name = "low")
    private Double low;
	
	@Column(name = "volume")
    private Double volume;

	@Override
	@TagName(name = "time")
	public Instant getTime() {
		return time;
	}

	@TagName(name = "close")
	public Double getClose() {
		return close;
	}

	@TagName(name = "high")
	public Double getHigh() {
		return high;
	}

	@TagName(name = "low")
	public Double getLow() {
		return low;
	}

	@TagName(name = "open")
	public Double getOpen() {
		return open;
	}

	@TagName(name = "volume")
	public Double getVolume() {
		return volume;
	}

	@Override
	public DateTimeFormatter getTimeFormat() {
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd[ HH:mm:ss]")
         .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
         .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
         .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
         .toFormatter();
		return formatter;
	}

}
