/**
 * 
 */
package data.source.internal.dataset.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

import data.source.external.database.influxdb.InternalStockTimeSeriesQueryInfluxdb;
import data.source.external.database.influxdb.InternalTimeSeriesQueryRequestInfluxdb;
import data.source.external.database.influxdb.mirrors.alphaVantage.StockTimeSeriesPointInfluxdb;
import data.source.internal.dataset.timeseries.InternalTimeSeriesI;
import data.source.internal.dataset.timeseries.InternalTimeSeriesQueryAbstract;
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

	private Map<InternalTimeSeriesQueryAbstract,InternalTimeSeriesI<? extends InternalTimeSeriesPoint>> datasetMap = new HashMap<InternalTimeSeriesQueryAbstract,InternalTimeSeriesI<? extends InternalTimeSeriesPoint>>();
	
	@Override
	public void addTimeSeries(InternalTimeSeriesI<? extends InternalTimeSeriesPoint> its) {
		datasetMap.put(its.getQuery(), its);
	}

	@Override
	public boolean removeTimeSeries(InternalTimeSeriesQueryAbstract its) {
		if(datasetMap.remove(its) == null) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public InternalTimeSeriesI<?> getTimeSeries(InternalTimeSeriesQueryAbstract its) {
		
		Iterator it = datasetMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println(pair.getKey().hashCode());
	    }
	    
	    System.out.println("-----------------");
	    System.out.println(its.hashCode());
	    
		
		return datasetMap.get(its);
	}
}
