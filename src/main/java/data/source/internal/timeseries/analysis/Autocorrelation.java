package data.source.internal.timeseries.analysis;

import java.lang.reflect.InvocationTargetException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import data.source.internal.timeseries.TimeSeriesI;
import data.source.internal.timeseries.point.TimeSeriesPointI;

/**
 * 
 * @author stefanopenazzi
 * 
 */

public class Autocorrelation {
	
	
	/**
	 * 
	 * @param ts Time-series implementing the interface TimeSeriesI
	 * @param source Name of one of the methods in the time-series with annotation @see data.source.annotations.TimeSeriesAnnotations#TagName
	 * @param maxLag
	 * 
	 * <b>Note:</b> Upper and lower bounds for autocorrelation consider a significance level equals to 0.05. The time-series is considered Gaussian. 
	 * 
	 * @return List of Double containing for each lag: autocorrelation coefficient, standard error lower bound, standard error upper bound, autocorrelation upper bound, autocorrelation lower bound.
	 *
	 */
	public static TreeMap<Integer,List<Double>> correlogram(TimeSeriesI ts, String source, int maxLag){
		
		TreeMap<Integer,List<Double>> res = new TreeMap<Integer,List<Double>>();
		
		
		List<TimeSeriesPointI> _ts = (List<TimeSeriesPointI>) ts.getList();
		Method method = ts.getTagMethod(source);
		Double mean = Stat.mean(ts, source);
		Double variance = Stat.variance(ts, source);
		int n = _ts.size();
		Double ris = 0.0;
		
		
		for(int i =1;i<= maxLag;i++) {
			Double ci  = 0.0;
			Double ri = null;
			Double seLB = null;
			Double seUB = null;
			Double lb = null;
			Double ub = null;
			
			for(int j = 0;j<_ts.size()-i;j++) {
				try {
					Double valJ = (Double) method.invoke(_ts.get(j));
					Double valJI = (Double) method.invoke(_ts.get(j+i));
					ci += (valJ-mean)*(valJI-mean);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			ri = (ci/n)/variance;
			
			
			if(i == 1) {
				seUB = 1/Math.sqrt(n);
				seLB = -seUB;
			}
			else {
				seUB = Math.sqrt((1+2*ris)/n);
				seLB = -seUB;
			}
			
			ub = 1.96 * seUB;
			lb = -ub;
			
			//update
			ris += Math.pow(ri,2);
			
			List<Double> riRes = new ArrayList<Double>();
			riRes.add(ri);
			riRes.add(seUB);
			riRes.add(seLB);
			riRes.add(ub);
			riRes.add(lb);
			
			res.put(i,riRes);
				               
		}
		
		return res;
		
	}

}
