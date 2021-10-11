/**
 * 
 */
package indicators.movingAverage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.DoubleStream;

import data.source.annotations.InternalQueryAnnotation.InternalQueryInfo;
import data.source.annotations.TimeSeriesAnnotations.TagName;
import data.source.internal.dataset.DatasetI;
import data.source.internal.dataset.DatasetHistoricalImpl;
import data.source.internal.timeseries.TimeSeriesAbstract;
import data.source.internal.timeseries.TimeSeriesI;
import data.source.internal.timeseries.TimeSeriesIdAbstract;
import data.source.internal.timeseries.point.SingleTagPoint;
import data.source.internal.timeseries.point.TimeSeriesPointI;
import data.source.internal.timeseries.standard.TimeSeriesIdImpl;
import data.source.internal.timeseries.standard.TimeSeriesImpl;
import data.source.internal.timeseries.standard.TimeSeriesIdImpl.Builder;
import data.source.internal.timeseries.structure.RBTree;
import data.source.utils.IO.ReflectionsUtils;
import indicators.IndicatorAbstract;
import indicators.IndicatorI;

/**
 * @author stefanopenazzi
 *
 */
public class SimpleMovingAverage implements IndicatorI {

	private TimeSeriesI itsRef;
	private int length;
	private String source;
	private int offset;
	
	public static class Builder {
		
		private TimeSeriesI ts;
		private int length = 9;
		private String source = "close";
		private int offset = 0;
		
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
		public Builder offset(int offset){
            this.offset = offset;
            return this;
        }
		 public SimpleMovingAverage build(){
			SimpleMovingAverage sma = new SimpleMovingAverage(); 
			sma.itsRef = this.ts;
			sma.length = this.length;
			sma.source = this.source;
			sma.offset = this.offset;
			
            return sma;
		}		
	}
	
	
	public TimeSeriesImpl run() throws Exception {
	   List<TimeSeriesPointI> itsRefList = (List<TimeSeriesPointI>) itsRef.getList();
	   List<SingleTagPoint<Double>> res = new ArrayList<>(); 
	   if(itsRefList.size() < length) {}  // run exception
	   int firstRemoveIndex = 0;
//	   // reflection invoked just once
       Method method = itsRef.getTagMethod(source);
	   
       double count = itsRefList.stream().limit(length).mapToDouble(point -> {
           try { 
        	   Double result = (Double) method.invoke(point);
               return result != null ? result : 0;
           } catch (IllegalAccessException | InvocationTargetException e) {
        	   return 0;
           }
       }).sum();
       
	   res.add(new SingleTagPoint<Double>(itsRefList.get(length-1).getTime(),count/length));
	   
	   for(int i = length;i<itsRefList.size();i++) {
	        count = count -  (Double)method.invoke(itsRefList.get(firstRemoveIndex)) + (Double)method.invoke(itsRefList.get(i));
	        res.add(new SingleTagPoint<Double>(itsRefList.get(i).getTime(),count/length));
	        firstRemoveIndex++;
	   }
	   
	   TimeSeriesIdImpl id = new TimeSeriesIdImpl.Builder("MA")
				 .startInstant(itsRef.getFirstInstant())
				 .endInstant(itsRef.getLastInstant())
				 .build();
	   
	   TimeSeriesImpl itsRes = new TimeSeriesImpl(new RBTree(res),id);
	   return itsRes;
	}

}
