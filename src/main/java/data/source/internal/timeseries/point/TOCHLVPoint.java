/**
 * 
 */
package data.source.internal.timeseries.point;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import data.source.annotation.InternalTimeSeries.TagName;

/**
 * @author stefanopenazzi
 *
 */
public class TOCHLVPoint extends TimeSeriesPointAbstract  {

	
	private final Instant time;
	private final double open;
	private final double close;
	private final double high;
	private final double low;
	private final double volume;
	
	
	public TOCHLVPoint(Instant time,double open,double close,double high,double low,double volume) {
		
		this.time = time;
		this.open = open;
		this.close = close;
		this.high = high;
		this.low = low;
		this.volume = volume;
	}

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
