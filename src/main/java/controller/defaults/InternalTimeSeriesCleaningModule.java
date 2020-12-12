/**
 * 
 */
package controller.defaults;

import controller.AbstractModule;
import data.source.external.database.influxdb.TimeSeriesCleanerNullValuesStockInfluxdb;
import data.source.internal.dataset.timeseries.cleaning.TimeSeriesCleanerI;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPointI;

/**
 * @author stefanopenazzi
 *
 */
public class InternalTimeSeriesCleaningModule extends AbstractModule {

	@Override
	public void install() {
		addInternalTimeSeriesCleaner("NULL_INFLUXDB").to((Class<? extends TimeSeriesCleanerI<? extends InternalTimeSeriesPointI>>) TimeSeriesCleanerNullValuesStockInfluxdb.class);
		
	}

}
