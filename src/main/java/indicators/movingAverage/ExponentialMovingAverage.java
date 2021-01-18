/**
 * 
 */
package indicators.movingAverage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import data.source.internal.timeseries.TimeSeriesI;
import data.source.internal.timeseries.point.SingleTagPoint;
import data.source.internal.timeseries.point.TimeSeriesPointI;
import data.source.internal.timeseries.standard.TimeSeriesIdImpl;
import data.source.internal.timeseries.standard.TimeSeriesImpl;
import data.source.internal.timeseries.structure.RBTree;
import indicators.IndicatorI;

/**
 * @author stefanopenazzi
 *
 */
public class ExponentialMovingAverage implements IndicatorI  {

	private TimeSeriesI itsRef;
	private int length;
	private String source;
	private double smoothing;
	
    public static class Builder {
		
		private TimeSeriesI ts;
		private int length = 9;
		private String source = "close";
		private double smoothing = 2;
		
		public Builder(TimeSeriesI ts) {
	        this.ts = ts;
	    }
		public Builder length(int length){
            this.length = length;
            return this;
        }
		public Builder source(String source){
           this.source = source; 
            return this;
        }
		public Builder smoothing(double smoothing){
            this.smoothing = smoothing;
            return this;
        }
		 public ExponentialMovingAverage build(){
			ExponentialMovingAverage ema = new ExponentialMovingAverage(); 
			ema.itsRef = this.ts;
			ema.length = this.length;
			ema.source = this.source;
			ema.smoothing = this.smoothing;
            return ema;
		}		
	}
	
	
	public TimeSeriesImpl run() throws Exception {
	   List<TimeSeriesPointI> itsRefList = (List<TimeSeriesPointI>) itsRef.getList();
	   List<SingleTagPoint<Double>> res = new ArrayList<>(); 
	   if(itsRefList.size() < length) {
		// run exception
	   }  
	   double k = smoothing/(length+1);
//	   // reflection invoked just once
       Method method = itsRef.getTagMethod(source);
       double count = itsRefList.stream().limit(length).mapToDouble(point -> {
           try { 
        	   Double result = (Double) method.invoke(point);
               return result != null ? result : 0;
           } catch (IllegalAccessException | InvocationTargetException e) {
        	   return 0;
           }
       }).average().orElse(Double.NaN);
       
	   for(int i = length;i<itsRefList.size()-length;i++) {
		   
		   count = (Double)method.invoke(itsRefList.get(i))*k + count*(1-k);
	       res.add(new SingleTagPoint<Double>(itsRefList.get(i).getTime(),count));
	   }
	   TimeSeriesIdImpl id = new TimeSeriesIdImpl.Builder("EMA")
				 .startInstant(itsRef.getFirstInstant())
				 .endInstant(itsRef.getLastInstant())
				 .build();
	   TimeSeriesImpl itsRes = new TimeSeriesImpl(new RBTree(res),id);
	   return itsRes;
	}
}
