/**
 * 
 */
package data.source.internal.dataset.timeseries.point;

import java.time.Instant;
import java.util.Date;

/**
 * @author stefanopenazzi
 *
 */
public class InternalStockTimeSeriesPointImpl implements InternalStockTimeSeriesPointI  {

	
	private final Instant time;
	private final double open;
	private final double close;
	private final double high;
	private final double low;
	private final double volume;
	
	
	public InternalStockTimeSeriesPointImpl(Instant time,double open,double close,double high,double low,double volume) {
		
		this.time = time;
		this.open = open;
		this.close = close;
		this.high = high;
		this.low = low;
		this.volume = volume;
	}

	@Override
	public Instant getTime() {
		
		return time;
	}

	@Override
	public Object[] getValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getClose() {
	
		return close;
	}

	@Override
	public Double getHigh() {
		
		return high;
	}

	@Override
	public Double getLow() {
	
		return low;
	}

	@Override
	public Double getOpen() {
		
		return open;
	}

	@Override
	public Double getVolume() {
		
		return volume;
	}

}
