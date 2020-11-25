/**
 * 
 */
package data.source.external.database.influxdb;

import data.source.external.database.influxdb.mirrors.alphaVantage.StockTimeSeriesPointInfluxdb;
import data.source.internal.dataset.timeseries.cleaning.TimeSeriesCleanerI;
import data.source.internal.dataset.timeseries.datastructure.TimeSeriesDataStructureI;


/**
 * @author stefanopenazzi
 *
 */
public class TimeSeriesCleanerNullValuesStockInfluxdb <T extends StockTimeSeriesPointInfluxdb> implements TimeSeriesCleanerI<T> {

	@Override
	public TimeSeriesDataStructureI<T> clean(TimeSeriesDataStructureI<T> tsd) {
		for(T itp: tsd) {
			/*
			 * if(itp.getClose() == null) {
			 * 
			 * }
			 */
		}
		return null;
	}

}
