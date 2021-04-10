/**
 * 
 */
package or.model;

import java.util.function.Function;

import io.jenetics.Genotype;
import io.jenetics.engine.Constraint;

/**
 * @author stefanopenazzi
 *
 */
public interface JeneticsModelI extends GeneticModelI {
	
	@SuppressWarnings("rawtypes")
	public Function<Genotype,Double> getFitnessFunction();
	
	@SuppressWarnings("rawtypes")
	public Genotype getEncoding();
	
	public Constraint <?,?>  getConstraint();

}
