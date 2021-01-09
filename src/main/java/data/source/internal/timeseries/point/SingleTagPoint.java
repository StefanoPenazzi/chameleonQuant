/**
 * 
 */
package data.source.internal.timeseries.point;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import data.source.annotation.InternalTimeSeries.TagName;

/**
 * @author stefanopenazzi
 *
 */
public class SingleTagPoint<T> extends TimeSeriesPointAbstract {

	private final Instant time;
	private final T value;
	
	public SingleTagPoint(Instant time, T value) {
		this.time = time;
		this.value = value;
	}
	
	@Override
	@TagName(name = "time")
	public Instant getTime() {
		// TODO Auto-generated method stub
		return this.time;
	}
	
	@TagName(name = "value")
	public T getValue() {
		return this.value;
	}

}
