/**
 * 
 */
package data.source.external.database.influxdb.mirrors.alphaVantage;

import java.time.Instant;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import data.source.external.database.Mirror;

/**
 * @author stefanopenazzi
 *
 */

@Measurement(name = "IBM")
public class StockTimeSeries implements Mirror {

	    @Column(name = "time")
	    private Instant time;
	 
	    @Column(name = "open")
	    private Double open;
	    
	    @Column(name = "close")
	    private Double close;
	 
	    @Column(name = "high")
	    private Double high;
	 
	    @Column(name = "low")
	    private Double low;
	 
	    @Column(name = "volume")
	    private Double volume;
	   
}
