/**
 * 
 */
package strategies;

import java.util.ArrayList;
import java.util.List;
import strategies.Position.PositionType;


/**
 * @author stefanopenazzi
 *
 */
public abstract class StrategyAbstract implements StrategyI {
	
	protected List<Position> positions = new ArrayList<>();
	
	 protected static abstract class Builder <T extends StrategyAbstract, B extends Builder<T, B>> {}
	
	@Override
	public List<Position> getPositions() {
		return this.positions;
	}
	
	@Override
	public String printPositions() {
		String s = "id,security,date,price,volume,action \n";
		for(Position position: positions) {
			s += position.print();
		}
		return s;
	}
	
	
	@Override
	public double getReturnOnInitialCapital() {
		double res = 0.0;
		for(Position position: positions) {
			res += position.getReturnOnInitialCapital();
		}
		return res;
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
	
	@Override
	public String getPerformanceReport() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
