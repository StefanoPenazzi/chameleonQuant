/**
 * 
 */
package data.source.external.database.influxdb;

import java.util.Date;

import data.source.annotation.InternalQueryAnnotation.InternalQueryInfo;
import data.source.internal.dataset.timeseries.standard.InternalStockId;

/**
 * @author stefanopenazzi
 *
 */
public class InternalStockTimeSeriesQueryInfluxdb extends InternalStockId {

	/**
	 * @param startDate
	 * @param endDate
	 * @param market
	 * @param code
	 * @param inter
	 */
	public InternalStockTimeSeriesQueryInfluxdb(Date startDate, Date endDate, String market, String code, String inter) {
		super(startDate, endDate, market, code, inter);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	@InternalQueryInfo(name="database")
	public String getMarket() {
		return super.getMarket();
	}
	
	@Override
	@InternalQueryInfo(name="series")
	public String getCode() {
		return super.getCode();
	}
	

}
