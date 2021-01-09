/**
 * 
 */
package indicators.volatility;

import data.source.internal.dataset.DatasetI;
import data.source.internal.dataset.DatasetImpl;
import data.source.internal.timeseries.TimeSeriesIdAbstract;
import data.source.internal.timeseries.point.SingleTagPoint;
import data.source.internal.timeseries.point.TimeSeriesPointI;
import data.source.internal.timeseries.standard.TimeSeriesImpl;
import indicators.IndicatorAbstract;
import indicators.movingAverage.SimpleMovingAverage;

/**
 * @author stefanopenazzi
 *
 */
public class AverageTrueRange<T extends TimeSeriesPointI> extends IndicatorAbstract {

	private final int periods;
	private final DatasetI dataSet;
	private final TimeSeriesIdAbstract id;
	
	/**
	 * @param dataSet
	 */
	public AverageTrueRange(DatasetI dataSet,int periods) {
		super(dataSet);
		this.periods = periods;
		this.dataSet = dataSet;
		this.id = null;
	}
	
	public AverageTrueRange(DatasetI dataSet,TimeSeriesIdAbstract id,int periods) {
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
