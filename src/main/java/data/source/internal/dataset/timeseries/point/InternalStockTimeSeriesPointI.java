/**
 * 
 */
package data.source.internal.dataset.timeseries.point;

/**
 * @author stefanopenazzi
 *
 */
public interface InternalStockTimeSeriesPointI extends InternalTimeSeriesPoint {

	public Double getClose();
	public Double getHigh();
	public Double getLow();
	public Double getOpen();
	public Double getVolume();
	
}
