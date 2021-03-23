/**
 * 
 */
package or.solver;

import or.model.SMASJeneticModel;
import or.model.StrategyJeneticModelAbstract;
import java.util.function.Function;

import io.jenetics.Chromosome;
import io.jenetics.Genotype;
import io.jenetics.IntegerChromosome;
import io.jenetics.IntegerGene;
import io.jenetics.Phenotype;
import io.jenetics.RouletteWheelSelector;
import io.jenetics.TournamentSelector;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.engine.Problem;
import io.jenetics.stat.DoubleMomentStatistics;
import io.jenetics.util.ISeq;
import io.jenetics.util.IntRange;

/**
 * @author stefanopenazzi
 *
 */
public final class Jenetics <T extends StrategyJeneticModelAbstract> implements SolverI<T> {
	
	private final T modelC;
	
	public Jenetics(T modelC) {
		this.modelC = modelC;
		
	}

	@Override
	public T getModel() {
		return this.modelC;
	}
	
	@SuppressWarnings("unchecked")
	private static final Genotype<?> ENCODING = Genotype.of(IntegerChromosome.of(0, 100, 2));

	@Override
	public void run() {
		
        @SuppressWarnings({ "unchecked", "rawtypes" })
		Engine engine = Engine
            .builder(this.modelC.getFitnessFunction(),ENCODING)
            .constraint(this.modelC.getConstraint())
            .populationSize(30)
            .offspringFraction(0.7)
            .survivorsSelector(new RouletteWheelSelector<>())
			.offspringSelector(new TournamentSelector<>())
            .build();
        
        final EvolutionStatistics<Double, DoubleMomentStatistics> statistics =
        	     EvolutionStatistics.ofNumber();
 
        // Start the execution (evolution) and
        //     collect the result.
        @SuppressWarnings({ "unchecked", "rawtypes" })
		final Phenotype best = (Phenotype)engine.stream()
    			.limit(60)
    			.peek(statistics)
    			.collect(EvolutionResult.toBestPhenotype());
        System.out.println("Best phenotype: ");
        System.out.println( best);
        System.out.println(statistics);
	}

}
