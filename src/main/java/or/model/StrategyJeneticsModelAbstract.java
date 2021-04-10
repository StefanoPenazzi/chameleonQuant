/**
 * 
 */
package or.model;

import java.util.Arrays;
import java.util.List;
import data.source.internal.timeseries.TimeSeriesI;
import io.jenetics.Genotype;
import io.jenetics.Phenotype;
import io.jenetics.engine.Constraint;

import strategies.StrategyI;

/**
 * @author stefanopenazzi
 *
 */
public abstract class StrategyJeneticsModelAbstract implements JeneticsModelI {
    
	protected final Class<? extends StrategyI> strategyC;
	protected final List<TimeSeriesI> tsl; 
	
	public StrategyJeneticsModelAbstract(Class<? extends StrategyI> strategyC, TimeSeriesI... tsl) {
		this.strategyC = strategyC;
		this.tsl = Arrays.asList(tsl);
	}
	
	@SuppressWarnings("rawtypes")
	public abstract Boolean isValid(final Genotype gt);
	
	@SuppressWarnings("rawtypes")
	public abstract Genotype repairGenotype(final Genotype gt);
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Constraint getConstraint(){
		return new RepairingConstraint();
	}
	
	@SuppressWarnings("rawtypes")
	private class RepairingConstraint implements Constraint{

		@Override
		public boolean test(Phenotype pt) {
			return isValid(pt.genotype());
		}

		@SuppressWarnings("unchecked")
		@Override
		public Phenotype repair(Phenotype pt, long generation) {
			return Phenotype.of(repairGenotype(pt.genotype()),generation);
		}
		
	}

}
