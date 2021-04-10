package data.source.internal.timeseries.analysis;

import java.util.List;
import java.util.TreeMap;

import data.source.internal.timeseries.TimeSeriesI;

public class Autocorrelation {
	
	public static TreeMap<Integer,List<Double>> correlogram(TimeSeriesI ts, String source ,int minLag, int maxLag){
		
		TreeMap<Integer,List<Double>> res = new TreeMap<Integer,List<Double>>();
		
		return res;
		
	}

}
