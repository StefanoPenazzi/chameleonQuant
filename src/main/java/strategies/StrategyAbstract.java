/**
 * 
 */
package strategies;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import strategies.StrategiesAnnotations.InputStrategyReport;
import strategies.StrategiesAnnotations.OutputStrategyReport;


/**
 * @author stefanopenazzi
 *
 */
public abstract class StrategyAbstract implements StrategyI {
	
	protected List<Position> positions = new ArrayList<>();
	
	protected static abstract class Builder <T extends StrategyAbstract, B extends Builder<T, B>> {}
	
	private enum ReportElementType{
		INPUT,
		OUTPUT
	}
	
	private final class ReportElement{
		private final String name;
		private final String section;
		private final int position;
		private final ReportElementType ret;
		private final Object result;
		public ReportElement(ReportElementType ret, String name,String section,int position,Object result) {
			this.ret = ret;
			this.name = name;
			this.section = section;
			this.position = position;
			this.result = result;
		}
		
		public String getName() {return this.name;}
		public String getSection() {return this.section;}
		public int getPosition() {return this.position;}
		public ReportElementType getReportElementType() {return this.ret;}
		public Object getResult() {return this.result;}
		
		
	}
	
	@Override
	public List<Position> getPositions() {
		return this.positions;
	}
	
	@Override
	@OutputStrategyReport(name = "Position details",section="Details",position=0)
	public String printPositions() {
		String s = "id,security,date,price,volume,action \n";
		for(Position position: positions) {
			s += position.print();
		}
		return s;
	}
	
	
	@Override
	@OutputStrategyReport(name = "Tot. Num. Of Trades",section="Main",position=4)
	public Integer getTotNumOfTrades() {
		return positions.size();
	}

	@Override
	@OutputStrategyReport(name = "Gross Profit",section="Main",position=1)
	public Double getGrossProfit() {
		Double res = 0.0;
		for(Position position: positions) {
			res += position.getWinLoss() > 0 ? position.getWinLoss(): 0;
		}
		return res;
	}

	@Override
	@OutputStrategyReport(name = "Gross Loss",section="Main",position=2)
	public Double getGrossLoss() {
		Double res = 0.0;
		for(Position position: positions) {
			res += position.getWinLoss() < 0 ? position.getWinLoss(): 0;
		}
		return res;
	}

	@Override
	@OutputStrategyReport(name = "Tot. Net Profit",section="Main",position=0)
	public Double getTotNetProfit() {
		Double res = 0.0;
		for(Position position: positions) {
			res += position.getWinLoss();
		}
		return res;
	}

	@Override
	@OutputStrategyReport(name = "Profit Factor",section="Main",position=3)
	public Double getProfitFactor() {
		
		return getGrossProfit()/getGrossLoss();
	}

	@Override
	@OutputStrategyReport(name = "Percet Profitable",section="Main",position=5)
	public Double getPercetProfitable() {
		return (double) (getWinningTrades()/positions.size());
	}

	@Override
	@OutputStrategyReport(name = "Winning Trades",section="Main",position=6)
	public Integer getWinningTrades() {
		Integer res = 0;
		for(Position position: positions) {
			res += position.getWinLoss() > 0 ? 1: 0;
		}
		return res;
	}

	@Override
	@OutputStrategyReport(name = "Losing Trades",section="Main",position=7)
	public Integer getLosingTrades() {
		Integer res = 0;
		for(Position position: positions) {
			res += position.getWinLoss() < 0 ? 1: 0;
		}
		return res;
	}

	@Override
	@OutputStrategyReport(name = "Even Trades",section="Main",position=8)
	public Integer getEvenTrades() {
		Integer res = 0;
		for(Position position: positions) {
			res += position.getWinLoss() == 0 ? 1: 0;
		}
		return res;
	}

	@Override
	@OutputStrategyReport(name = "Avg. Trade Net Profit",section="Main",position=9)
	public Double getAvgTradeNetProfit() {
		return getTotNetProfit()/positions.size();
	}

	@Override
	@OutputStrategyReport(name = "Avg. Winning Trade",section="Main",position=10)
	public Double getAvgWinningTrade() {
		return getGrossProfit()/getWinningTrades();
	}

	@Override
	@OutputStrategyReport(name = "Avg. Losing Trade",section="Main",position=11)
	public Double getAvgLosingTrade() {
		return getGrossLoss()/getLosingTrades();
	}

	@Override
	@OutputStrategyReport(name = "Ratio Avg. Win Avg. Loss",section="Main",position=12)
	public Double getRatioAvgWinAvgLoss() {
		return getAvgWinningTrade()/getAvgLosingTrade();
		
	}

