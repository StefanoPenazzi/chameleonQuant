/**
 * 
 */
package data.source.external.database.influxdb;

import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import java.util.Properties;
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

import data.source.external.database.DatabaseAbstract;
import data.source.external.database.DatabaseI;
import data.source.internal.timeseries.point.TimeSeriesPointI;
import data.source.utils.IO.CSVUtils;


/**
 * @author stefanopenazzi
 *
 */
public class Influxdb extends DatabaseAbstract {

	private final static Logger log = LogManager.getLogger(Influxdb.class);
	private InfluxDB influxDB = null; 
	final String serverURL;
	final String username;
	final String password;
	
	public Influxdb() {
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
	
	@Override
	public void connect() {
		influxDB = InfluxDBFactory.connect(serverURL, username, password);
	}

	@Override
	public boolean ping() {
		//verify the connection
		Pong response = this.influxDB.ping();
		if (response.getVersion().equalsIgnoreCase("unknown")) {
		    log.error("Error pinging server.");
		    System.out.println("CONNECTION FAILED");
		    return false;
		} 
		else {
			System.out.println("SUCCESSFUL CONNECTION");
		}
		return true;
	}

	
	@Override
	public void update( String database,  String measurement , Class<? extends TimeSeriesPointI> mirror ,List<Map<String,String>> csvMap) {
		boolean pingCheck = ping();
		if (!pingCheck) {
			//run exception
		}
		String timeDaily = "time";
		Map<String,Class<?>> mirrorMapType =  getMapTypes(mirror);
		DateTimeFormatter formatter = null;
		try {
			formatter = mirror.newInstance().getTimeFormat();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		BatchPoints batchPoints = BatchPoints
				  .database(database)
				  .build();
		for(Map<String, String> m: csvMap) {
			Number date = null;
			//TODO this is not correct!! UTC is not correct for US data etc
		    LocalDateTime dateTime = LocalDateTime.parse(m.get(timeDaily), formatter);
			OffsetDateTime utcDateTime = dateTime.atOffset(ZoneOffset.UTC);
			date = utcDateTime.toInstant().toEpochMilli();	
			Builder bui = Point.measurement(measurement)
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
	}

	@Override
	public void close() {
		influxDB.close();
	}

	@Override
	public List<? extends TimeSeriesPointI> select(String query,String database,Class<? extends TimeSeriesPointI> cl) {
		QueryResult queryResult = influxDB.query(new Query(query,database));
		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		List<? extends TimeSeriesPointI> pointList = resultMapper.toPOJO(queryResult, cl);
		return pointList;
	}
	
	public InfluxDB getInfluxDB() {
		return this.influxDB;
	}

	
	
}
