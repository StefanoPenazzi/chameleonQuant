/**
 * 
 */
package indicators.volatility;

import data.source.internal.dataset.core.DatasetI;
import data.source.internal.dataset.core.DatasetImpl;
import data.source.internal.dataset.timeseries.InternalTimeSeriesIdAbstract;
import data.source.internal.dataset.timeseries.point.InternalSingleTagTimeSeriesPoint;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPointI;
import data.source.internal.dataset.timeseries.standard.InternalTimeSeriesImpl;
import indicators.IndicatorAbstract;
import indicators.movingAverage.SimpleMovingAverage;

/**
 * @author stefanopenazzi
 *
 */
public class AverageTrueRange<T extends InternalTimeSeriesPointI> extends IndicatorAbstract {

	private final int periods;
	private final DatasetI dataSet;
	private final InternalTimeSeriesIdAbstract id;
	
	/**
	 * @param dataSet
	 */
	public AverageTrueRange(DatasetI dataSet,int periods) {
		super(dataSet);
		this.periods = periods;
		this.dataSet = dataSet;
		this.id = null;
	}
	
	public AverageTrueRange(DatasetI dataSet,InternalTimeSeriesIdAbstract id,int periods) {
		super(dataSet);
		this.periods = periods;
		this.dataSet = dataSet;
		this.id = id;
	}
	
	public DatasetImpl create() throws Exception {
		DatasetImpl trRes;
		if(this.id != null) {
			TrueRange tr = new TrueRange(this.dataSet,this.id);
			trRes = tr.create();
		}
		else {
			TrueRange tr = new TrueRange(this.dataSet,this.id);
			trRes = tr.create();
		}
		SimpleMovingAverage sma = new SimpleMovingAverage(trRes,"value",this.periods);
		DatasetImpl atrRes = sma.create();
		return atrRes;
	}

	@Override
	public boolean dataSetCheck() {
		// TODO Auto-generated method stub
		return false;
	}

}
