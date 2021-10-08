/**
 * 
 */
package data.source.external.database.influxdb.utils.update;

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
	
	
	
	public UpdateAbstract() {
		
	}
	
	public synchronized void updateMultiThreadingStopwatch_m_d(List<String> series, String database,Influxdb idb,int maxReqPerMin, int maxReqPerDay, int nThreads) {
		
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
					Future<Boolean> future = service.submit(getWorker(tic,database,idb));
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
		try(Influxdb idb = new Influxdb()){
			idb.connect();
			runUpdate(series,database,idb);
		} 
	}
	
	public abstract Callable<Boolean> getWorker(String serie, String database,Influxdb idb);
	public abstract void beforeUpdate(List<String> series, String database ,int maxReqPerMin, int maxReqPerDay, int nThreads);
	public abstract void afterUpdate(Object ...objects);
	public abstract void runUpdate(List<String> series, String database,Influxdb idb );
	
	
}
