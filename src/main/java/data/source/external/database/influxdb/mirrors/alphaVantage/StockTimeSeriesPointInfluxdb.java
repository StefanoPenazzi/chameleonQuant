/**
 * 
 */
package data.source.external.database.influxdb.mirrors.alphaVantage;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import com.google.inject.Inject;

import data.source.annotation.InternalTimeSeries.Function;
import data.source.annotation.InternalTimeSeries.TagName;
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
		@TagName(name = "time")
		public Instant getTime() {
			return time;
		}

		@Override
		@TagName(name = "close")
		public Double getClose() {
			return close;
		}

		@Override
		@TagName(name = "high")
		public Double getHigh() {
			return high;
		}

		@Override
		@TagName(name = "low")
		public Double getLow() {
			return low;
		}

		@Override
		@TagName(name = "open")
		public Double getOpen() {
			return open;
		}

		@Override
		@TagName(name = "volume")
		public Double getVolume() {
			return volume;
		}
		
		@Inject
		public
		StockTimeSeriesPointInfluxdb(){}

		@Override
		public Map<String, Object> getTagsMap() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getString() {
			String s = "date: "+ Date.from(this.time).toString()  + " open: "+this.getOpen().toString()+" close: "+this.getClose().toString()+" high: "+this.getHigh().toString()+" low: "+this.getLow().toString()+" volume: "+this.getVolume().toString();
			return s;
		}
		
	   
}
