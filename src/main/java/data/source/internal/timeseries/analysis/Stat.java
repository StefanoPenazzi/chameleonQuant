package data.source.internal.timeseries.analysis;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.OptionalDouble;
import java.util.TreeMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import data.source.internal.timeseries.TimeSeriesI;
import data.source.internal.timeseries.point.TimeSeriesPointI;

public class Stat {
	
	public static double mean(TimeSeriesI ts, String source ) {
		
	   List<TimeSeriesPointI> _ts = (List<TimeSeriesPointI>) ts.getList();
       Method method = ts.getTagMethod(source);
       
       OptionalDouble m = _ts.stream().mapToDouble(point -> {
           try { 
        	   Double result = (Double) method.invoke(point);
               return result != null ? result : 0;
           } catch (IllegalAccessException | InvocationTargetException e) {
        	   return 0;
           }
       }).average();
       
	   return m.getAsDouble();
		
	}
	
    public static double variance(TimeSeriesI ts, String source ) {
    	
       List<TimeSeriesPointI> _ts = (List<TimeSeriesPointI>) ts.getList();
       Method method = ts.getTagMethod(source);
       double m = mean(ts,source);
       
       double v = _ts.stream()
    		   .mapToDouble(point -> {
	        	   Double result;
				try {
					result = (Double) method.invoke(point);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
					return 0;
				}
	               return result != null ? result : 0;
    	        })
    		   .map(i -> i-m)
               .map(i -> i*i)
               .map(i -> i).average().getAsDouble();
  	   return v;
	}
    
    public static double qOne(TimeSeriesI ts, String source ) {
    	
        List<TimeSeriesPointI> _ts = (List<TimeSeriesPointI>) ts.getList();
        Method method = ts.getTagMethod(source);
        
        double q1 = _ts.stream().sorted()
        		.skip(Math.max(0, ((_ts.size() / 2 + 1) / 2) - 1))
                .limit(1 + (1 + _ts.size() / 2) % 2)
                .mapToDouble(point -> {
  	        	   Double result;
  				try {
  					result = (Double) method.invoke(point);
  				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
  					e.printStackTrace();
  					return 0;
  				}
  	               return result != null ? result : 0;
      	        })
                .average().getAsDouble();
   	   return q1;
 	}
    
    public static double qTwo(TimeSeriesI ts, String source ) {
     	
        List<TimeSeriesPointI> _ts = (List<TimeSeriesPointI>) ts.getList();
        Method method = ts.getTagMethod(source);
        
        double q1 = _ts.stream().sorted()
        		.skip(Math.max(0, ((_ts.size() + 1) / 2) - 1))
                .limit(1 + (1 + _ts.size()) % 2)
                .mapToDouble(point -> {
  	        	   Double result;
  				try {
  					result = (Double) method.invoke(point);
  				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
  					e.printStackTrace();
  					return 0;
  				}
  	               return result != null ? result : 0;
      	        })
                .average().getAsDouble();
   	   return q1;
 	}
    
    public static double qThree(TimeSeriesI ts, String source ) {
     	
        List<TimeSeriesPointI> _ts = (List<TimeSeriesPointI>) ts.getList();
        Method method = ts.getTagMethod(source);
        
        double q1 = _ts.stream().sorted()
        		.skip(Math.max(0, ((_ts.size() + 1) / 2) ))
                .skip(Math.max(0, ((_ts.size() / 2 + 1) / 2) - 1))
                .limit(1 + (1 + _ts.size() / 2) % 2)
                .mapToDouble(point -> {
  	        	   Double result;
  				try {
  					result = (Double) method.invoke(point);
  				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
  					e.printStackTrace();
  					return 0;
  				}
  	               return result != null ? result : 0;
      	        })
                .average().getAsDouble();
   	   return q1;
 	}
    
    public static double mode(TimeSeriesI ts, String source ) {
     	
        List<TimeSeriesPointI> _ts = (List<TimeSeriesPointI>) ts.getList();
        Method method = ts.getTagMethod(source);
        
        Double mode = _ts.stream()
        		.mapToDouble(point -> {
   	        	   Double result;
   				try {
   					result = (Double) method.invoke(point);
   				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
   					e.printStackTrace();
   					return 0;
   				}
   	               return result != null ? result : 0;
       	        })
        		.boxed()
                .collect(Collectors.groupingBy(i -> i, () -> new TreeMap<Double, Long>(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((a, b) -> {
                    if (!a.getValue().equals(b.getValue()))
                        return b.getValue().compareTo(a.getValue());
                    return a.getKey().compareTo(b.getKey());
                })
                .findFirst()
                .get()
                .getKey();
        return mode;
 	}
    
    public static double standardDeviation(TimeSeriesI ts, String source ) {
    	return  Math.sqrt(variance(ts,source));
    }

}
