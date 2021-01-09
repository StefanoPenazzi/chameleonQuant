/**
 * 
 */
package data.source.external.database;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.influxdb.annotation.Column;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Point.Builder;

import data.source.external.database.influxdb.Influxdb;
import data.source.internal.timeseries.point.TimeSeriesPointI;
import data.source.utils.IO.CSVUtils;

/**
 * @author stefanopenazzi
 *
 */
public abstract class DatabaseAbstract implements DatabaseI {
	
	private final static Logger log = LogManager.getLogger(Influxdb.class);
	
	
	protected Map<String,Class<?>> getMapTypes( Class<? extends TimeSeriesPointI> mirror){
		
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

}
