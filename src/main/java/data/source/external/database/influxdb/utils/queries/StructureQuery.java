/**
 * 
 */
package data.source.external.database.influxdb.utils.queries;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;

import data.source.external.database.influxdb.Influxdb;
import data.source.internal.timeseries.point.TimeSeriesPointI;

/**
 * @author stefanopenazzi
 *
 */
public class StructureQuery {
	
	
	public static Map<String,String> getDatabaseMap(List<String> databases){
		Map<String,String> result = new TreeMap<String,String>();
		Influxdb idb = new Influxdb();
		idb.connect();
		for(String database: databases) {
			Query query = new Query("SHOW MEASUREMENTS",database);
			QueryResult queryResult = idb.getInfluxDB().query(query);
			InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
			List<ShowMeasurements> measurementsList = resultMapper.toPOJO(queryResult,ShowMeasurements.class );
			for(ShowMeasurements sm: measurementsList) {
				result.put(sm.getName(),database);
			}
            System.out.println();
		}
		idb.close();
		return result;
	}
	
	
	//TODO it doesn't work ??????
	public static Map<String,String> getDatabaseMap(){
		List<String> result = new ArrayList<String>();
		Influxdb idb = new Influxdb();
		Query query = new Query("SHOW DATABASES");
		QueryResult queryResult = idb.getInfluxDB().query(query);
		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		List<ShowDatabases> databasesList = resultMapper.toPOJO(queryResult,ShowDatabases.class );
		for(ShowDatabases sm: databasesList) {
			result.add(sm.getName());
		}
		idb.close();
		return getDatabaseMap(result);
	}
}
