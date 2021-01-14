/**
 * 
 */
package controller.defaults;

import controller.AbstractModule;
import data.source.external.database.influxdb.TimeSeriesRequestInfluxdb;
import data.source.external.financialdatavendors.alphavantage.TimeSeriesRequestAlphaVantage;
import data.source.external.financialdatavendors.eodhistoricaldatacom.TimeSeriesRequestEODHistoricalDataCom;
import data.source.internal.timeseries.TimeSeriesRequestI;

/**
 * @author stefanopenazzi
 *
 */
public class TimeSeriesRequestModule extends AbstractModule {

	@SuppressWarnings("unchecked")
	@Override
	public void install() {
		addTimeSeriesRequest("influxdb").to(TimeSeriesRequestInfluxdb.class).asEagerSingleton();
		addTimeSeriesRequest("alphavantage").to(TimeSeriesRequestAlphaVantage.class).asEagerSingleton();
		addTimeSeriesRequest("eodhistoricaldatacom").to(TimeSeriesRequestEODHistoricalDataCom.class).asEagerSingleton();
		
	}

}
