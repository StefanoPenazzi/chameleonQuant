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
import strategies.SingleExpMovingAverageStrategy;
import strategies.positionsizing.FixedMoneyAmount;
import strategies.positionsizing.PositionSizingI;

/**
 * @author stefanopenazzi
 *
 */
public class ESMASJeneticModel extends StrategyJeneticModelAbstract {

	/**
	 * @param strategyC
	 * @param ts
	 */
	public ESMASJeneticModel(Class<SingleExpMovingAverageStrategy> strategyC, TimeSeriesI ts) {
		super(strategyC, ts);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Boolean isValid(Genotype gt) {
		return ((IntegerChromosome)gt.get(0)).intValue() > 0? true : false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Genotype repairGenotype(Genotype gt) {
		return Genotype.of(IntegerChromosome.of(1, 200, 1));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Function<Genotype,Double> getFitnessFunction() {
		@SuppressWarnings("unchecked")
		Function<Genotype,Double> res = gt -> {
			IntegerChromosome length = (IntegerChromosome)gt.get(0);
			SingleExpMovingAverageStrategy strategy = null;
			try {
				strategy = (SingleExpMovingAverageStrategy) this.strategyC.getConstructor(TimeSeriesI.class,String.class,Integer.TYPE,Double.TYPE,PositionSizingI.class).newInstance(this.tsl.get(0),"close",length.intValue(),2,new FixedMoneyAmount.Builder()
						.fixedMoneyAmount(10000)
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Genotype getEncoding() {
		return Genotype.of((Chromosome)IntegerChromosome.of(1, 200, 1));
	}

}