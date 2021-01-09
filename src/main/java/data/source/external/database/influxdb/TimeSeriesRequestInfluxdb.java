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
import data.source.annotation.InternalQueryAnnotation.InternalQueryInfo;
import data.source.annotation.InternalTimeSeries.Function;
import data.source.internal.timeseries.TimeSeriesIdI;
import data.source.internal.timeseries.TimeSeriesRequestI;
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
	//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd[ HH:mm:ss]")
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .toFormatter().withZone(ZoneId.of("America/New_York"));
	
	
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
	
	private String getStringQuery(TimeSeriesId iq) {
		
		//"SELECT first(open) AS open, last(close) AS close, max(high) AS high, min(low) AS low, sum(volume) AS volume FROM "+stock+" WHERE time>'2020-10-19 09:30:00'  GROUP BY time(8h)
		
		String res ="SELECT ";
		
		//TODO what happens if one or both the dates are null?
		String st = iq.getStartInstant() == null? "1990-01-01 00:00:00":formatter.format(iq.getStartInstant()).toString(); 
		String et = iq.getEndInstant() == null?  LocalDateTime.now().format(formatter) : formatter.format(iq.getEndInstant()).toString();
		
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
		res = res + " FROM "+iq.getTicker()+" WHERE time > '" + st + "' and time < '"+ et + "' GROUP BY time("+iq.getInterval()+")" ;
		
		return res;
	}
	
	@Override
	public List<? extends TimeSeriesPointI> getTimeSeries(TimeSeriesId iq) { 
		Influxdb idb = new Influxdb();
		idb.connect();
		//Query data from InfluxDB
		String db = "";
		try {
			db = (String)(ReflectionsUtils.getMethodsAnnotatedWith(TimeSeriesId.class,InternalQueryInfo.class,"database").invoke(iq));
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Query query = new Query(getStringQuery(iq),db);
		QueryResult queryResult = idb.getInfluxDB().query(query);
		//Convert QueryResult to POJO
		String measurement = iq.getTicker();
		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		List<? extends TimeSeriesPointI> results = resultMapper.toPOJO(queryResult, iq.getTimeSeriesPoint(), measurement );   
		idb.close();
		return results;
	}
}

	
