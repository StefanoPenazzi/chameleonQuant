/**
 * 
 */
package data.source.internal.dataset.timeseries.standard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.google.inject.Inject;

import data.source.internal.dataset.timeseries.InternalTimeSeriesAbstract;
import data.source.internal.dataset.timeseries.InternalTimeSeriesFactoryI;
import data.source.internal.dataset.timeseries.InternalTimeSeriesIdAbstract;
import data.source.internal.dataset.timeseries.InternalTimeSeriesIdI;
import data.source.internal.dataset.timeseries.InternalTimeSeriesQueryRequestI;
import data.source.internal.dataset.timeseries.cleaning.TimeSeriesCleanerI;
import data.source.internal.dataset.timeseries.datastructure.RBTree;
import data.source.internal.dataset.timeseries.datastructure.TimeSeriesDataStructureI;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPoint;



/**
 * @author stefanopenazzi
 *
 */
public class InternalTimeSeriesFactoryImpl<T extends InternalTimeSeriesPoint> implements InternalTimeSeriesFactoryI<T> {

	private Map<String,TimeSeriesCleanerI<? extends InternalTimeSeriesPoint>> cleaners;
	
	private List<TimeSeriesCleanerI<T>> cleanersList;
	
	
	//would it be better to have cleaners in enum?
	//should we have default cleaners?
	@Inject
	public InternalTimeSeriesFactoryImpl(Map<String,TimeSeriesCleanerI<? extends InternalTimeSeriesPoint>> cleaners) {
		this.cleaners = cleaners;
		
		
		
	}
	
	@Override
	public InternalTimeSeriesAbstract<T> createTimeSeriesQueryRequest(List<String> cleanersId, InternalTimeSeriesQueryRequestI<T> itsReq ,InternalTimeSeriesIdAbstract iq) {
		
		this.cleanersList = new ArrayList<>();
		
		for(String s : cleanersId) {
			TimeSeriesCleanerI<T> tsc = (TimeSeriesCleanerI<T>) cleaners.get(s);
			if(tsc != null) {
				cleanersList.add(tsc);
			}
		}
		return new InternalTimeSeriesImpl<T>(new RBTree<T>(itsReq.getResult(iq)) ,iq,cleanersList);
	}

	@Override
	public InternalTimeSeriesAbstract<T> createTimeSeries(TimeSeriesDataStructureI<T> tsd,
			InternalTimeSeriesIdAbstract iq) {
		return new InternalTimeSeriesImpl<T>(tsd ,iq,cleanersList);
		
	}

	@Override
	public InternalTimeSeriesAbstract<T> createTimeSeries(TimeSeriesDataStructureI<T> tsd,
			InternalTimeSeriesIdAbstract iq, List<String> cleanersId) {
        this.cleanersList = new ArrayList<>();
		
		for(String s : cleanersId) {
			TimeSeriesCleanerI<T> tsc = (TimeSeriesCleanerI<T>) cleaners.get(s);
			if(tsc != null) {
				cleanersList.add(tsc);
			}
		}
		return new InternalTimeSeriesImpl<T>(tsd ,iq,cleanersList);
	}

}
