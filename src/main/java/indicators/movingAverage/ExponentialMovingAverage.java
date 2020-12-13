/**
 * 
 */
package indicators.movingAverage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import data.source.internal.dataset.core.DatasetI;
import data.source.internal.dataset.core.DatasetImpl;
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
public class ExponentialMovingAverage<T extends InternalTimeSeriesPointI> extends IndicatorAbstract  {

	private final InternalTimeSeriesImpl<T> itsRef;
	private InternalTimeSeriesImpl <InternalSingleTagTimeSeriesPoint<Double>> itsRes;
	private final int periods;
	private final String tagName;
	private final double smoothing;
	
	/**
	 * @param dataSet
	 */
	public ExponentialMovingAverage(DatasetI dataSet,String tagName,int periods,double smoothing) {
		super(dataSet);
		itsRef = (InternalTimeSeriesImpl<T>) this.dataSet.iterator().next();
		this.periods= periods;
		this.tagName = tagName;
		this.smoothing = smoothing;
	}
	
	public ExponentialMovingAverage(DatasetI dataSet,InternalTimeSeriesIdAbstract id,String tagName,int periods,double smoothing) {
		super(dataSet);
		itsRef = (InternalTimeSeriesImpl<T>) this.dataSet.getTimeSeries(id);
		this.periods= periods;
		this.tagName = tagName;
		this.smoothing = smoothing;
	}
	
	public ExponentialMovingAverage(DatasetI dataSet,InternalTimeSeriesIdAbstract id,String tagName,int periods) {
		super(dataSet);
		itsRef = (InternalTimeSeriesImpl<T>) this.dataSet.getTimeSeries(id);
		this.periods= periods;
		this.tagName = tagName;
		this.smoothing = 2;
	}
	
	public DatasetImpl create() throws Exception {
	   List<InternalTimeSeriesPointI> itsRefList = (List<InternalTimeSeriesPointI>) itsRef.getList();
	   List<InternalSingleTagTimeSeriesPoint<Double>> res = new ArrayList<>(); 
	   if(itsRefList.size() < periods) {}  // run exception
	   double k = smoothing/(periods+1);
//	   // reflection invoked just once
       Method method = itsRef.getTagMethod(tagName);
       
       double count = itsRefList.stream().limit(periods).mapToDouble(point -> {
           try { 
        	   Double result = (Double) method.invoke(point);
               return result != null ? result : 0;
           } catch (IllegalAccessException | InvocationTargetException e) {
        	   return 0;
           }
       }).average().orElse(Double.NaN);
       
	   for(int i = periods;i<itsRefList.size()-periods;i++) {
		   
		   count = (Double)method.invoke(itsRefList.get(i))*k + count*(1-k);
	       res.add(new InternalSingleTagTimeSeriesPoint<Double>(itsRefList.get(i).getTime(),count));
	   }
	   InternalTimeSeriesIdImpl id = new InternalTimeSeriesIdImpl(itsRef.getFirstDate(),itsRef.getLastDate(),"EMA","");
	   itsRes = new InternalTimeSeriesImpl(new RBTree(res),id);
	   DatasetImpl ds = new DatasetImpl();
	   ds.addTimeSeries(itsRes);
	   
	   System.out.println(itsRef.getString());
	   System.out.println();
	   System.out.println(itsRes.getString());
	   
	   return ds;
	   
	}

	@Override
	public boolean dataSetCheck() {
		// TODO Auto-generated method stub
		return false;
	}
}
