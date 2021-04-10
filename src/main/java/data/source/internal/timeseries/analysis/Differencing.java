package data.source.internal.timeseries.analysis;

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


/**
 * @author stefanopenazzi
 *
 */

public class Differencing {

	public static TimeSeriesImpl differencing(TimeSeriesI ts, String source ,int lag) {
		
	   List<TimeSeriesPointI> _ts = (List<TimeSeriesPointI>) ts.getList();
	   List<SingleTagPoint<Double>> res = new ArrayList<>(); 
       Method method = ts.getTagMethod(source);
	   
	   for(int i = lag;i<_ts.size();i++) {
	        try {
				res.add(new SingleTagPoint<Double>(_ts.get(i).getTime(),(Double)method.invoke(_ts.get(i)) - (Double)method.invoke(_ts.get(i-lag))));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
	   }
	   
	   TimeSeriesIdImpl id = new TimeSeriesIdImpl.Builder("Differencing")
				 .startInstant(ts.getFirstInstant())
				 .endInstant(ts.getLastInstant())
				 .build();
	   
	   TimeSeriesImpl itsRes = new TimeSeriesImpl(new RBTree(res),id);
	   return itsRes;
	}

}
