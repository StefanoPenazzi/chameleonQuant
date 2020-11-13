/**
 * 
 */
package data.source.internal.dataset.timeseries.point;

import java.util.Date;

/**
 * @author stefanopenazzi
 *
 */
public class AlphaVantageStockTimeSeriesPoint implements StockTimeSeriesPoint  {

	private final Date startDate;
	private final Date centralDate;
	private final Date endDate;
	private final double open;
	private final double close;
	private final double high;
	private final double low;
	private final double volume;
	
	
	public AlphaVantageStockTimeSeriesPoint(Date startDate,Date centralDate,Date endDate,double open,double close,double high,double low,double volume) {
		this.startDate = startDate;
		this.centralDate = centralDate;
		this.endDate = endDate;
		this.open = open;
		this.close = close;
		this.high = high;
		this.low = low;
		this.volume = volume;
	}
	
	@Override
	public Date getStartDate() {
		
		return startDate;
	}

	@Override
	public Date getCentralDate() {
		
		return centralDate;
	}

	@Override
	public Date getEndDate() {
	
		return endDate;
	}

	@Override
	public Object[] getValue() {
		// TODO Auto-generated method stub
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
