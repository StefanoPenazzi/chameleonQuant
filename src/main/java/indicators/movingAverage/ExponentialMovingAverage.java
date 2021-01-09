/**
 * 
 */
package indicators.movingAverage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import data.source.internal.dataset.DatasetI;
import data.source.internal.dataset.DatasetImpl;
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
public class ExponentialMovingAverage<T extends TimeSeriesPointI> extends IndicatorAbstract  {

	private final TimeSeriesImpl<T> itsRef;
	private TimeSeriesImpl <SingleTagPoint<Double>> itsRes;
	private final int periods;
	private final String tagName;
	private final double smoothing;
	
	/**
	 * @param dataSet
	 */
	public ExponentialMovingAverage(DatasetI dataSet,String tagName,int periods,double smoothing) {
		super(dataSet);
		itsRef = (TimeSeriesImpl<T>) this.dataSet.iterator().next();
		this.periods= periods;
		this.tagName = tagName;
		this.smoothing = smoothing;
	}
	
	public ExponentialMovingAverage(DatasetI dataSet,TimeSeriesIdAbstract id,String tagName,int periods,double smoothing) {
		super(dataSet);
		itsRef = (TimeSeriesImpl<T>) this.dataSet.getTimeSeries(id);
		this.periods= periods;
		this.tagName = tagName;
		this.smoothing = smoothing;
	}
	
	public ExponentialMovingAverage(DatasetI dataSet,TimeSeriesIdAbstract id,String tagName,int periods) {
		super(dataSet);
		itsRef = (TimeSeriesImpl<T>) this.dataSet.getTimeSeries(id);
		this.periods= periods;
		this.tagName = tagName;
		this.smoothing = 2;
	}
	
	public DatasetImpl create() throws Exception {
	   List<TimeSeriesPointI> itsRefList = (List<TimeSeriesPointI>) itsRef.getList();
	   List<SingleTagPoint<Double>> res = new ArrayList<>(); 
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
	       res.add(new SingleTagPoint<Double>(itsRefList.get(i).getTime(),count));
	   }
	   TimeSeriesIdImpl id = new TimeSeriesIdImpl(itsRef.getFirstInstant(),itsRef.getLastInstant(),"EMA","",SingleTagPoint.class);
	   itsRes = new TimeSeriesImpl(new RBTree(res),id);
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
