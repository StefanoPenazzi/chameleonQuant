/**
 * 
 */
package or.model;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.function.Function;
import data.source.internal.timeseries.TimeSeriesI;
import io.jenetics.Chromosome;
import io.jenetics.DoubleChromosome;
import io.jenetics.Genotype;
import io.jenetics.IntegerChromosome;
import io.jenetics.IntegerGene;
import strategies.TripleSimpleMovingAverageCrossoverStrategy;
import strategies.positionsizing.InitialMoneyAmount;
import strategies.positionsizing.PositionSizingI;

/**
 * @author stefanopenazzi
 *
 */
public class TSMASJeneticsModel extends StrategyJeneticsModelAbstract {

	Random rand = new Random();
	
	/**
	 * @param strategyC
	 * @param ts
	 */
	public TSMASJeneticsModel(Class<TripleSimpleMovingAverageCrossoverStrategy> strategyC, TimeSeriesI ts) {
		super(strategyC, ts);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Function<Genotype,Double> getFitnessFunction() {
		@SuppressWarnings("unchecked")
		Function<Genotype,Double> res = gt -> {
			int lengthS = ((IntegerChromosome)gt.get(0)).get(0).intValue();
			int lengthM = ((IntegerChromosome)gt.get(0)).get(1).intValue();
		    int lengthL = ((IntegerChromosome)gt.get(0)).get(2).intValue();
		    
		    double targetRange = ((DoubleChromosome)gt.get(1)).get(0).doubleValue();
		    
		    TripleSimpleMovingAverageCrossoverStrategy strategy = null;
			try {
				strategy = (TripleSimpleMovingAverageCrossoverStrategy) this.strategyC
						.getConstructor(TimeSeriesI.class,Integer.TYPE,Integer.TYPE,Integer.TYPE,String.class,Integer.TYPE,Integer.TYPE,Integer.TYPE,Double.TYPE,PositionSizingI.class)
						.newInstance(this.tsl.get(0),lengthS,lengthM,lengthL,"close",0,0,0,targetRange,new InitialMoneyAmount.Builder()
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
		int lengthM = ((IntegerChromosome)gt.get(0)).get(1).intValue();
		int lengthL = ((IntegerChromosome)gt.get(0)).get(2).intValue();
		boolean c1 = lengthS > 0? true : false;
		boolean c2 = lengthM > 0? true : false;
		boolean c3 = lengthL > 0? true : false;
		boolean c4 = lengthS < lengthM ?  true : false;
		boolean c5 = lengthM < lengthL ?  true : false;
		return (c1 && c2 && c3 && c4 && c5);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Genotype<IntegerGene> repairGenotype(Genotype gt) {
		
		int lengthS = rand.ints(1,196).findFirst().getAsInt();
		int lengthM = rand.ints(lengthS,198).findFirst().getAsInt();
		int lengthL = rand.ints(lengthM,200).findFirst().getAsInt();
		
		IntegerGene[] itga = {IntegerGene.of(lengthS,1,200),
				IntegerGene.of(lengthM,1,200),
				IntegerGene.of(lengthL,1,200)};
		return Genotype.of(IntegerChromosome.of(itga),
				gt.get(1));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Genotype getEncoding() {
		
		 int lengthS = rand.ints(1,196).findFirst().getAsInt();
		 int lengthM = rand.ints(lengthS,198).findFirst().getAsInt();
		 int lengthL = rand.ints(lengthM,200).findFirst().getAsInt();
		  
		 IntegerGene[] itga = {IntegerGene.of(lengthS,1,200),
				 IntegerGene.of(lengthM,1,200),
				 IntegerGene.of(lengthL,1,200)}; 
		 return Genotype.of(IntegerChromosome.of(itga),
				 (Chromosome)DoubleChromosome.of(0, 10, 1));
		 
	}

	

}

