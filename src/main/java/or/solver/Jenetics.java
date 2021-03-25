/**
 * 
 */
package or.solver;

import or.model.JeneticsModelI;
import io.jenetics.Phenotype;
import io.jenetics.RouletteWheelSelector;
import io.jenetics.TournamentSelector;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.stat.DoubleMomentStatistics;

/**
 * @author stefanopenazzi
 *
 */
public final class Jenetics <T extends JeneticsModelI> implements SolverI<T> {
	
	private final T modelC;
	
	public Jenetics(T modelC) {
		this.modelC = modelC;
	}

	@Override
	public T getModel() {
		return this.modelC;
	}

	@Override
	public void run() {
		
        @SuppressWarnings({ "unchecked", "rawtypes" })
		Engine engine = Engine
            .builder(this.modelC.getFitnessFunction(),this.modelC.getEncoding())
            .constraint(this.modelC.getConstraint())
            .populationSize(200)
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
    			.limit(6000)
    			.peek(statistics)
    			.collect(EvolutionResult.toBestPhenotype());
        System.out.println("Best phenotype: ");
        System.out.println( best);
        System.out.println(statistics);
	}

}
