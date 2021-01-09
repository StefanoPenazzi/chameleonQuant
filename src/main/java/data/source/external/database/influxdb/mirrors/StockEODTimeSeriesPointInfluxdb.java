/**
 * 
 */
package data.source.external.database.influxdb.mirrors;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;
import data.source.annotation.InternalTimeSeries.Function;
import data.source.annotation.InternalTimeSeries.TagName;
import data.source.external.database.MirrorI;
import data.source.internal.timeseries.point.TimeSeriesPointAbstract;

/**
 * @author stefanopenazzi
 *
 */

@Measurement(name = "")
public class StockEODTimeSeriesPointInfluxdb extends TimeSeriesPointAbstract  implements MirrorI  {

	    /**
	 * @param time
	 * @param open
	 * @param close
	 * @param high
	 * @param low
	 * @param volume
	 */

		@Column(name = "time")
	    private Instant time;
	 
	    @Column(name = "open")
	    @Function(name = "first")
	    private Double open;
	    
	    @Column(name = "close")
	    @Function(name = "last")
	    private Double close;
	 
	    @Column(name = "high")
	    @Function(name = "max")
	    private Double high;
	 
	    @Column(name = "low")
	    @Function(name = "min")
	    private Double low;
	 
	    @Column(name = "volume")
	    @Function(name = "sum")
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
			//return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		}
	   
}
