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

	
	/**
	 * @param series
	 * @param database
	 * @param idb
	 * @param maxReqPerMin
	 * @param maxReqPerDay
	 * @param nThreads
	 */
	public synchronized void updateMultiThreadingStopwatch_m_d(List<String> series, String database,Influxdb idb,int maxReqPerMin, int maxReqPerDay, int nThreads) {
		
		beforeUpdate(series,database,maxReqPerMin,maxReqPerDay,nThreads);
		ExecutorService service = Executors.newFixedThreadPool(nThreads);
		//daily sublist in case the tickersSet contains more tickers than the maxReqPerDay
		List<List<String>> dailyTickersSet  = Lists.partition(series, maxReqPerDay);
		int totDays = dailyTickersSet.size();
		int countDays = 1;
		for(List<String> ltd: dailyTickersSet) {
			List<List<String>> minTickersSet  = Lists.partition(ltd, maxReqPerMin);
			int minTickersSetSize = minTickersSet.size();
			for(List<String> ltm: minTickersSet) {
				minTickersSetSize--;
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
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
				    }
				}
				try {
					if(minTickersSetSize>0) {
						Thread.sleep(60000);
					}
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
	
	public synchronized void updateSingleThread(String serie, String database, Influxdb idb) {
		ExecutorService service = Executors.newSingleThreadExecutor();
		Future<Boolean> future = service.submit(getWorker(serie,database,idb));
		while(true) {
			if(future.isDone()) {
	    		break;
	    	}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		service.shutdown();
		afterUpdate();
	}
	
	public synchronized void run(List<String> series, String database) {
		try(Influxdb idb = new Influxdb()){
			runUpdate(series,database,idb);
		} 
	}
	
	public synchronized void run(String serie, String database) {
		try(Influxdb idb = new Influxdb()){
			List<String> series = new ArrayList<>();
			series.add(serie);
			runUpdate(series,database,idb);
		} 
	}
	
	public abstract Callable<Boolean> getWorker(String serie, String database,Influxdb idb);
	public abstract void beforeUpdate(List<String> series, String database ,int maxReqPerMin, int maxReqPerDay, int nThreads);
	public abstract void afterUpdate(Object ...objects);
	public abstract void runUpdate(List<String> series, String database,Influxdb idb );
		
	
}
