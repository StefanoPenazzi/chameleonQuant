/**
 * 
 */
package or.model;

import java.util.ArrayList;
import java.util.List;

import strategies.StrategiesAnnotations.StrategyVariable;
import strategies.StrategyI;

/**
 * @author stefanopenazzi
 *
 */
public class StrategyModel implements ModelI  {
	
    private StrategyI strategy;
	
	@Override
	public List<Double> getObjectiveFunctionValues(Object... objects){
		return null;
	}	
		
	public static final class Builder {
		 
		private  List<String> stringConstraints =  new ArrayList<String>();
		private StrategyI strategy;
		
		public Builder(StrategyI strategy) {
	        this.strategy = strategy;
	    }
		
		public Builder addConstraint(StrategyVariable sv) {
	        this.stringConstraints.add(sv.name());
	        return this;
	    }
		
		 public StrategyModel build(){
			 
			 StrategyModel strategyModel = new StrategyModel();
			 strategyModel.strategy = this.strategy;
			 
			 return strategyModel;
		}		
	  }

}
