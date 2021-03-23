/**
 * 
 */
package or.model;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

import data.source.internal.timeseries.TimeSeriesI;
import io.jenetics.Chromosome;
import io.jenetics.Genotype;
import io.jenetics.IntegerChromosome;
import io.jenetics.IntegerGene;
import io.jenetics.Phenotype;
import io.jenetics.engine.Constraint;
import strategies.SingleMovingAverageCrossoverStrategy;

/**
 * @author stefanopenazzi
 *
 */
public abstract class SMASJeneticModel<T extends SingleMovingAverageCrossoverStrategy> extends StrategyJeneticModelAbstract<T> {

	private TimeSeriesI ts;
	
	public SMASJeneticModel(Class<T> strategyC,TimeSeriesI ts) {
		super(strategyC);
		this.ts = ts;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Function<Genotype,Double> getFitnessFunction() {
		
		Function<Genotype,Double> res = gt -> {
			IntegerChromosome length = (IntegerChromosome)gt.get(0);
			T strategy = null;
			try {
				strategy = (T) this.strategyC.getConstructor(TimeSeriesI.class,String.class,Integer.TYPE,Integer.TYPE).newInstance(this.ts,"close",length.intValue(),0);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
			strategy.run();
			return fitnessFunctionDesign(strategy);
			
		};
		return res;
	}
	
	@SuppressWarnings("rawtypes")
	public abstract Boolean isValid(final Genotype gt);
	
	@SuppressWarnings("rawtypes")
	public abstract Genotype repairGenotype(final Genotype gt);
	
	public abstract double fitnessFunctionDesign(T smacs);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Genotype getEncoding() {
		return Genotype.of((Chromosome)IntegerChromosome.of(1, 150, 1));
	}
	
	@SuppressWarnings("unchecked")
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

		@Override
		public Phenotype repair(Phenotype pt, long generation) {
			return Phenotype.of(repairGenotype(pt.genotype()),generation);
		}
		
	}
}
