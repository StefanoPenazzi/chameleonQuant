/**
 * 
 */
package data.source.internal.dataset.timeseries.standard;

import java.util.Date;

import data.source.internal.dataset.timeseries.InternalTimeSeriesIdAbstract;

/**
 * @author stefanopenazzi
 *
 */
public class InternalTimeSeriesIdImpl extends InternalTimeSeriesIdAbstract {

	
	private final Date startDate;
	private final Date endDate;
	private final String id;
	private final String inter;
	
	public InternalTimeSeriesIdImpl(Date startDate, Date endDate, String id,String inter) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.id = id;
		this.inter = inter;
	}
	
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public Date getStartDate() {
		return this.startDate;
	}

	@Override
	public Date getEndDate() {
		return this.endDate;
	}

	@Override
	public String getInterval() {
		return this.inter;
	}

}
