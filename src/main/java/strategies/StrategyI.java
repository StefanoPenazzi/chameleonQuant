/**
 * 
 */
package strategies;

import java.util.List;

import strategies.positionsizing.PositionSizingI;

/**
 * @author stefanopenazzi
 *
 */
public interface StrategyI {
	
	public void run();
	public PositionSizingI getPositionSizing();
	public String getStrategyName();
	
	public Integer getTotNumOfTrades();
	public Double getGrossProfit();
	public Double getGrossLoss();
	public Double getTotNetProfit();
	public Double getProfitFactor();
	public Double getPercetProfitable();
	public Integer getWinningTrades();
	public Integer getLosingTrades();
	public Integer getEvenTrades();
	public Double getAvgTradeNetProfit();
	public Double getAvgWinningTrade();
	public Double getAvgLosingTrade();
	public Double getRatioAvgWinAvgLoss();
	public Double getLargestWinnigTrade();
	public Double getLargestLosingTrade();
	public Integer getMaxConsecWinningTrades();
	public Integer getMaxConsecLosingTrades();
	public Double getPercOfTimeInTheMarket();
	public Double getMaxTradeDrawdown();
	public String getPerformanceReport();
	public String printPositions();
	
	public List<Position> getPositions();
	


}