	@Override
	@OutputStrategyReport(name = "Largest Winnig Trade",section="Main",position=13)
	public Double getLargestWinnigTrade() {
		double res = 0;
		for(Position position: positions) {
			res = position.getWinLoss() > res ? position.getWinLoss(): res;
		}
		return res;
	}

	@Override
	@OutputStrategyReport(name = "Largest Losing Trade",section="Main",position=14)
	public Double getLargestLosingTrade() {
		double res = 0;
		for(Position position: positions) {
			res = position.getWinLoss() < res ? position.getWinLoss(): res;
		}
		return res;
	}

	@Override
	@OutputStrategyReport(name = "Max. Consecutive Winning Trades",section="Main",position=15)
	public Integer getMaxConsecWinningTrades() {
		Integer res = 0;
		int currMaxConsecWinningTrades = 0;
		for(Position position: positions) {
			if(position.getWinLoss() < 0) {
				res = res<currMaxConsecWinningTrades? currMaxConsecWinningTrades:res;
				currMaxConsecWinningTrades = 0;
			}
			else {
				currMaxConsecWinningTrades++;
			}
		}
		return res;
	}

	@Override
	@OutputStrategyReport(name = "Max. Consecutive Losing Trades",section="Main",position=16)
	public Integer getMaxConsecLosingTrades() {
		Integer res = 0;
		int currMaxConsecLosingTrades = 0;
		for(Position position: positions) {
			if(position.getWinLoss() > 0) {
				res = res<currMaxConsecLosingTrades? currMaxConsecLosingTrades:res;
				currMaxConsecLosingTrades = 0;
			}
			else {
				currMaxConsecLosingTrades++;
			}
		}
		return res;
	}

	@Override
	@OutputStrategyReport(name = "Perc. Of Time In The Market",section="Main",position=17)
	public Double getPercOfTimeInTheMarket() {
		// TODO Auto-generated method stub
		return 0.0;
	}

	@Override
	@OutputStrategyReport(name = "Max Trade Drawdown",section="Main",position=18)
	public Double getMaxTradeDrawdown() {
		// TODO Auto-generated method stub
		return 0.0;
	}
	
	@Override
	public String getPerformanceReport() {
		String res = "";
		List<ReportElement> reportElements = new ArrayList<ReportElement>();
		
		for(Method m: this.getClass().getMethods()) {
			if(m.isAnnotationPresent(OutputStrategyReport.class)) {
				OutputStrategyReport osr = m.getAnnotation(OutputStrategyReport.class);
				try {
					reportElements.add(new ReportElement(ReportElementType.OUTPUT,osr.name(),osr.section(),osr.position(),m.invoke(this)));
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			if(m.isAnnotationPresent(InputStrategyReport.class)) {
				InputStrategyReport osr = m.getAnnotation(InputStrategyReport.class);
				try {
					reportElements.add(new ReportElement(ReportElementType.OUTPUT,osr.name(),osr.section(),osr.position(),m.invoke(this)));
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		
		res += "------- INPUTS ------- \n";
		List<ReportElement> inputReportElements = reportElements.stream()
				.filter(e -> e.getReportElementType() == ReportElementType.INPUT)
				.sorted(Comparator.comparing(ReportElement::getSection).thenComparing(Comparator.comparingInt(ReportElement::getPosition)))
				.collect(Collectors.toList());
		for(ReportElement re: inputReportElements){
			res += re.getName() + " : " +re.getResult().toString() + "\n";
		}
		res += "\n";
		res += "------- OUTPUT GENERAL ------- \n";
		List<ReportElement> outputReportElements = reportElements.stream()
				.filter(e -> e.getReportElementType() == ReportElementType.OUTPUT)
				.filter(e -> !e.getSection().equals("Details"))
				.sorted(Comparator.comparing(ReportElement::getSection).thenComparing(Comparator.comparingInt(ReportElement::getPosition)))
				.collect(Collectors.toList());
		for(ReportElement re: outputReportElements){
			res += re.getName() + " : " +re.getResult().toString() + "\n";
		}
		res += "\n";
		res += "------- OUTPUT DETAILS ------- \n";
		List<ReportElement> outputDetailsReportElements = reportElements.stream()
				.filter(e -> e.getReportElementType() == ReportElementType.OUTPUT)
				.filter(e -> e.getSection().equals("Details"))
				.sorted(Comparator.comparing(ReportElement::getSection).thenComparing(Comparator.comparingInt(ReportElement::getPosition)))
				.collect(Collectors.toList());
		for(ReportElement re: outputDetailsReportElements){
			res += re.getName() + " : " +re.getResult().toString() + "\n";
		}
		
		
		return res;
	}
	
	

}
