/**
 * 
 */
package indicators.volatility;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import data.source.internal.dataset.DatasetI;
import data.source.internal.dataset.DatasetHistoricalImpl;
import data.source.internal.timeseries.TimeSeriesI;
import data.source.internal.timeseries.TimeSeriesIdAbstract;
import data.source.internal.timeseries.point.SingleTagPoint;
import data.source.internal.timeseries.point.TimeSeriesPointI;
import data.source.internal.timeseries.standard.TimeSeriesIdImpl;
import data.source.internal.timeseries.standard.TimeSeriesImpl;
import data.source.internal.timeseries.structure.RBTree;
import indicators.IndicatorAbstract;
import indicators.IndicatorI;
import indicators.movingAverage.MACD;
import indicators.movingAverage.MACD.Builder;

/**
 * @author stefanopenazzi
 *
 */
public class TrueRange implements IndicatorI {

	private TimeSeriesI itsRef;
	
	
	 public static class Builder {
		
		private TimeSeriesI ts;
		 
		public Builder(TimeSeriesI ts) {
	        this.ts = ts;
	    }
		
		 public TrueRange build(){
			TrueRange tr = new TrueRange();
			tr.itsRef = this.ts;
            return tr;
		}		
	}
	
	public TimeSeriesImpl run() throws Exception {
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
	       
		   TimeSeriesIdImpl id = new TimeSeriesIdImpl.Builder("TR")
					 .startInstant(itsRef.getFirstInstant())
					 .endInstant(itsRef.getLastInstant())
					 .build();
		   TimeSeriesImpl itsRes = new TimeSeriesImpl(new RBTree(res),id);
		   return  itsRes;
		}

}
