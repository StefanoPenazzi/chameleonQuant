/**
 * 
 */
package data.source.internal.dataset.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

import data.source.external.database.influxdb.InternalStockTimeSeriesQueryInfluxdb;
import data.source.external.database.influxdb.InternalTimeSeriesQueryRequestInfluxdb;
import data.source.external.database.influxdb.mirrors.alphaVantage.StockTimeSeriesPointInfluxdb;
import data.source.internal.dataset.timeseries.InternalTimeSeriesI;
import data.source.internal.dataset.timeseries.InternalTimeSeriesQueryI;
import data.source.internal.dataset.timeseries.InternalTimeSeriesQueryRequestI;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPoint;
import data.source.internal.dataset.timeseries.standard.InternalTimeSeriesFactoryImpl;
import data.source.internal.dataset.timeseries.standard.stock.InternalStockTimeSeriesImpl;

/**
 * @author stefanopenazzi
 *
 */
public class DatasetImpl implements DatasetI {

	private Map<InternalTimeSeriesQueryI,InternalTimeSeriesI<? extends InternalTimeSeriesPoint>> datasetMap = new HashMap<>();
	
	@Override
	public void addTimeSeries(InternalTimeSeriesI<? extends InternalTimeSeriesPoint> its) {
		datasetMap.put(its.getQuery(), its);
	}

	@Override
	public boolean removeTimeSeries(InternalTimeSeriesQueryI its) {
		return false;
	}

	@Override
	public InternalTimeSeriesI<?> getTimeSeries(InternalTimeSeriesQueryI its) {
		return null;
	}
}
