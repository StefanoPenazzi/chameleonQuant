/**
 * 
 */
package data.source.internal.dataset.timeseries.standard.stock;

import java.util.Date;

import data.source.internal.dataset.timeseries.InternalTimeSeriesQueryI;

/**
 * @author stefanopenazzi
 *
 */
public class InternalStockQuery implements InternalTimeSeriesQueryI {

	private final Date startDate;
	private final Date endDate;
	private final String market;
	private final String code;
	private final String inter;
	
	public InternalStockQuery(Date startDate, Date endDate, String market, String code, String inter) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.market = market;
		this.code = code;
		this.inter = inter;
	}
	
	public Date getStartDate() {
		return this.startDate;
	}
	
	public Date getEndDate() {
		return this.endDate;
	}
	
	public String getMarket() {
		return this.market;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getInterval() {
		return this.inter;
	}

	@Override
	public String getId() {
		
		return code;
	}
	
}
