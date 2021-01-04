/**
 * 
 */
package data.source.external.database.influxdb;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
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
import data.source.internal.dataset.timeseries.InternalTimeSeriesIdI;
import data.source.internal.dataset.timeseries.InternalTimeSeriesQueryRequestAbstract;
import data.source.internal.dataset.timeseries.InternalTimeSeriesQueryRequestI;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPointAbstract;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPointI;
import data.source.internal.dataset.timeseries.standard.InternalStockId;
import data.source.utils.IO.ReflectionsUtils;

/**
 * @author stefanopenazzi
 *
 */
public class InternalTimeSeriesQueryRequestInfluxdb<T extends InternalTimeSeriesPointAbstract> implements InternalTimeSeriesQueryRequestI<T> {
	
	private static final Logger logger = LogManager.getLogger(InternalTimeSeriesQueryRequestInfluxdb.class);
	
	final String serverURL;
	final String username;
	final String password;
	//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd[ HH:mm:ss]")
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .toFormatter().withZone(ZoneId.of("America/New_York"));
	
	private final Class<T> itmp;
	
	
	public InternalTimeSeriesQueryRequestInfluxdb(Class<T> itmp) {
		this.itmp = itmp;
		Properties properties = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("database.properties");	
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    this.serverURL = properties.getProperty("influx_serverURL");
	    this.username = properties.getProperty("influx_username");
	    this.password = properties.getProperty("influx_password");
	}
	
	private String getStringQuery(InternalStockId iq) {
		
		//"SELECT first(open) AS open, last(close) AS close, max(high) AS high, min(low) AS low, sum(volume) AS volume FROM "+stock+" WHERE time>'2020-10-19 09:30:00'  GROUP BY time(8h)
		
		String res ="SELECT ";
		
		//TODO what happens if one or both the dates are null?
		String st = iq.getStartInstant() == null? "1990-01-01 00:00:00":formatter.format(iq.getStartInstant()).toString(); 
		String et = iq.getEndInstant() == null?  LocalDateTime.now().format(formatter) : formatter.format(iq.getEndInstant()).toString();
		
		//TODO check that the dates range is not too big compare to the interval???
		
		
		//build the fields
		Field[] fields = itmp.getDeclaredFields();
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
		res = res + " FROM "+iq.getCode()+" WHERE time > '" + st + "' and time < '"+ et + "' GROUP BY time("+iq.getInterval()+")" ;
		
		return res;
	}
	
	@Override
	public List<T> getResult(InternalTimeSeriesIdI iqI) {
		//TODO what if this is not an InternalStockQuery 
				InternalStockTimeSeriesQueryInfluxdb iq = (InternalStockTimeSeriesQueryInfluxdb)iqI;
				Influxdb idb = new Influxdb();
				String[] dbCon = {this.serverURL,this.username,this.password};
				//the server must be on(service influxdb start) otherwise the connection will not be successful
				idb.connect(dbCon);
				
				//Query data from InfluxDB
				String db = "";
				try {
					db = (String)(ReflectionsUtils.getMethodsAnnotatedWith(InternalStockTimeSeriesQueryInfluxdb.class,InternalQueryInfo.class,"database").invoke(iq));
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
				String measurement = iq.getCode();
				InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
				List<T> results = (List<T>) resultMapper.toPOJO(queryResult, itmp, measurement );   
				idb.close();
				return results;
	}

	
	
	enum Interval{
		MIN1("1m"),
		MIN2("2m"),
		MIN5("5m"),
		MIN10("10m"),
		MIN15("15m"),
		MIN30("30m"),
		MIN45("45m"),
		HOUR1("1h"),
		HOUR2("2h"),
		HOUR4("4h"),
		HOUR8("8h"),
		HOUR12("12h"),
		HOUR16("16h");
		
		private final String inter;
		
		Interval(String inter){
			this.inter = inter;
		}
		
		public String getInterval() {
			return this.inter;
		}
		
	}
	
	enum Market{
		
		US_STOCKS("US_STOCKS_TIME_SERIES_INTRADAY_1MIN"),
		FOREX(""),
		CRYPTO("");
		
        private final String market;
		
		Market(String market){
			this.market = market;
		}
		
		public String getMarket() {
			return this.market;
		}
	}

}

	
