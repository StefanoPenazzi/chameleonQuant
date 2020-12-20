/**
 * 
 */
package indicators.volatility;

import data.source.internal.dataset.core.DatasetI;
import data.source.internal.dataset.timeseries.point.InternalSingleTagTimeSeriesPoint;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPointI;
import data.source.internal.dataset.timeseries.standard.InternalTimeSeriesImpl;
import indicators.IndicatorAbstract;

/**
 * @author stefanopenazzi
 *
 */
public class AverageTrueRange<T extends InternalTimeSeriesPointI> extends IndicatorAbstract {

	private final InternalTimeSeriesImpl<T> itsRef;
	private InternalTimeSeriesImpl <InternalSingleTagTimeSeriesPoint<Double>> itsRes;
	private final int periods;
	private final String tagName;
	
	/**
	 * @param dataSet
	 */
	public AverageTrueRange(DatasetI dataSet) {
		super(dataSet);
		this.tagName = "";
		this.periods = 0;
		this.itsRef = null;
		
	}

	@Override
	public boolean dataSetCheck() {
		// TODO Auto-generated method stub
		return false;
	}

}
