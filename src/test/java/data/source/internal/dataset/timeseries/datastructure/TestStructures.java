/**
 * 
 */
package data.source.internal.dataset.timeseries.datastructure;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

import org.influxdb.annotation.Measurement;
import org.junit.jupiter.api.Test;

import data.source.external.database.influxdb.Influxdb;
import data.source.external.database.influxdb.mirrors.alphaVantage.StockTimeSeriesPoint;

/**
 * @author stefanopenazzi
 *
 */
class TestStructures {

	@Test
	void testRBTree() {
        String stock = "AAPL";
		
		Influxdb idb = new Influxdb();
		final String serverURL = "http://127.0.0.1:7086", username = "stefanopenazzi", password = "korky1987";
		String[] dbCon = {serverURL,username,password};
		//the server must be on(service influxdb start) otherwise the connection will not be successful
		idb.connect(dbCon);
		//the StockTimeSeries class has an annotation refering the name of the measure in the db.
		//this has to be changed accordingly with the query
		
		 
		    
		     // Get the annotation on Test
		    Measurement stss =  StockTimeSeriesPoint.class.getAnnotation(Measurement.class);
		    if (stss == null) {
		      throw new RuntimeException("please add @Measurement for StockTimeSeries");
		    }
		     // Get the proxy processor
		    InvocationHandler invocationHandler = Proxy.getInvocationHandler(stss);
		     // Get the private memberValues ​​property
		    Field f = null;
			try {
				f = invocationHandler.getClass().getDeclaredField("memberValues");
			} catch (NoSuchFieldException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    f.setAccessible(true);
		     // Get the attribute map of the instance
		    Map<String, Object> memberValues = null;
			try {
				memberValues = (Map<String, Object>) f.get(invocationHandler);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     // Modify the attribute value
		    memberValues.put("name", stock);
		    
		    
		
		List<StockTimeSeriesPoint> sts = (List<StockTimeSeriesPoint>) idb.performQuery("SELECT first(open) AS open, last(close) AS close, max(high) AS high, min(low) AS low, sum(volume) AS volume FROM "+stock+" WHERE time>'2020-10-19 09:30:00'  GROUP BY time(8h)", "US_STOCKS_TIME_SERIES_INTRADAY_1MIN", StockTimeSeriesPoint.class );
		
		RBTree rbt = new RBTree(sts);
		System.out.println();
		idb.close();
	}

}
