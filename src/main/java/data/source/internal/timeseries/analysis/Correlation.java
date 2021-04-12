package data.source.internal.timeseries.analysis;

import java.lang.reflect.InvocationTargetException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import data.source.internal.timeseries.TimeSeriesI;
import data.source.internal.timeseries.point.TimeSeriesPointI;

/**
 * 
 * @author stefanopenazzi
 * 
 */

public class Correlation {
	
	
	/**
	 * 
	 * <b>Note:</b> Upper and lower bounds for autocorrelation consider a significance level equals to 0.05. The time-series is considered Gaussian. 
	 * 
	 * @param ts Time-series implementing the interface TimeSeriesI
	 * @param source Name of one of the methods in the time-series with annotation {@link data.source.annotations.TimeSeriesAnnotations#TagName}
	 * @param maxLag .
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
			
			res.put(i,Collections.unmodifiableList(riRes));
				               
		}
		
		return res;
		
	}
	
	/**
	 * 
	 * <b>Note:</b> Upper and lower bounds for cross-correlation consider a significance level equals to 0.05.
	 * The time-series is considered Gaussian.
	 * ts2 must be a sub-list of ts1 in terms of times.
	 * 
	 * @param ts1 Time-series implementing the interface TimeSeriesI
	 * @param ts2 Time-series implementing the interface TimeSeriesI
	 * @param source Name of one of the methods in the time-series with annotation {@link data.source.annotations.TimeSeriesAnnotations#TagName}
	 * @param maxLag .
	 * 
	 * @return List of Double containing for each lag: cross-correlation coefficient, standard error lower bound, standard error upper bound, cross-correlation upper bound, cross-correlation lower bound.
	 *
	 */
	
     public static TreeMap<Integer,List<Double>> crossCorrelation(TimeSeriesI ts1, TimeSeriesI ts2 ,String source, int maxLag){
		
		TreeMap<Integer,List<Double>> res = new TreeMap<Integer,List<Double>>();
		
		
		List<TimeSeriesPointI> _ts1 = (List<TimeSeriesPointI>) ts1.getList();
		List<TimeSeriesPointI> _ts2 = (List<TimeSeriesPointI>) ts2.getComparableList(ts1);
		
		Method method = ts1.getTagMethod(source);
		//TODO check ts2
		
		Double mean1 = Stat.mean(ts1, source);
		Double sd1 = Stat.standardDeviation(ts1, source);
		int n1 = _ts1.size();
		
		Double mean2 = Stat.mean(ts2, source);
		Double sd2 = Stat.standardDeviation(ts2, source);
		int n2 = _ts2.size();
		
		if(n1 != n2) {
			//throw error
		}
		
		Double ris = 0.0;
		
		
		for(int i =1;i<= maxLag;i++) {
			Double ci  = 0.0;
			Double ri = null;
			Double seLB = null;
			Double seUB = null;
			Double lb = null;
			Double ub = null;
			
			for(int j = 0;j<n1-i;j++) {
				try {
					Double valJ1 = (Double) method.invoke(_ts1.get(j));
					Double valJI2 = (Double) method.invoke(_ts2.get(j+i));
					ci += (valJ1-mean1)*(valJI2-mean2);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			ri = (ci/n1)/(Math.sqrt(sd1*sd2));
			
			
			if(i == 1) {
				seUB = 1/Math.sqrt(n1);
				seLB = -seUB;
			}
			else {
				seUB = Math.sqrt((1+2*ris)/n1);
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
			
			res.put(i,Collections.unmodifiableList(riRes));
				               
		}
		
		return res;
		
	}

}
