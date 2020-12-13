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

import data.source.annotation.InternalQueryAnnotation.InternalQueryInfo;
import data.source.annotation.InternalTimeSeries.TagName;
import data.source.external.database.influxdb.InternalStockTimeSeriesQueryInfluxdb;
import data.source.internal.dataset.core.DatasetI;
import data.source.internal.dataset.core.DatasetImpl;
import data.source.internal.dataset.timeseries.InternalTimeSeriesAbstract;
import data.source.internal.dataset.timeseries.InternalTimeSeriesIdAbstract;
import data.source.internal.dataset.timeseries.datastructure.RBTree;
import data.source.internal.dataset.timeseries.point.InternalSingleTagTimeSeriesPoint;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPointI;
import data.source.internal.dataset.timeseries.standard.InternalTimeSeriesIdImpl;
import data.source.internal.dataset.timeseries.standard.InternalTimeSeriesImpl;
import data.source.utils.IO.ReflectionsUtils;
import indicators.IndicatorAbstract;

/**
 * @author stefanopenazzi
 *
 */
public class SimpleMovingAverage<T extends InternalTimeSeriesPointI> extends IndicatorAbstract {

	private final InternalTimeSeriesImpl<T> itsRef;
	private InternalTimeSeriesImpl <InternalSingleTagTimeSeriesPoint<Double>> itsRes;
	private final int periods;
	private final String tagName;
	
	/**
	 * @param dataSet
	 */
	public SimpleMovingAverage(DatasetI dataSet,String tagName,int periods) {
		super(dataSet);
		itsRef = (InternalTimeSeriesImpl<T>) this.dataSet.iterator().next();
		this.periods= periods;
		this.tagName = tagName;
	}
	
	public SimpleMovingAverage(DatasetI dataSet,InternalTimeSeriesIdAbstract id,String tagName,int periods) {
		super(dataSet);
		itsRef = (InternalTimeSeriesImpl<T>) this.dataSet.getTimeSeries(id);
		this.periods= periods;
		this.tagName = tagName;
	}
	
	public DatasetImpl create() throws Exception {
	   List<InternalTimeSeriesPointI> itsRefList = (List<InternalTimeSeriesPointI>) itsRef.getList();
	   List<InternalSingleTagTimeSeriesPoint<Double>> res = new ArrayList<>(); 
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
	   res.add(new InternalSingleTagTimeSeriesPoint<Double>(itsRefList.get(periods-1).getTime(),count/periods));
	   for(int i = periods;i<itsRefList.size();i++) {
	        count = count -  (Double)method.invoke(itsRefList.get(firstRemoveIndex)) + (Double)method.invoke(itsRefList.get(i));
	        res.add(new InternalSingleTagTimeSeriesPoint<Double>(itsRefList.get(i).getTime(),count/periods));
	        firstRemoveIndex++;
	   }
	   InternalTimeSeriesIdImpl id = new InternalTimeSeriesIdImpl(itsRef.getFirstDate(),itsRef.getLastDate(),"MA","");
	   itsRes = new InternalTimeSeriesImpl(new RBTree(res),id);
	   DatasetImpl ds = new DatasetImpl();
	   ds.addTimeSeries(itsRes);
	   return ds;
//	   System.out.println(itsRef.getString());
//	   System.out.println();
//	   System.out.println(itsRes.getString());
	   
	}

	@Override
	public boolean dataSetCheck() {
		// TODO Auto-generated method stub
		return false;
	}

}
