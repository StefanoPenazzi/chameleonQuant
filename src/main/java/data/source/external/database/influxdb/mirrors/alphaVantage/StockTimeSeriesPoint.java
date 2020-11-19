/**
 * 
 */
package data.source.external.database.influxdb.mirrors.alphaVantage;

import java.time.Instant;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import data.source.external.database.Mirror;
import data.source.internal.dataset.timeseries.point.InternalStockTimeSeriesPointI;

/**
 * @author stefanopenazzi
 *
 */

@Measurement(name = "IBM")
public class StockTimeSeriesPoint implements Mirror,InternalStockTimeSeriesPointI {

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
		public Instant getTime() {
			return time;
		}

		@Override
		public Object[] getValues() {
			return null;
		}

		@Override
		public double getClose() {
			return close;
		}

		@Override
		public double getHigh() {
			return high;
		}

		@Override
		public double getLow() {
			return low;
		}

		@Override
		public double getOpen() {
			return open;
		}

		@Override
		public double getVolume() {
			return volume;
		}
	   
}
