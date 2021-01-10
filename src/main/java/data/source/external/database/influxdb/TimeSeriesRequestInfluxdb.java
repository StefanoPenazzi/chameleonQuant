/**
 * 
 */
package data.source.external.database.influxdb;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.influxdb.annotation.Column;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;

import data.source.annotations.InternalQueryAnnotation.InternalQueryInfo;
import data.source.annotations.TimeSeriesAnnotations.Function;
import data.source.internal.timeseries.TimeSeriesIdI;
import data.source.internal.timeseries.TimeSeriesRequestI;
import data.source.internal.timeseries.TimeSeriesRequestIdI;
import data.source.internal.timeseries.point.TimeSeriesPointAbstract;
import data.source.internal.timeseries.point.TimeSeriesPointI;
import data.source.utils.IO.ReflectionsUtils;

/**
 * @author stefanopenazzi
 *
 */
public class TimeSeriesRequestInfluxdb implements TimeSeriesRequestI {
	
	private static final Logger logger = LogManager.getLogger(TimeSeriesRequestInfluxdb.class);
	
	final String serverURL;
	final String username;
	final String password;
	
	public TimeSeriesRequestInfluxdb() {
		Properties properties = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("database.properties");	
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    this.serverURL = properties.getProperty("influx_serverURL");
	    this.username = properties.getProperty("influx_username");
	    this.password = properties.getProperty("influx_password");
	}
	
	private String getStringQuery(TimeSeriesRequestIdInfluxdb iq) {
		
		String res ="SELECT ";
		
		String st = iq.getStartTime();
		String et = iq.getEndTime();
		
		//TODO check that the dates range is not too big compare to the interval???
		//build the fields
		Field[] fields = iq.getTimeSeriesPoint().getDeclaredFields();
		for(Field field: fields) {
			Annotation[] annotations = field.getDeclaredAnnotations();
			String function = "";
			String column = "";
			for(Annotation annotation : annotations){
			    if(annotation instanceof Function){
			    	Function myAnnotation = (Function) annotation;
			        function =  myAnnotation.name();
			    }
			    if(annotation instanceof Column){
			    	Column myAnnotation = (Column) annotation;
			        column =  myAnnotation.name();
			    }
			}
			if(column == "time" || column == "") {
				continue;
			}
			else if(function != "") {
				res = res+ " "+function+"("+column+") AS " + column+", ";
			}
		}
		 if (res.charAt(res.length() - 1) == ' ' && res.charAt(res.length() - 2) ==',') {
			 res = res.substring(0, res.length() - 2);
		}
		res = res + " FROM \""+iq.getTicker()+"\" WHERE time > '" + st + "' and time < '"+ et + "' GROUP BY time("+iq.getInterval()+")" ;
		return res;
	}

	@Override
	public List<? extends TimeSeriesPointI> getTimeSeries(TimeSeriesRequestIdI iqp) {
		
		TimeSeriesRequestIdInfluxdb iq = (TimeSeriesRequestIdInfluxdb)iqp;
		Influxdb idb = new Influxdb();
		idb.connect();
		//Query data from InfluxDB
		String db = "";
		try {
			db = (String)(ReflectionsUtils.getMethodsAnnotatedWith(TimeSeriesRequestIdInfluxdb.class,InternalQueryInfo.class,"database").invoke(iq));   //TODO reflection not necessary anymore!!!!!!!!!
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Query query = new Query(getStringQuery(iq),db);
		QueryResult queryResult = idb.getInfluxDB().query(query);
		String measurement = iq.getTicker();
		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		List<? extends TimeSeriesPointI> results = resultMapper.toPOJO(queryResult, iq.getTimeSeriesPoint(), measurement );   
		idb.close();
		return results;
	}
}

	
