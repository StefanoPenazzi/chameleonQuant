/**
 * 
 */
package data.source.external.database.influxdb;

import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.annotation.Column;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Point.Builder;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;

import data.source.external.database.Database;
import data.source.external.database.Mirror;
import data.source.utils.IO.CSVUtils;


/**
 * @author stefanopenazzi
 *
 */
public class Influxdb implements Database {

	private final static Logger log = LogManager.getLogger(Influxdb.class);
	
	private InfluxDB influxDB = null; 
	
	@Override
	public void connect(String... con) {
		influxDB = InfluxDBFactory.connect(con[0], con[1], con[2]);
	}

	@Override
	public boolean pingServer() {
		//verify the connection
		Pong response = this.influxDB.ping();
		if (response.getVersion().equalsIgnoreCase("unknown")) {
		    log.error("Error pinging server.");
		    return false;
		} 
		return true;
	}

	 /**
     * Write a csv file into the database
     *
     * @param dbName
     *            database name
     * @param csvFile
     *            path to the csv file
     * @param mirror
     *            this is a class from which reflection allows to assign the types to the fields
     * @param options
     *            firstRowAsHeader, separators, customQuote 
     * @return true if it was successful.
     */
	@Override
	public boolean writingBatchFromCsvFile(String dbName, String table ,String csvFile, Class<? extends Mirror> mirror ,Object... options) {
		List<Map<String, String>> csvMap = null;
		try {
			csvMap = CSVUtils.readCSV(csvFile, (boolean)options[0], (char)options[1], (char)options[2]);
		} catch (FileNotFoundException e) {
			log.error("File "+ csvFile + "not found.");
			e.printStackTrace();
		}
		return writingBatchFromMap(dbName,table,mirror,csvMap);
	}

	public boolean writingBatchFromMap( String dbName,  String table , Class<? extends Mirror> mirror ,List<Map<String,String>> csvMap) {
		boolean pingCheck = pingServer();
		if (!pingCheck) {return false;}
		
		Map<String,Class<?>> mirrorMapType =  getMapTypes(mirror);
		
		DateTimeFormatter formatter = null;
		try {
			formatter = mirror.newInstance().getTimeFormat();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String timeDaily = "time";
		
		BatchPoints batchPoints = BatchPoints
				  .database(dbName)
				  .build();
		//TODO all wrong
		for(Map<String, String> m: csvMap) {
			
			Number date = null;
			
		    LocalDateTime dateTime = LocalDateTime.parse(m.get(timeDaily), formatter);
			OffsetDateTime utcDateTime = dateTime.atOffset(ZoneOffset.UTC);
			date = utcDateTime.toInstant().toEpochMilli();
					
			Builder bui = Point.measurement(table)
					  .time(date, TimeUnit.MILLISECONDS);
			for(String key: m.keySet()) {
				if(!key.equals(timeDaily) && mirrorMapType.containsKey(key)) {
					
					if( mirrorMapType.get(key) == Double.class) {
						bui.addField(key,Double.parseDouble(m.get(key)));
					}
					else if( mirrorMapType.get(key) == Float.class) {
						bui.addField(key,Float.parseFloat(m.get(key)));
					}
					else if( mirrorMapType.get(key) == String.class) {
						bui.addField(key,m.get(key));
					}
				}
			}
			Point point = bui.build();
			
			batchPoints.point(point);
		}
		
		influxDB.write(batchPoints);
		
		return true;
	}

	@Override
	public boolean writingBatchFromCsvString( String dbName, String csv, Class<? extends Mirror> mirror ,Object... options) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void close() {
		pingServer();
		influxDB.close();
	}

	@Override
	public List<? extends Mirror> performQuery(String query,String database,Class<? extends Mirror> cl) {
		QueryResult queryResult = influxDB.query(new Query(query,database));
				 
				InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
				List<? extends Mirror> pointList = resultMapper.toPOJO(queryResult, cl);
				 
				//assertEquals(2, memoryPointList.size());
				//assertTrue(4743696L == memoryPointList.get(0).getFree());
		return pointList;
	}
	
	private Map<String,Class<?>> getMapTypes( Class<? extends Mirror> mirror){
		
		 Map<String,Class<?>> res = new HashMap<>();
		 Field[] declaredFields = mirror.getDeclaredFields();
		 
		 for (Field field : declaredFields) {
	            if(field.isAnnotationPresent(Column.class)) {
	            	Column column = field.getAnnotation(Column.class);
	                res.put(column.name(), field.getType());
	            }
	        }
		
		return res;
	}

	public InfluxDB getInfluxDB() {
		return this.influxDB;
	}
	
}
