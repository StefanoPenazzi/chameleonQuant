/**
 * 
 */
package or.model;

import data.source.internal.timeseries.TimeSeriesI;
import io.jenetics.Genotype;
import io.jenetics.IntegerChromosome;
import io.jenetics.IntegerGene;
import strategies.SingleMovingAverageCrossoverStrategy;

/**
 * @author stefanopenazzi
 *
 */
public class SSMASJeneticModel extends SMASJeneticModel {

	/**
	 * @param strategyC
	 * @param ts
	 */
	public SSMASJeneticModel(Class strategyC, TimeSeriesI ts) {
		super(strategyC, ts);
	}

	@Override
	public double fitnessFunctionDesign(SingleMovingAverageCrossoverStrategy smacs) {
		return smacs.getTotNetProfit();
	}

	@Override
	public Boolean isValid(Genotype gt) {
		return ((IntegerChromosome)gt.get(0)).intValue() > 0? true : false;
	}

	@Override
	public Genotype<IntegerGene> repairGenotype(Genotype gt) {
		return Genotype.of(IntegerChromosome.of(1, 200, 1));
	}

	

}
