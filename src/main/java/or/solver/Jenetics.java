/**
 * 
 */
package or.solver;

import or.model.JeneticsModelI;
import java.util.concurrent.Executors;
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
	static Phenotype best = null;
	
	public Jenetics(T modelC) {
		this.modelC = modelC;
	}

	@Override
	public T getModel() {
		return this.modelC;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		
        @SuppressWarnings({ "unchecked", "rawtypes" })
		Engine engine = Engine
            .builder(this.modelC.getFitnessFunction(),this.modelC.getConstraint().constrain(this.modelC.getEncoding()))
            .constraint(this.modelC.getConstraint())
            .populationSize(200)
            .offspringFraction(0.7)
            .survivorsSelector(new RouletteWheelSelector<>())
			.offspringSelector(new TournamentSelector<>())
			.executor(Executors.newFixedThreadPool(10))
            .build();
        
		
		final EvolutionStatistics<Double, DoubleMomentStatistics> statistics =
		EvolutionStatistics.ofNumber();
		 
 
        // Start the execution (evolution) and
        //     collect the result.
        
		engine.stream()
    			.limit(300)
    			.peek(statistics)
    			.peek(res -> {
    				EvolutionResult result = (EvolutionResult)res; 
    				if(best == null ||
    					 best.compareTo(result.bestPhenotype()) < 0 )
    					 {
    					 best = result.bestPhenotype();
    					 System.out.print(result.generation()+ " : " ) ;
    					 System.out.println( "Found best phenotype : " + best) ;
    				 }
    			})
    			.collect(EvolutionResult.toBestGenotype());
    			
        System.out.println("Best phenotype: ");
        System.out.println(statistics);
	}

}
