/**
 * 
 */
package indicators.movingAverage;

import java.util.ArrayList;
import java.util.List;

import data.source.internal.dataset.core.DatasetI;
import data.source.internal.dataset.core.DatasetImpl;
import data.source.internal.dataset.timeseries.InternalTimeSeriesAbstract;
import data.source.internal.dataset.timeseries.InternalTimeSeriesIdAbstract;
import data.source.internal.dataset.timeseries.datastructure.RBTree;
import data.source.internal.dataset.timeseries.point.InternalSingleTagTimeSeriesPoint;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPointI;
import data.source.internal.dataset.timeseries.standard.InternalTimeSeriesIdImpl;
import data.source.internal.dataset.timeseries.standard.InternalTimeSeriesImpl;
import indicators.IndicatorAbstract;

/**
 * @author stefanopenazzi
 *
 */
public class MACD <T extends InternalTimeSeriesPointI> extends IndicatorAbstract {

	private final DatasetI dataSet;
	private final InternalTimeSeriesIdAbstract id;
	private final int fastEMAperiods;
	private final int slowEMAperiods;
	private final String tagName;
	private final int maPeriods;
	/**
	 * @param dataSet
	 */
	public MACD(DatasetI dataSet,InternalTimeSeriesIdAbstract id,String tagName,int fastEMAperiods, int slowEMAperiods, int maPeriods) {
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
		ExponentialMovingAverage<T> fastEMA = new ExponentialMovingAverage<T>(dataSet,id,tagName,fastEMAperiods);
		DatasetImpl dsFastEMA = fastEMA.create();
		
		ExponentialMovingAverage<T> slowEMA = new ExponentialMovingAverage<T>(dataSet,id,tagName,slowEMAperiods);
		DatasetImpl dsSlowEMA = slowEMA.create();
		
		List<T> itsFastEMA = ((InternalTimeSeriesImpl<T>) dsFastEMA.iterator().next()).getList();
		List<T> itsSlowEMA = ((InternalTimeSeriesImpl<T>) dsSlowEMA.iterator().next()).getList();
		
		//MACD
		List<InternalSingleTagTimeSeriesPoint<Double>> macdRes = new ArrayList<>();
		for(int j=1;j <= itsSlowEMA.size();j++) {
			double diff = (Double)itsFastEMA.get(itsFastEMA.size()-j).getTagValue("value") - (Double)itsSlowEMA.get(itsSlowEMA.size()-j).getTagValue("value");
			macdRes.add(new InternalSingleTagTimeSeriesPoint<Double>(itsFastEMA.get(j).getTime(),diff));
		}
		 DatasetImpl macdDs = new DatasetImpl();
		 RBTree<InternalSingleTagTimeSeriesPoint<Double>> macdRBT = new RBTree<InternalSingleTagTimeSeriesPoint<Double>>(macdRes);
		 macdDs.addTimeSeries(new InternalTimeSeriesImpl(macdRBT,new InternalTimeSeriesIdImpl(macdRBT.getFirst().getTime(),macdRBT.getLast().getTime(),"macd","")));
		 
		 //signal line
		 SimpleMovingAverage sma = new SimpleMovingAverage(macdDs,"value",this.maPeriods);
		 DatasetImpl signalLineDs = sma.create();
		 List<InternalSingleTagTimeSeriesPoint<Double>> slRes = ((InternalTimeSeriesAbstract<InternalSingleTagTimeSeriesPoint<Double>>)signalLineDs.iterator().next()).getList();
		 
		 
		 //histogram macd
		 List<InternalSingleTagTimeSeriesPoint<Double>> histRes = new ArrayList<>();
		 for(int j= 1;j <= slRes.size();j++) {
				double diff = (Double)macdRes.get(macdRes.size()-j).getTagValue("value") - (Double)slRes.get(slRes.size()-j).getTagValue("value");
				histRes.add(new InternalSingleTagTimeSeriesPoint<Double>(slRes.get(slRes.size()-j).getTime(),diff));
		 }
		 DatasetImpl histDs = new DatasetImpl();
		 histDs.addTimeSeries(new InternalTimeSeriesImpl(new RBTree(histRes),new InternalTimeSeriesIdImpl(null,null,"histogramMacd","")));
		 
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
