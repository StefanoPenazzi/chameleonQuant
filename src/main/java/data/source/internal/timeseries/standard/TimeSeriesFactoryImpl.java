/**
 * 
 */
package data.source.internal.timeseries.standard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.google.inject.Inject;

import data.source.external.database.influxdb.TimeSeriesId;
import data.source.internal.timeseries.TimeSeriesAbstract;
import data.source.internal.timeseries.TimeSeriesFactoryI;
import data.source.internal.timeseries.TimeSeriesIdAbstract;
import data.source.internal.timeseries.TimeSeriesIdI;
import data.source.internal.timeseries.TimeSeriesRequestI;
import data.source.internal.timeseries.cleaning.TimeSeriesCleanerI;
import data.source.internal.timeseries.point.TimeSeriesPointI;
import data.source.internal.timeseries.structure.RBTree;
import data.source.internal.timeseries.structure.TimeSeriesDataStructureI;



/**
 * @author stefanopenazzi
 *
 */
public class TimeSeriesFactoryImpl<T extends TimeSeriesPointI> implements TimeSeriesFactoryI<T> {

	private Map<String,TimeSeriesCleanerI<? extends TimeSeriesPointI>> cleaners;
	
	private List<TimeSeriesCleanerI<T>> cleanersList;
	
	
	//would it be better to have cleaners in enum?
	//should we have default cleaners?
	@Inject
	public TimeSeriesFactoryImpl(Map<String,TimeSeriesCleanerI<? extends TimeSeriesPointI>> cleaners) {
		this.cleaners = cleaners;
		
		
		
	}
	
	@Override
	public TimeSeriesImpl<T> createTimeSeriesQueryRequest(List<String> cleanersId, TimeSeriesRequestI itsReq ,TimeSeriesId iq) {
		
		this.cleanersList = new ArrayList<>();
		
		for(String s : cleanersId) {
			TimeSeriesCleanerI<T> tsc = (TimeSeriesCleanerI<T>) cleaners.get(s);
			if(tsc != null) {
				cleanersList.add(tsc);
			}
		}
		return new TimeSeriesImpl<T>(new RBTree(itsReq.getTimeSeries(iq)) ,iq,cleanersList);
	}

	@Override
	public TimeSeriesImpl <T> createTimeSeries(TimeSeriesDataStructureI<T> tsd,
			TimeSeriesId iq) {
		return new TimeSeriesImpl<T>(tsd ,iq,cleanersList);
		
	}

	@Override
	public TimeSeriesImpl <T> createTimeSeries(TimeSeriesDataStructureI<T> tsd,
			TimeSeriesId iq, List<String> cleanersId) {
        this.cleanersList = new ArrayList<>();
		
		for(String s : cleanersId) {
			TimeSeriesCleanerI<T> tsc = (TimeSeriesCleanerI<T>) cleaners.get(s);
			if(tsc != null) {
				cleanersList.add(tsc);
			}
		}
		return new TimeSeriesImpl<T>(tsd ,iq,cleanersList);
	}

}
