/**
 * 
 */
package indicators.movingAverage;

import java.util.ArrayList;
import java.util.List;

import data.source.internal.dataset.DatasetI;
import data.source.internal.dataset.DatasetImpl;
import data.source.internal.timeseries.TimeSeriesAbstract;
import data.source.internal.timeseries.TimeSeriesI;
import data.source.internal.timeseries.TimeSeriesIdAbstract;
import data.source.internal.timeseries.point.SingleTagPoint;
import data.source.internal.timeseries.point.TimeSeriesPointI;
import data.source.internal.timeseries.standard.TimeSeriesIdImpl;
import data.source.internal.timeseries.standard.TimeSeriesImpl;
import data.source.internal.timeseries.structure.RBTree;
import indicators.IndicatorAbstract;
import indicators.IndicatorI;
import indicators.movingAverage.ExponentialMovingAverage.Builder;

/**
 * @author stefanopenazzi
 *
 */
public class MACD implements IndicatorI {

	private TimeSeriesI itsRef;
	private int fastEMALength;
	private int slowEMALength;
	private String source;
	private int signalLineLength;
	
    public static class Builder {
		
		private TimeSeriesI ts;
		private int fastEMALength = 12;
		private int slowEMALength = 24;
		private String source = "close";
		private int signalLineLength = 9;
		
		public Builder(TimeSeriesI ts) {
	        this.ts = ts;
	    }
		public Builder signalLineLength(int signalLineLength ){
            this.signalLineLength  = signalLineLength ;
            return this;
        }
		public Builder source(String source){
           this.source = source; 
            return this;
        }
		public Builder fastEMALength(int fastEMALength){
            this.fastEMALength = fastEMALength;
            return this;
        }
		
		public Builder slowEMALength(int slowEMALength){
            this.slowEMALength = slowEMALength;
            return this;
        }
		
		 public MACD build(){
			MACD macd = new MACD(); 
			macd.itsRef = ts;
			macd.fastEMALength = this.fastEMALength;
			macd.slowEMALength = this.slowEMALength;
			macd.source = this.source;
			macd.signalLineLength = this.signalLineLength;
            return macd;
		}		
	}
	
	public DatasetImpl run() throws Exception { 
		
		//TODO bypassare il dataset come ?
		ExponentialMovingAverage fastEMA = new ExponentialMovingAverage.Builder(itsRef)
				.source(this.source)
				.length(this.fastEMALength)
				.build();
		
		ExponentialMovingAverage slowEMA = new ExponentialMovingAverage.Builder(itsRef)
				.source(this.source)
				.length(this.slowEMALength)
				.build();
		
		List<? extends TimeSeriesPointI> itsFastEMA =  fastEMA.run().getList();
		List<? extends TimeSeriesPointI> itsSlowEMA =  slowEMA.run().getList();
		
		//macd
		List<SingleTagPoint<Double>> macdRes = new ArrayList<>();
		for(int j=1;j <= itsSlowEMA.size();j++) {
			double diff = (Double)itsFastEMA.get(itsFastEMA.size()-j).getTagValue("value") - (Double)itsSlowEMA.get(itsSlowEMA.size()-j).getTagValue("value");
			macdRes.add(new SingleTagPoint<Double>(itsFastEMA.get(j).getTime(),diff));
		}
		 RBTree macdRBT = new RBTree(macdRes);
		 TimeSeriesImpl macd = new TimeSeriesImpl(macdRBT,new TimeSeriesIdImpl.Builder("macd")
				 .startInstant(macdRBT.getFirst().getTime())
				 .endInstant(macdRBT.getLast().getTime())
				 .build());
		 
		 //signal line
		 SimpleMovingAverage sma = new SimpleMovingAverage.Builder(macd)
				 .length(this.signalLineLength)
				 .source("value")
				 .build();
		 TimeSeriesImpl signalLine = sma.run();
		 List<TimeSeriesPointI> slRes = signalLine.getList();
		 
		 
		 //histogram macd
		 List<SingleTagPoint<Double>> histRes = new ArrayList<>();
		 for(int j= 1;j <= slRes.size();j++) {
				double diff = (Double)macdRes.get(macdRes.size()-j).getTagValue("value") - (Double)slRes.get(slRes.size()-j).getTagValue("value");
				histRes.add(new SingleTagPoint<Double>(slRes.get(slRes.size()-j).getTime(),diff));
		 }
		 
		 TimeSeriesImpl hist =  new TimeSeriesImpl(new RBTree(histRes),new TimeSeriesIdImpl.Builder("histogramMacd").build());
		 
		 DatasetImpl resDs = new DatasetImpl();
		 resDs.addTimeSeries(macd);
		 resDs.addTimeSeries(signalLine);
		 resDs.addTimeSeries(hist);
		 
		
	    return resDs;
	}

}
