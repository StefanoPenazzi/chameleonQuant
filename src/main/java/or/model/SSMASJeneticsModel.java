/**
 * 
 */
package or.model;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;
import data.source.internal.timeseries.TimeSeriesI;
import io.jenetics.Genotype;
import io.jenetics.IntegerChromosome;
import io.jenetics.IntegerGene;
import strategies.SingleSimpleMovingAverageStrategy;
import strategies.positionsizing.FixedMoneyAmount;
import strategies.positionsizing.PositionSizingI;

/**
 * @author stefanopenazzi
 *
 */
public class SSMASJeneticsModel extends StrategyJeneticsModelAbstract {

	/**
	 * @param strategyC
	 * @param ts
	 */
	public SSMASJeneticsModel(Class<SingleSimpleMovingAverageStrategy> strategyC, TimeSeriesI ts) {
		super(strategyC, ts);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Function<Genotype,Double> getFitnessFunction() {
		@SuppressWarnings("unchecked")
		Function<Genotype,Double> res = gt -> {
			IntegerChromosome length = (IntegerChromosome)gt.get(0);
			SingleSimpleMovingAverageStrategy strategy = null;
			try {
				strategy = (SingleSimpleMovingAverageStrategy) this.strategyC.getConstructor(TimeSeriesI.class,String.class,Integer.TYPE,Integer.TYPE,PositionSizingI.class).newInstance(this.tsl.get(0),"close",length.intValue(),0,new FixedMoneyAmount.Builder()
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

	@Override
	public Boolean isValid(Genotype gt) {
		return ((IntegerChromosome)gt.get(0)).intValue() > 0? true : false;
	}

	@Override
	public Genotype<IntegerGene> repairGenotype(Genotype gt) {
		return Genotype.of(IntegerChromosome.of(1, 200, 1));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Genotype getEncoding() {
		return Genotype.of(IntegerChromosome.of(1, 200, 1));
	}

	

}
