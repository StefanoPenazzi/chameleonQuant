/**
 * 
 */
package strategies;

import java.util.ArrayList;
import java.util.List;

import strategies.Signal.Action;

/**
 * @author stefanopenazzi
 *
 */
public abstract class StrategyAbstract implements StrategyI {
	
	protected List<Signal> signals = new ArrayList<>();
	
	@Override
	public List<Signal> getSignals() {
		return this.signals;
	}
	
	@Override
	public double getReturnOnInitialCapital() {
		
		if(signals.size() <= 1) return 1.0;
		
		double expRet = 1.0;
		Action action = signals.get(0).getAction();
		double prevPrice = signals.get(0).getPrice();
		
		for(int i =1;i<signals.size();i++) {
			if(action == Action.BUY) {
				expRet = expRet * (1-((prevPrice-signals.get(i).getPrice())/prevPrice));
				action = Action.BUY;
			}
			else {
				expRet = expRet * (1+((prevPrice-signals.get(i).getPrice())/prevPrice));
				action = Action.SELL;
			}
			action = signals.get(i).getAction();
			prevPrice = signals.get(i).getPrice();
		}
		return expRet;
	}
	
	@Override
	public int getTotNumOfTrades() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getGrossProfit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getGrossLoss() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getTotNetProfit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getProfitFactor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double percetProfitable() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWinningTrades() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLosingTrades() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getEvenTrades() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAvgTradeNetProfit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAvgWinningTrade() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAvgLosingTrade() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getRatioAvgWinAvgLoss() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getLargestWinnigTrade() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getLargestLosingTrade() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxConsecWinningTrades() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxConsecLosingTrades() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getPercOfTimeInTheMarket() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getMaxTradeDrawdown() {
		// TODO Auto-generated method stub
		return 0;
	}

}
