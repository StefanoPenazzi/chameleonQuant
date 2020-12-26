/**
 * 
 */
package indicators.volatility;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import data.source.internal.dataset.core.DatasetI;
import data.source.internal.dataset.core.DatasetImpl;
import data.source.internal.dataset.timeseries.InternalTimeSeriesIdAbstract;
import data.source.internal.dataset.timeseries.datastructure.RBTree;
import data.source.internal.dataset.timeseries.point.InternalSingleTagTimeSeriesPoint;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPointI;
import data.source.internal.dataset.timeseries.standard.InternalTimeSeriesIdImpl;
import data.source.internal.dataset.timeseries.standard.InternalTimeSeriesImpl;
import indicators.IndicatorAbstract;

/**
 * @author stefanopenazzi
 *
 */
public class TrueRange <T extends InternalTimeSeriesPointI> extends IndicatorAbstract {

	private final InternalTimeSeriesImpl<T> itsRef;
	private InternalTimeSeriesImpl <InternalSingleTagTimeSeriesPoint<Double>> itsRes;
	
	/**
	 * @param dataSet
	 */
	public TrueRange(DatasetI dataSet,InternalTimeSeriesIdAbstract id) {
		super(dataSet);
		this.itsRef = (InternalTimeSeriesImpl<T>) this.dataSet.getTimeSeries(id);
		
	}
	
	public TrueRange(DatasetI dataSet) {
		super(dataSet);
		itsRef = (InternalTimeSeriesImpl<T>) this.dataSet.iterator().next();
	}

	@Override
	public boolean dataSetCheck() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public DatasetImpl create() throws Exception {
		   List<InternalTimeSeriesPointI> itsRefList = (List<InternalTimeSeriesPointI>) itsRef.getList();
		   List<InternalSingleTagTimeSeriesPoint<Double>> res = new ArrayList<>(); 
//		   // reflection invoked just once
	       Method methodHigh = itsRef.getTagMethod("high");
	       Method methodLow = itsRef.getTagMethod("low");
	       Method methodClose = itsRef.getTagMethod("close");
		   
	       for(int i = 1; i<itsRefList.size();i++) {
	    	   
	    	   Double resultHigh = 0.;
        	   Double resultLow = 0.;
        	   Double resultClose = 0.;
        	   InternalTimeSeriesPointI point = itsRefList.get(i);
        	   InternalTimeSeriesPointI prevPoint = itsRefList.get(i-1);
        	   
	    	   try { 
	        	   resultHigh = (Double) methodHigh.invoke(point);
	        	   resultLow = (Double) methodLow.invoke(point);
	        	   resultClose = (Double) methodClose.invoke(prevPoint);
	           } catch (IllegalAccessException | InvocationTargetException e) {
	        	   return null;
	           }
	    	   res.add(new InternalSingleTagTimeSeriesPoint<Double>(point.getTime(),Math.max(Math.abs(resultLow-resultClose),Math.max((resultHigh-resultLow), Math.abs(resultHigh-resultClose)))));
	       }
	       
		   InternalTimeSeriesIdImpl id = new InternalTimeSeriesIdImpl(itsRef.getFirstInstant(),itsRef.getLastInstant(),"TR","");
		   itsRes = new InternalTimeSeriesImpl(new RBTree(res),id);
		   DatasetImpl ds = new DatasetImpl();
		   ds.addTimeSeries(itsRes);
		   return ds;
		}

}
