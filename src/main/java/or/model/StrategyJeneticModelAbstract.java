/**
 * 
 */
package or.model;

import java.util.function.Function;

import io.jenetics.Genotype;
import io.jenetics.engine.Constraint;
import strategies.StrategyI;

/**
 * @author stefanopenazzi
 *
 */
public abstract class StrategyJeneticModelAbstract <T extends StrategyI> implements ModelI {
    
	protected final Class<? extends StrategyI> strategyC;
	
	public StrategyJeneticModelAbstract(Class<? extends StrategyI> strategyC) {
		this.strategyC = strategyC;
	}
	
	@SuppressWarnings("rawtypes")
	public abstract Function<Genotype,Double> getFitnessFunction();
	
	@SuppressWarnings("rawtypes")
	public abstract Genotype getEncoding();
	
	public abstract Constraint <?,?>  getConstraint();

}
