/**
 * 
 */
package data.source.internal.dataset.timeseries;

import java.util.Date;

import data.source.internal.dataset.timeseries.InternalTimeSeriesQueryInfluxdb.Interval;
import data.source.internal.dataset.timeseries.InternalTimeSeriesQueryInfluxdb.Market;

/**
 * @author stefanopenazzi
 *
 */
public class InternalQuery {

	private final Date startDate;
	private final Date endDate;
	private final Market market;
	private final String code;
	private final Interval inter;
	
	public InternalQuery(Date startDate, Date endDate, Market market, String code, Interval inter) {
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
	
	public Market getMarket() {
		return this.market;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public Interval getInterval() {
		return this.inter;
	}
	
}
