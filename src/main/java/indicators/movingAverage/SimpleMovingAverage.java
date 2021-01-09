/**
 * 
 */
package indicators.movingAverage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.DoubleStream;

import data.source.annotations.InternalQueryAnnotation.InternalQueryInfo;
import data.source.annotations.TimeSeriesAnnotations.TagName;
import data.source.external.database.influxdb.TimeSeriesId;
import data.source.internal.dataset.DatasetI;
import data.source.internal.dataset.DatasetImpl;
import data.source.internal.timeseries.TimeSeriesAbstract;
import data.source.internal.timeseries.TimeSeriesIdAbstract;
import data.source.internal.timeseries.point.SingleTagPoint;
import data.source.internal.timeseries.point.TimeSeriesPointI;
import data.source.internal.timeseries.standard.TimeSeriesIdImpl;
import data.source.internal.timeseries.standard.TimeSeriesImpl;
import data.source.internal.timeseries.structure.RBTree;
import data.source.utils.IO.ReflectionsUtils;
import indicators.IndicatorAbstract;

/**
 * @author stefanopenazzi
 *
 */
public class SimpleMovingAverage<T extends TimeSeriesPointI> extends IndicatorAbstract {

	private final TimeSeriesImpl<T> itsRef;
	private TimeSeriesImpl <SingleTagPoint<Double>> itsRes;
	private final int periods;
	private final String tagName;
	
	/**
	 * @param dataSet
	 */
	public SimpleMovingAverage(DatasetI dataSet,String tagName,int periods) {
		super(dataSet);
		itsRef = (TimeSeriesImpl<T>) this.dataSet.iterator().next();
		this.periods= periods;
		this.tagName = tagName;
	}
	
	public SimpleMovingAverage(DatasetI dataSet,TimeSeriesIdAbstract id,String tagName,int periods) {
		super(dataSet);
		itsRef = (TimeSeriesImpl<T>) this.dataSet.getTimeSeries(id);
		this.periods= periods;
		this.tagName = tagName;
	}
	
	public DatasetImpl create() throws Exception {
	   List<TimeSeriesPointI> itsRefList = (List<TimeSeriesPointI>) itsRef.getList();
	   List<SingleTagPoint<Double>> res = new ArrayList<>(); 
	   if(itsRefList.size() < periods) {}  // run exception
	   int firstRemoveIndex = 0;
//	   // reflection invoked just once
       Method method = itsRef.getTagMethod(tagName);
	   double count = itsRefList.stream().limit(periods).mapToDouble(point -> {
           try { 
        	   Double result = (Double) method.invoke(point);
               return result != null ? result : 0;
           } catch (IllegalAccessException | InvocationTargetException e) {
        	   return 0;
           }
       }).sum();
	   res.add(new SingleTagPoint<Double>(itsRefList.get(periods-1).getTime(),count/periods));
	   for(int i = periods;i<itsRefList.size();i++) {
	        count = count -  (Double)method.invoke(itsRefList.get(firstRemoveIndex)) + (Double)method.invoke(itsRefList.get(i));
	        res.add(new SingleTagPoint<Double>(itsRefList.get(i).getTime(),count/periods));
	        firstRemoveIndex++;
	   }
	   TimeSeriesIdImpl id = new TimeSeriesIdImpl(itsRef.getFirstInstant(),itsRef.getLastInstant(),"MA","",SingleTagPoint.class);
	   itsRes = new TimeSeriesImpl(new RBTree(res),id);
	   DatasetImpl ds = new DatasetImpl();
	   ds.addTimeSeries(itsRes);
	   return ds;
	}

	@Override
	public boolean dataSetCheck() {
		// TODO Auto-generated method stub
		return false;
	}

}
