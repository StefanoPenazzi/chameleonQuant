/**
 * 
 */
package data.source.external.database.influxdb.mirrors.alphaVantage;

import java.time.Instant;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;
import data.source.annotation.InternalTimeSeries.Function;
import data.source.annotation.InternalTimeSeries.TagName;
import data.source.external.database.Mirror;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPointAbstract;

/**
 * @author stefanopenazzi
 *
 */

@Measurement(name = "")
public class StockEODTimeSeriesPointInfluxdb extends InternalTimeSeriesPointAbstract  implements Mirror  {

	    /**
	 * @param time
	 * @param open
	 * @param close
	 * @param high
	 * @param low
	 * @param volume
	 */

		@Column(name = "timestamp")
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
	   
}
