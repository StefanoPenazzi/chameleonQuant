/**
 * 
 */
package data.source.external.database.influxdb;

import java.time.Instant;
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
	public InternalStockTimeSeriesQueryInfluxdb(Instant startInstant, Instant endInstant, String market, String code, String inter) {
		super(startInstant, endInstant, market, code, inter);
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
