package strategies.hmm;

import strategies.SingleExpMovingAverageStrategy;
import strategies.SingleMovingAverageCrossoverStrategy;
import strategies.StrategyAbstract;
import strategies.SingleExpMovingAverageStrategy.Builder;
import strategies.positionsizing.FixedMoneyAmount;
import strategies.positionsizing.PositionSizingI;

import java.util.ArrayList;
import java.util.List;

import org.tensorflow.ConcreteFunction;
import org.tensorflow.Signature;
import org.tensorflow.Tensor;
import org.tensorflow.TensorFlow;
import org.tensorflow.ndarray.Shape;
import org.tensorflow.op.Ops;
import org.tensorflow.op.core.Placeholder;
import org.tensorflow.op.math.Add;
import org.tensorflow.types.TInt32;


import data.source.internal.timeseries.TimeSeriesI;

import org.tensorflow.types.TFloat64;

public class Nguyen2018 extends StrategyAbstract {
	
	private TimeSeriesI ts;
	private String source;
	private int nStates;
	private PositionSizingI ps;

	public static final class Builder {
		    private TimeSeriesI ts;
			private int nStates = 4;
			private String source = "close";
			private String emissionProbDist = "normal";
			private int trainingSetPeriods = 1;
			//private List<String> outOfSampleIndicators = new ArrayList<String>();
			
			private PositionSizingI ps = new FixedMoneyAmount.Builder()
					.fixedMoneyAmount(10000)
					.build();
			
			public Builder(TimeSeriesI ts) {
		        this.ts = ts;
		    }
			public Builder nStates(int nStates){
	            this.nStates = nStates;
	            return this;
	        }
			public Builder source(String source){
	           this.source = source; 
	            return this;
	        }
			public Builder positionSizing(PositionSizingI ps){
	            this.ps = ps;
	            return this;
	        }
			 public Nguyen2018 build(){
				 Nguyen2018  strategy = new Nguyen2018 (this.ts,this.source,this.nStates,this.ps); 
	            return strategy;
			}		
	  }
	
	public Nguyen2018(TimeSeriesI ts,String source,int nStates, PositionSizingI ps) {
		super(ps);
		this.ts = ts;
		this.source = source;
		this.nStates =nStates;
		this.ps = ps;
		
	}

	@Override
	public void run() {
		
		//1) training using the last trainingSetPeriods
		
		
		
		/*
		 * System.out.println("Hello TensorFlow " + TensorFlow.version());
		 * 
		 * try (ConcreteFunction dbl = ConcreteFunction.create(Nguyen2018::dbl); TInt32
		 * x = TInt32.scalarOf(10); Tensor dblX = dbl.call(x)) {
		 * System.out.println(x.getInt() + " doubled is " + ((TInt32)dblX).getInt()); }
		 */
		
	}
	
	/*
	 * private static Signature dbl(Ops tf) { Placeholder<TInt32> x =
	 * tf.placeholder(TInt32.class); Add<TInt32> dblX = tf.math.add(x, x); return
	 * Signature.builder().input("x", x).output("dbl", dblX).build(); }
	 */

	@Override
	public String getStrategyName() {
		// TODO Auto-generated method stub
		return null;
	}

}
