/**
 * 
 */
package strategies;

import java.util.List;

/**
 * @author stefanopenazzi
 *
 */
public interface StrategyI {
	
	public void run();
	public double getReturnOnInitialCapital();
	public int getTotNumOfTrades();
	public double getGrossProfit();
	public double getGrossLoss();
	public double getTotNetProfit();
	public double getProfitFactor();
	public double percetProfitable();
	public int getWinningTrades();
	public int getLosingTrades();
	public int getEvenTrades();
	public double getAvgTradeNetProfit();
	public double getAvgWinningTrade();
	public double getAvgLosingTrade();
	public double getRatioAvgWinAvgLoss();
	public double getLargestWinnigTrade();
	public double getLargestLosingTrade();
	public int getMaxConsecWinningTrades();
	public int getMaxConsecLosingTrades();
	public double getPercOfTimeInTheMarket();
	public double getMaxTradeDrawdown();
	public String getPerformanceReport();
	public String printPositions();
	
	public List<Position> getPositions();
	


}
