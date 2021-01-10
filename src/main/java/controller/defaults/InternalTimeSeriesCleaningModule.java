/**
 * 
 */
package controller.defaults;

import controller.AbstractModule;
import data.source.external.database.influxdb.utils.adjustments.TimeSeriesCleanerNullValuesStockInfluxdb;
import data.source.internal.timeseries.cleaning.TimeSeriesCleanerI;
import data.source.internal.timeseries.point.TimeSeriesPointI;

/**
 * @author stefanopenazzi
 *
 */
public class InternalTimeSeriesCleaningModule extends AbstractModule {

	@Override
	public void install() {
		addInternalTimeSeriesCleaner("NULL_INFLUXDB").to((Class<? extends TimeSeriesCleanerI>) TimeSeriesCleanerNullValuesStockInfluxdb.class);
		
	}

}
