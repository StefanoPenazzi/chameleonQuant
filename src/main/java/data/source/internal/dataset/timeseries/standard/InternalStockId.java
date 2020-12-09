/**
 * 
 */
package data.source.internal.dataset.timeseries.standard;

import java.util.Date;
import java.util.Objects;

import data.source.internal.dataset.timeseries.InternalTimeSeriesIdAbstract;
import data.source.internal.dataset.timeseries.InternalTimeSeriesIdI;

/**
 * @author stefanopenazzi
 *
 */

//better to have an abstract class
public class InternalStockId extends InternalTimeSeriesIdAbstract {

	private final Date startDate;
	private final Date endDate;
	private final String market;
	private final String code;
	private final String inter;
	
	public InternalStockId(Date startDate, Date endDate, String market, String code, String inter) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.market = market;
		this.code = code;
		this.inter = inter;
	}
	
	@Override
	public Date getStartDate() {
		return this.startDate;
	}
	
	@Override
	public Date getEndDate() {
		return this.endDate;
	}
	
	
	public String getMarket() {
		return this.market;
	}
	
	
	public String getCode() {
		return this.code;
	}
	
	@Override
	public String getInterval() {
		return this.inter;
	}

	@Override
	public String getId() {
		
		return this.code;
	}
	
}
