/**
 * 
 */
package data.source.internal.timeseries.standard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.google.inject.Inject;

import data.source.internal.timeseries.TimeSeriesAbstract;
import data.source.internal.timeseries.TimeSeriesFactoryI;
import data.source.internal.timeseries.TimeSeriesIdAbstract;
import data.source.internal.timeseries.TimeSeriesIdI;
import data.source.internal.timeseries.TimeSeriesRequestI;
import data.source.internal.timeseries.TimeSeriesRequestIdI;
import data.source.internal.timeseries.cleaning.TimeSeriesCleanerI;
import data.source.internal.timeseries.point.TimeSeriesPointI;
import data.source.internal.timeseries.structure.RBTree;
import data.source.internal.timeseries.structure.TimeSeriesDataStructureI;



/**
 * @author stefanopenazzi
 *
 */
public class TimeSeriesFactoryImpl implements TimeSeriesFactoryI {

	private Map<String,TimeSeriesCleanerI> cleaners;
	
	private List<TimeSeriesCleanerI> cleanersList;
	
	
	//would it be better to have cleaners in enum?
	//should we have default cleaners?
	@Inject
	public TimeSeriesFactoryImpl(Map<String,TimeSeriesCleanerI> cleaners) {
		this.cleaners = cleaners;
	}
	
	@Override
	public TimeSeriesImpl createTimeSeriesQueryRequest(List<String> cleanersId, TimeSeriesRequestI itsReq ,TimeSeriesRequestIdI iq) {
		
		this.cleanersList = new ArrayList<>();
		
		for(String s : cleanersId) {
			TimeSeriesCleanerI tsc = (TimeSeriesCleanerI) cleaners.get(s);
			if(tsc != null) {
				cleanersList.add(tsc);
			}
		}
		return new TimeSeriesImpl(new RBTree(itsReq.getTimeSeries(iq)) ,iq.getTimeSeriesId(),cleanersList);
	}

	@Override
	public TimeSeriesImpl createTimeSeries(TimeSeriesDataStructureI tsd,
			TimeSeriesIdI iq) {
		return new TimeSeriesImpl(tsd ,iq,cleanersList);
		
	}

	@Override
	public TimeSeriesImpl createTimeSeries(TimeSeriesDataStructureI tsd,
			TimeSeriesIdI iq, List<String> cleanersId) {
        this.cleanersList = new ArrayList<>();
		
		for(String s : cleanersId) {
			TimeSeriesCleanerI tsc = (TimeSeriesCleanerI) cleaners.get(s);
			if(tsc != null) {
				cleanersList.add(tsc);
			}
		}
		return new TimeSeriesImpl(tsd ,iq,cleanersList);
	}

}
