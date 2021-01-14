/**
 * 
 */
package data.source.external.database.influxdb;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.influxdb.annotation.Column;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;

import com.google.inject.Inject;

import data.source.annotations.TimeSeriesAnnotations.Function;
import data.source.external.database.influxdb.utils.queries.StructureQuery;
import data.source.internal.timeseries.TimeSeriesRequestI;
import data.source.internal.timeseries.TimeSeriesRequestIdI;
import data.source.internal.timeseries.point.TimeSeriesPointI;
import data.source.utils.IO.CSVUtils;

/**
 * @author stefanopenazzi
 *
 */
public class TimeSeriesRequestInfluxdb implements TimeSeriesRequestI {
	
	private static final Logger logger = LogManager.getLogger(TimeSeriesRequestInfluxdb.class);
	private final Map<String,String> measureDatabaseMap;
	private final Map<String,Class<? extends TimeSeriesPointI>> classDatabaseMap;
	
	@Inject
	public TimeSeriesRequestInfluxdb() throws ClassNotFoundException {
		List<String> databasesList = new ArrayList<String>();
		classDatabaseMap = new HashMap<String,Class<? extends TimeSeriesPointI>>();
		List<Map<String,String>> dbClassesLM = null;
		URL res = getClass().getClassLoader().getResource("influx/databases.csv");
		File file=null;
		try {
			dbClassesLM = CSVUtils.parseCsv2Map(Paths.get(res.toURI()).toFile(),true, ';','"');
		} catch (FileNotFoundException | URISyntaxException e) {
			e.printStackTrace();
		}
		for(Map<String,String> m: dbClassesLM) {
			databasesList.add(m.get("name"));
			classDatabaseMap.put(m.get("name"),(Class<? extends TimeSeriesPointI>) Class.forName(m.get("class")));
		}
		measureDatabaseMap = StructureQuery.getDatabaseMap(databasesList);
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
		res = res + " FROM \""+iq.getId()+"\" WHERE time > '" + st + "' and time < '"+ et + "' GROUP BY time("+iq.getInterval()+")" ;
		return res;
	}

	@Override
	public List<? extends TimeSeriesPointI> getTimeSeries(TimeSeriesRequestIdI iqp) {
		TimeSeriesRequestIdInfluxdb iq = (TimeSeriesRequestIdInfluxdb)iqp;
		if(iq.getTimeSeriesPoint() == null) { 
			iq = new TimeSeriesRequestIdInfluxdb(iq.getTimeSeriesId(),classDatabaseMap.get(measureDatabaseMap.get(iq.getTimeSeriesId().getId())));
		}
		Influxdb idb = new Influxdb();
		idb.connect();
		String db = measureDatabaseMap.get(iq.getId());
		//TODO run exception if the id is not present in the map
		Query query = new Query(getStringQuery(iq),db);
		QueryResult queryResult = idb.getInfluxDB().query(query);
		String measurement = iq.getId();
		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		List<? extends TimeSeriesPointI> results = resultMapper.toPOJO(queryResult,iq.getTimeSeriesPoint(), measurement );   
		idb.close();
		return results;
	}
	
	public Class<? extends TimeSeriesPointI> getPointClassFromDatabase(String s){
		return classDatabaseMap.get(s);
	}
	
	public String getDatabaseFromMeasure(String s){
		return measureDatabaseMap.get(s);
	}
}

	
