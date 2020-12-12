/**
 * 
 */
package data.source.internal.dataset.timeseries.point;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import data.source.annotation.InternalTimeSeries.TagName;

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
	private final Map<String, Object> tagsMap;
	
	
	public InternalStockTimeSeriesPointImpl(Instant time,double open,double close,double high,double low,double volume) {
		
		this.time = time;
		this.open = open;
		this.close = close;
		this.high = high;
		this.low = low;
		this.volume = volume;
		this.tagsMap = new HashMap<String, Object>(){
			{put("time",time);
			put("open",open);
			put("close",close);
			put("high",high);
			put("low",low);
			put("volume",volume);
			}
			};
	}

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

	@Override
	public Map<String, Object> getTagsMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getString() {
		String s = "open: "+this.getOpen().toString()+" close: "+this.getClose().toString()+" high: "+this.getHigh().toString()+" low: "+this.getLow().toString()+"volume: "+this.getVolume().toString();
		return s;
	}

}
