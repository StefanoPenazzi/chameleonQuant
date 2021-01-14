/**
 * 
 */
package data.source.external.database.influxdb.utils.queries;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

/**
 * @author stefanopenazzi
 *
 */
@Measurement(name = "databases")
public class ShowDatabases {
		
		@Column(name = "name")
	    private String name ;
		
		public String getName() {
			return this.name;
		}
		
	}
