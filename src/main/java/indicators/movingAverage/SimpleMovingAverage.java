/**
 * 
 */
package indicators.movingAverage;

import java.util.Iterator;

import data.source.internal.dataset.core.DatasetI;
import data.source.internal.dataset.timeseries.InternalTimeSeriesAbstract;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPoint;
import indicators.IndicatorAbstract;

/**
 * @author stefanopenazzi
 *
 */
public class SimpleMovingAverage extends IndicatorAbstract {

	private final InternalTimeSeriesAbstract itsRef;
	private InternalTimeSeriesAbstract itsRes;
	/**
	 * @param dataSet
	 */
	public SimpleMovingAverage(DatasetI dataSet) {
		super(dataSet);
		itsRef = this.dataSet.iterator().next();
	}
	
	public void create() {
		Iterator<? extends InternalTimeSeriesPoint> iter = itsRef.iterator();
	    while (iter.hasNext()) {
	        InternalTimeSeriesPoint itsp = (InternalTimeSeriesPoint)iter.next();
	        System.out.println(itsp.getTime().toString());
	    }
		
	}

	@Override
	public boolean dataSetCheck() {
		// TODO Auto-generated method stub
		return false;
	}

}
