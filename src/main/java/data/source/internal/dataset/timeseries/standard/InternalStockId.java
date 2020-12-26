/**
 * 
 */
package data.source.internal.dataset.timeseries.standard;

import java.time.Instant;
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

	private final Instant startInstant;
	private final Instant endInstant;
	private final String market;
	private final String code;
	private final String inter;
	
	public InternalStockId(Instant startInstant, Instant endInstant, String market, String code, String inter) {
		this.startInstant = startInstant;
		this.endInstant = endInstant;
		this.market = market;
		this.code = code;
		this.inter = inter;
	}
	
	@Override
	public Instant getStartInstant() {
		return this.startInstant;
	}
	
	@Override
	public Instant getEndInstant() {
		return this.endInstant;
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
