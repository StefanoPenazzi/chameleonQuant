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



/**
 * @author stefanopenazzi
 *
 */
public class InternalTimeSeriesFactoryImpl implements InternalTimeSeriesFactoryI {

	private Map<String,TimeSeriesCleanerI> cleaners;
	
	private List<TimeSeriesCleanerI> cleanersList;
	
	
	//would it be better to have cleaners in enum?
	//should we have default cleaners?
	@Inject
	public InternalTimeSeriesFactoryImpl(Map<String,TimeSeriesCleanerI> cleaners) {
		this.cleaners = cleaners;
		
		
		
	}
	
	@Override
	public InternalStockTimeSeriesImpl<RBTree> createTimeSeries(List<String> cleanersId, InternalTimeSeriesQueryRequest itsReq ,InternalTimeSeriesQueryI iq) {
		
		this.cleanersList = new ArrayList<>();
		
		for(String s : cleanersId) {
			TimeSeriesCleanerI tsc = cleaners.get(s);
			if(tsc != null) {
				cleanersList.add(tsc);
			}
		}
		
		
		return new InternalStockTimeSeriesImpl<RBTree>(new RBTree(itsReq.getResult(iq)) ,iq.getId(),cleanersList);
	}

}
