/**
 * 
 */
package indicators.movingAverage;

import java.util.ArrayList;
import java.util.List;

import data.source.internal.dataset.DatasetI;
import data.source.internal.dataset.DatasetImpl;
import data.source.internal.timeseries.TimeSeriesAbstract;
import data.source.internal.timeseries.TimeSeriesIdAbstract;
import data.source.internal.timeseries.point.SingleTagPoint;
import data.source.internal.timeseries.point.TimeSeriesPointI;
import data.source.internal.timeseries.standard.TimeSeriesIdImpl;
import data.source.internal.timeseries.standard.TimeSeriesImpl;
import data.source.internal.timeseries.structure.RBTree;
import indicators.IndicatorAbstract;

/**
 * @author stefanopenazzi
 *
 */
public class MACD extends IndicatorAbstract {

	private final DatasetI dataSet;
	private final TimeSeriesIdAbstract id;
	private final int fastEMAperiods;
	private final int slowEMAperiods;
	private final String tagName;
	private final int maPeriods;
	/**
	 * @param dataSet
	 */
	public MACD(DatasetI dataSet,TimeSeriesIdAbstract id,String tagName,int fastEMAperiods, int slowEMAperiods, int maPeriods) {
		super(dataSet);
		this.dataSet = dataSet;
		this.id = id;
		this.tagName = tagName;
		this.fastEMAperiods = fastEMAperiods;
		this.slowEMAperiods = slowEMAperiods ;
		this.maPeriods = maPeriods;
	}
	
	public DatasetImpl create() throws Exception { 
		
		//TODO bypassare il dataset come ?
		ExponentialMovingAverage fastEMA = new ExponentialMovingAverage(dataSet,id,tagName,fastEMAperiods);
		DatasetImpl dsFastEMA = fastEMA.create();
		
		ExponentialMovingAverage slowEMA = new ExponentialMovingAverage(dataSet,id,tagName,slowEMAperiods);
		DatasetImpl dsSlowEMA = slowEMA.create();
		
		List<? extends TimeSeriesPointI> itsFastEMA = ((TimeSeriesImpl) dsFastEMA.iterator().next()).getList();
		List<? extends TimeSeriesPointI> itsSlowEMA = ((TimeSeriesImpl) dsSlowEMA.iterator().next()).getList();
		
		//MACD
		List<SingleTagPoint<Double>> macdRes = new ArrayList<>();
		for(int j=1;j <= itsSlowEMA.size();j++) {
			double diff = (Double)itsFastEMA.get(itsFastEMA.size()-j).getTagValue("value") - (Double)itsSlowEMA.get(itsSlowEMA.size()-j).getTagValue("value");
			macdRes.add(new SingleTagPoint<Double>(itsFastEMA.get(j).getTime(),diff));
		}
		 DatasetImpl macdDs = new DatasetImpl();
		 RBTree macdRBT = new RBTree(macdRes);
		 macdDs.addTimeSeries(new TimeSeriesImpl(macdRBT,new TimeSeriesIdImpl(macdRBT.getFirst().getTime(),macdRBT.getLast().getTime(),"macd","")));
		 
		 //signal line
		 SimpleMovingAverage sma = new SimpleMovingAverage(macdDs,"value",this.maPeriods);
		 DatasetImpl signalLineDs = sma.create();
		 List<TimeSeriesPointI> slRes = signalLineDs.iterator().next().getList();
		 
		 
		 //histogram macd
		 List<SingleTagPoint<Double>> histRes = new ArrayList<>();
		 for(int j= 1;j <= slRes.size();j++) {
				double diff = (Double)macdRes.get(macdRes.size()-j).getTagValue("value") - (Double)slRes.get(slRes.size()-j).getTagValue("value");
				histRes.add(new SingleTagPoint<Double>(slRes.get(slRes.size()-j).getTime(),diff));
		 }
		 DatasetImpl histDs = new DatasetImpl();
		 histDs.addTimeSeries(new TimeSeriesImpl(new RBTree(histRes),new TimeSeriesIdImpl(null,null,"histogramMacd","")));
		 
		 DatasetImpl resDs = new DatasetImpl();
		 resDs.merge(macdDs);
		 resDs.merge(signalLineDs);
		 resDs.merge(histDs);
		 
		
	    return resDs;
	}

	@Override
	public boolean dataSetCheck() {
		// TODO Auto-generated method stub
		return false;
	}

}
