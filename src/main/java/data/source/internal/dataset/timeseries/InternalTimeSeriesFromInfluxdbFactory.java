/**
 * 
 */
package data.source.internal.dataset.timeseries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;

import data.source.internal.dataset.timeseries.cleaning.TimeSeriesCleanerI;
import data.source.internal.dataset.timeseries.datastructure.RBTree;
import data.source.internal.dataset.timeseries.datastructure.TimeSeriesDataStructureI;


/**
 * @author stefanopenazzi
 *
 */
public class InternalTimeSeriesFromInfluxdbFactory implements InternalTimeSeriesFactoryI {

	private Map<String,TimeSeriesCleanerI> cleaners;
	private InternalTimeSeriesQueryInfluxdb  its;
	
	
	private List<TimeSeriesCleanerI> cleanersList;
	
	
	//would it be better to have cleaners in enum?
	//should we have default cleaners?
	@Inject
	public InternalTimeSeriesFromInfluxdbFactory(Map<String,TimeSeriesCleanerI> cleaners,InternalTimeSeriesQueryInfluxdb  its) {
		this.cleaners = cleaners;
		this.its = its;
		
		
	}
	
	@Override
	public InternalStockTimeSeriesImpl<RBTree> createTimeSeries(List<String> cleanersId, InternalQuery iq) {
		
		this.cleanersList = new ArrayList<>();
		
		for(String s : cleanersId) {
			TimeSeriesCleanerI tsc = cleaners.get(s);
			if(tsc != null) {
				cleanersList.add(tsc);
			}
		}
		
		
		return new InternalStockTimeSeriesImpl<RBTree>(new RBTree(its.getResult(iq)) ,"AAPL",cleanersList);
	}

}
