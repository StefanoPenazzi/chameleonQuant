/**
 * 
 */
package indicators.volatility;

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
public class TrueRange extends IndicatorAbstract {

	private final TimeSeriesImpl itsRef;
	private TimeSeriesImpl itsRes;
	
	/**
	 * @param dataSet
	 */
	public TrueRange(DatasetI dataSet,TimeSeriesIdAbstract id) {
		super(dataSet);
		this.itsRef = (TimeSeriesImpl) this.dataSet.getTimeSeries(id);
		
	}
	
	public TrueRange(DatasetI dataSet) {
		super(dataSet);
		itsRef = (TimeSeriesImpl) this.dataSet.iterator().next();
	}

	@Override
	public boolean dataSetCheck() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public DatasetImpl create() throws Exception {
		   List<TimeSeriesPointI> itsRefList = (List<TimeSeriesPointI>) itsRef.getList();
		   List<SingleTagPoint<Double>> res = new ArrayList<>(); 
//		   // reflection invoked just once
	       Method methodHigh = itsRef.getTagMethod("high");
	       Method methodLow = itsRef.getTagMethod("low");
	       Method methodClose = itsRef.getTagMethod("close");
		   
	       for(int i = 1; i<itsRefList.size();i++) {
	    	   
	    	   Double resultHigh = 0.;
        	   Double resultLow = 0.;
        	   Double resultClose = 0.;
        	   TimeSeriesPointI point = itsRefList.get(i);
        	   TimeSeriesPointI prevPoint = itsRefList.get(i-1);
        	   
	    	   try { 
	        	   resultHigh = (Double) methodHigh.invoke(point);
	        	   resultLow = (Double) methodLow.invoke(point);
	        	   resultClose = (Double) methodClose.invoke(prevPoint);
	           } catch (IllegalAccessException | InvocationTargetException e) {
	        	   return null;
	           }
	    	   res.add(new SingleTagPoint<Double>(point.getTime(),Math.max(Math.abs(resultLow-resultClose),Math.max((resultHigh-resultLow), Math.abs(resultHigh-resultClose)))));
	       }
	       
		   TimeSeriesIdImpl id = new TimeSeriesIdImpl(itsRef.getFirstInstant(),itsRef.getLastInstant(),"TR","");
		   itsRes = new TimeSeriesImpl(new RBTree(res),id);
		   DatasetImpl ds = new DatasetImpl();
		   ds.addTimeSeries(itsRes);
		   return ds;
		}

}
