/**
 * 
 */
package data.source.internal.dataset.timeseries.point;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import data.source.annotation.InternalTimeSeries.TagName;

/**
 * @author stefanopenazzi
 *
 */
public class InternalSingleTagTimeSeriesPoint<T> implements InternalTimeSeriesPoint {

	private final Instant time;
	private final T value;
	
	public InternalSingleTagTimeSeriesPoint(Instant time, T value) {
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
	

	@Override
	public Map<String, Object> getTagsMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getString() {
		String s = "date: "+ Date.from(this.time).toString() + " value: " + this.value.toString();
		return s;
	}

}
