/**
 * 
 */
package data.source.external.database.influxdb.utils.update;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import data.source.external.database.influxdb.Influxdb;
import data.source.external.financialdatavendors.alphavantage.AlphaVantageConnector;


/**
 * @author stefanopenazzi
 *
 */
public abstract class UpdateFromAlphaVantageAbstract extends UpdateAbstract {
	
	private static final Logger log = LogManager.getLogger(UpdateFromAlphaVantageAbstract.class);
	private final AlphaVantageConnector avc;
	private final int maxReqPerMin;
	private final int maxReqPerDay;
	private final int nThreads;
	private final boolean stopwatch;
	
	public UpdateFromAlphaVantageAbstract(int maxReqPerMin, int maxReqPerDay, int nThreads) {
		this.maxReqPerMin = maxReqPerMin;
		this.maxReqPerDay = maxReqPerDay;
		this.nThreads = nThreads;
		this.avc = new AlphaVantageConnector(60000);
		this.stopwatch = true;
	}
	
	public UpdateFromAlphaVantageAbstract() {
		this.maxReqPerMin = 0;
		this.maxReqPerDay = 0;
		this.nThreads = 0;
		this.avc = new AlphaVantageConnector(60000);
		this.stopwatch = false;
	}
	
	public AlphaVantageConnector getAlphaVantageConnector() {
		return this.avc;
	}

	@Override
	public void afterUpdate(Object... objects) {
		// TODO Auto-generated method stub
	}

	@Override
	public void beforeUpdate(List<String> series, String database, int maxReqPerMin, int maxReqPerDay, int nThreads) {
		// TODO Auto-generated method stub
	}

	@Override
	public void runUpdate(final List<String> series,final String database, final Influxdb idb) {
		if(this.stopwatch) {
			this.updateMultiThreadingStopwatch_m_d(series,database,idb,this.maxReqPerMin,this.maxReqPerDay,this.nThreads);
		}
		else {
			this.updateSingleThread(series.get(0),database,idb);
		}
	}
}
	

