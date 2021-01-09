/**
 * 
 */
package data.source.internal.timeseries;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import data.source.annotations.TimeSeriesAnnotations.Column;
import data.source.external.financialdatavendors.alphavantage.mirrors.StockEODTimeSeriesPointAlphaVantage;
import data.source.internal.timeseries.point.TimeSeriesPointI;

/**
 * @author stefanopenazzi
 *
 */
public class Utils {
	
	public static List<TimeSeriesPointI> map2PointsList(List<Map<String,String>> inputMap, Class<? extends TimeSeriesPointI> tspClass ) {
		
		List<TimeSeriesPointI> pointsList = new ArrayList<>();
		DateTimeFormatter formatter = null;
		try {
			formatter = tspClass.newInstance().getTimeFormat();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		//TODO check if it is possible
		for(Map<String,String> m: inputMap) {
			TimeSeriesPointI tsp = null;
			try {
				tsp = tspClass.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			Field[] fields = tspClass.getDeclaredFields();
		    for(Field field: fields) {
		    	Annotation[] annotations = field.getDeclaredAnnotations();
				String column = "";
				for(Annotation annotation : annotations){
				    if(annotation instanceof Column){
				    	field.setAccessible(true);
				    	Column myAnnotation = (Column) annotation;
				        column =  myAnnotation.name();
				        String res = m.get(column);
				        if(res == null) {
				        	//run exception
				        }
				        try {
					        if (Integer.class.isAssignableFrom(field.getType())) {
								field.set(tsp, Integer.valueOf(res));
					        } else if (Double.class.isAssignableFrom(field.getType())) {
					        	field.set(tsp, Double.valueOf(res));
					        } else if (Float.class.isAssignableFrom(field.getType())) {
					        	field.set(tsp, Float.valueOf(res));
					        } else if (String.class.isAssignableFrom(field.getType())) {
					        	field.set(tsp, String.valueOf(res));
					        } else if (Instant.class.isAssignableFrom(field.getType())) {
					        	Instant instant = null;
							    LocalDateTime dateTime = LocalDateTime.parse(res, formatter);
								OffsetDateTime utcDateTime = dateTime.atOffset(ZoneOffset.UTC);
								instant = utcDateTime.toInstant();
					        	field.set(tsp, instant);
					        }
					        else {
					        	//run exception
					        }
				        } catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
				        field.setAccessible(false);
				    }
				}
		    }	
		    pointsList.add(tsp);
		}
		return pointsList;
	}
}
