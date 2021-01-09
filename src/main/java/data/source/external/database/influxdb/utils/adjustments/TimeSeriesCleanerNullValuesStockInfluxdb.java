/**
 * 
 */
package data.source.external.database.influxdb.utils.adjustments;

import java.util.ArrayList;
import java.util.List;

import data.source.external.database.influxdb.mirrors.StockEODTimeSeriesPointInfluxdb;
import data.source.internal.timeseries.cleaning.TimeSeriesCleanerI;
import data.source.internal.timeseries.structure.TimeSeriesDataStructureI;


/**
 * @author stefanopenazzi
 *
 */
public class TimeSeriesCleanerNullValuesStockInfluxdb <T extends StockEODTimeSeriesPointInfluxdb> implements TimeSeriesCleanerI<T> {

	@Override
	public TimeSeriesDataStructureI<T> clean(TimeSeriesDataStructureI<T> tsd) {
		List<T> rem = new ArrayList<>();
		for(T itp: tsd) {
			  if(itp.getClose() == null || itp.getHigh() == null || itp.getLow() == null || itp.getOpen() == null || itp.getVolume() == null) {
				  rem.add(itp);
			  } 
		}
		for(T itp: rem) {
			tsd.removePoint(itp);
		}
		
		return tsd;
	}

}
