/**
 * 
 */
package data.source.external.database.influxdb.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import data.source.external.database.influxdb.Influxdb;



/**
 * @author stefanopenazzi
 *
 */
public abstract class UpdateAbstract implements UpdateI {
	
	private final Influxdb idb;
	String serverURL = "http://127.0.0.1:7086", username = "stefanopenazzi", password = "korky1987";
	private final String[] dbCon = {serverURL,username,password};
	
	public UpdateAbstract() {
		idb = new Influxdb();
	}
	
	protected Influxdb getInfluxdb() {
		return this.idb;
	}
	
	public synchronized void updateMultiThreadingStopwatch_m_d(List<String> series, String database ,int maxReqPerMin, int maxReqPerDay, int nThreads) {
		
		beforeUpdate(series,database,maxReqPerMin,maxReqPerDay,nThreads);
		ExecutorService service = Executors.newFixedThreadPool(nThreads);
		//daily sublist in case the tickersSet contains more tickers than the maxReqPerDay
		List<List<String>> dailyTickersSet  = Lists.partition(series, maxReqPerDay);
		int totDays = dailyTickersSet.size();
		int countDays = 1;
		for(List<String> ltd: dailyTickersSet) {
			List<List<String>> minTickersSet  = Lists.partition(ltd, maxReqPerMin);
			for(List<String> ltm: minTickersSet) {
				List<Future<Boolean>> futureResultList = new ArrayList<>();
				for(String tic: ltm ) {
					Future<Boolean> future = service.submit(getWorker(tic,database));
					futureResultList.add(future);
				}
				boolean done = false;
				while(!done) {
					done = true;
				    for(Future<Boolean> f: futureResultList) {
				    	if(!f.isDone()) {
				    		done = false;
				    		break;
				    	}
				    }
				    if(!done) {
				    	try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
				    }
				}
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			//if the list is not the last wait
			if(countDays<totDays) {
				try {
					Thread.sleep(86400000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			countDays++;
		}
		service.shutdown();
		afterUpdate();
		
	}
	
	public synchronized void run(List<String> series, String database) {
		idb.connect(dbCon);
		runUpdate(series,database);
		idb.close();
	}
	
	public abstract Callable<Boolean> getWorker(String serie, String database);
	public abstract void beforeUpdate(List<String> series, String database ,int maxReqPerMin, int maxReqPerDay, int nThreads);
	public abstract void afterUpdate(Object ...objects);
	public abstract void runUpdate(List<String> series, String database);
	
	
}
