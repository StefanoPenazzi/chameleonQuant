/**
 * 
 */
package data.source.external.database.influxdb;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.influxdb.annotation.Column;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import com.google.inject.Inject;

import data.source.annotation.InternalTimeSeries.Function;
import data.source.internal.dataset.timeseries.InternalStockQuery;
import data.source.internal.dataset.timeseries.InternalTimeSeriesQueryI;
import data.source.internal.dataset.timeseries.InternalTimeSeriesQueryRequest;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPoint;

/**
 * @author stefanopenazzi
 *
 */
public class InternalTimeSeriesQueryInfluxdb implements InternalTimeSeriesQueryRequest {
	
	private Map<String,InternalTimeSeriesPoint> internalTimeSeriesPointMap;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	@Inject
	public InternalTimeSeriesQueryInfluxdb(Map<String,InternalTimeSeriesPoint> internalTimeSeriesPointMap) {
		this.internalTimeSeriesPointMap = internalTimeSeriesPointMap;
	}
	
	private String getStringQuery(InternalStockQuery iq) {
		
		//"SELECT first(open) AS open, last(close) AS close, max(high) AS high, min(low) AS low, sum(volume) AS volume FROM "+stock+" WHERE time>'2020-10-19 09:30:00'  GROUP BY time(8h)
		
		String res ="SELECT ";
		
		//TODO what happens if one or both the dates are null?
		String st = iq.getStartDate() == null? "1990-01-01 00:00:00":sdf.format(iq.getStartDate()).toString(); 
		String et = iq.getEndDate() == null?  LocalDateTime.now().format(formatter) : sdf.format(iq.getEndDate()).toString();
		
		//TODO check that the dates range is not too big compare to the interval???
		
		
		//build the fields
		Field[] fields = internalTimeSeriesPointMap.get(iq.getMarket()).getClass().getDeclaredFields();
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
	
	private String getStringDB() {
		return null;
	}
	
	@Override
	public List<? extends InternalTimeSeriesPoint> getResult(InternalTimeSeriesQueryI iqI){
		//TODO what if this is not an InternalStockQuery 
		InternalStockQuery iq = (InternalStockQuery)iqI;
		Influxdb idb = new Influxdb();
		final String serverURL = "http://127.0.0.1:7086", username = "stefanopenazzi", password = "korky1987";
		String[] dbCon = {serverURL,username,password};
		//the server must be on(service influxdb start) otherwise the connection will not be successful
		idb.connect(dbCon);
		
		//Query data from InfluxDB
		Query query = new Query(getStringQuery(iq), iq.getMarket());
		QueryResult queryResult = idb.getInfluxDB().query(query);
		//Convert QueryResult to POJO
		String measurement = iq.getCode();
		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		List<? extends InternalTimeSeriesPoint> results = resultMapper.toPOJO(queryResult, internalTimeSeriesPointMap.get(iq.getMarket()).getClass(), measurement );   
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
