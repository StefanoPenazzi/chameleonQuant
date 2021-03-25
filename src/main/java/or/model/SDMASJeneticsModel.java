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
import strategies.DualSimpleMovingAverageCrossoverStrategy;
import strategies.positionsizing.FixedMoneyAmount;
import strategies.positionsizing.InitialMoneyAmount;
import strategies.positionsizing.PositionSizingI;
import java.util.Random;

/**
 * @author stefanopenazzi
 *
 */
public class SDMASJeneticsModel extends StrategyJeneticModelAbstract {

	Random rand = new Random();
	
	/**
	 * @param strategyC
	 * @param ts
	 */
	public SDMASJeneticsModel(Class<DualSimpleMovingAverageCrossoverStrategy> strategyC, TimeSeriesI ts) {
		super(strategyC, ts);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Function<Genotype,Double> getFitnessFunction() {
		@SuppressWarnings("unchecked")
		Function<Genotype,Double> res = gt -> {
			int lengthS = ((IntegerChromosome)gt.get(0)).get(0).intValue();
		    int lengthL = ((IntegerChromosome)gt.get(0)).get(1).intValue();
			DualSimpleMovingAverageCrossoverStrategy strategy = null;
			try {
				strategy = (DualSimpleMovingAverageCrossoverStrategy) this.strategyC.getConstructor(TimeSeriesI.class,Integer.TYPE,Integer.TYPE,String.class,Integer.TYPE,Integer.TYPE,PositionSizingI.class).newInstance(this.tsl.get(0),lengthS,lengthL,"close",0,0,new InitialMoneyAmount.Builder()
						.initialMoneyAmount(10000)
						.build());
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
			strategy.run();
			return strategy.getTotNetProfit();
		};
		return res;
	}

	@Override
	public Boolean isValid(Genotype gt) {
		int lengthS = ((IntegerChromosome)gt.get(0)).get(0).intValue();
		int lengthL = ((IntegerChromosome)gt.get(0)).get(1).intValue();
		boolean c1 = lengthS > 0? true : false;
		boolean c2 = lengthL > 0? true : false;
		boolean c3 = lengthS < lengthL ?  true : false;
		return c1 & c2 & c3;
	}

	@Override
	public Genotype<IntegerGene> repairGenotype(Genotype gt) {
		
		int lengthS = rand.ints(1,199).findFirst().getAsInt();
		int lengthL = rand.ints(lengthS,200).findFirst().getAsInt();
		
		IntegerGene[] itga = {IntegerGene.of(lengthS,1,200),
				IntegerGene.of(lengthL,1,200)};
		return Genotype.of(IntegerChromosome.of(itga));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Genotype getEncoding() {
		
		 int lengthS = rand.ints(1,199).findFirst().getAsInt();
		 int lengthL = rand.ints(lengthS,200).findFirst().getAsInt();
		  
		 IntegerGene[] itga = {IntegerGene.of(lengthS,1,200),IntegerGene.of(lengthL,1,200)}; 
		 return Genotype.of(IntegerChromosome.of(itga));
		 
	}

	

}
