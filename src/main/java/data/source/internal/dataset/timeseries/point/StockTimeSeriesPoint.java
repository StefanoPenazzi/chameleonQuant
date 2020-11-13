/**
 * 
 */
package data.source.internal.dataset.timeseries.point;

/**
 * @author stefanopenazzi
 *
 */
public interface StockTimeSeriesPoint extends TimeSeriesPoint {

	public double getClose();
	public double getHigh();
	public double getLow();
	public double getOpen();
	public double getVolume();
	
}
