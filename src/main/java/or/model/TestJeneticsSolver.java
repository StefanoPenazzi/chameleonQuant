/**
 * 
 */
package or.model;

/**
 * @author stefanopenazzi
 *
 */

import io.jenetics.BitChromosome;
import io.jenetics.BitGene;
import io.jenetics.Genotype;
import io.jenetics.RouletteWheelSelector;
import io.jenetics.TournamentSelector;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.util.Factory;

public class TestJeneticsSolver {

	// 2.) Definition of the fitness function.
    private static Integer eval(Genotype<BitGene> gt) {
        return gt.chromosome()
            .as(BitChromosome.class)
            .bitCount();
    }
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 1.) Define the genotype (factory) suitable
        //     for the problem.
		
		/* A chromosome is an array of Genes. A Genotype is an array of chromosome
		 * the chromosomes in the Genotype do not necessarily have the same size.
		 * Still unclear how this works considering that the genes are the variables of 
		 * the model(a vector not a matrix). All the chromosomes must have the same genes' type */
		
        Factory<Genotype<BitGene>> gtf =
            Genotype.of(BitChromosome.of(50, 0.5));

        // 3.) Create the execution environment.
        Engine<BitGene, Integer> engine = Engine
            .builder(TestJeneticsSolver::eval, gtf)
            //Selection for crossover and mutation follows 
            .survivorsSelector(new RouletteWheelSelector<>())  //Strongest individual are selected and avoid the crossover
			.offspringSelector(new TournamentSelector<>())  //Crossover 
			.offspringFraction(0.7)
			.populationSize(50)
			//when the candidates are selected it is necessary specify how they crossover and how they mutate
            .build();

        // 4.) Start the execution (evolution) and
        //     collect the result.
        Genotype<BitGene> result = engine.stream()
            .limit(100)
            .collect(EvolutionResult.toBestGenotype());

        System.out.println("Hello World:\n" + result);
    }

}



/*
 * CROSSOVER
 * The crossover operator is the process in which individuals from the
 * population trade genetic information, hopefully to create a new individual
 * which contains the best parts from its parents’ genomes. During crossover
 * each individual in the population is considered for crossover; this is where
 * the crossover rate parameter is used. By comparing the crossover rate to a
 * random number, we can decide whether the individual should have crossover
 * applied to it, or whether it should be added straight into the next
 * population unaffected by crossover. If an individual is selected for
 * crossover then a second parent needs be found. To find the second parent, we
 * need to pick one of many possible selection methods.
 */