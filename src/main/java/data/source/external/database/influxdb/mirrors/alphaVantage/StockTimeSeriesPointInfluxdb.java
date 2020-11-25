/**
 * 
 */
package data.source.external.database.influxdb.mirrors.alphaVantage;

import java.time.Instant;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import com.google.inject.Inject;

import data.source.annotation.InternalTimeSeries.Function;
import data.source.external.database.Mirror;
import data.source.internal.dataset.timeseries.point.InternalStockTimeSeriesPointI;

/**
 * @author stefanopenazzi
 *
 */

@Measurement(name = "")
public class StockTimeSeriesPointInfluxdb implements Mirror,InternalStockTimeSeriesPointI {

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
		
		@Inject
		public
		StockTimeSeriesPointInfluxdb(){}
		
	   
}
