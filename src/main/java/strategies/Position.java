/**
 * 
 */
package strategies;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import data.source.internal.timeseries.TimeSeriesI;



/**
 * @author stefanopenazzi
 *
 */
public final class Position {
	
	public enum PositionType{
		LONG,
		SHORT
	}
	
	public enum Action {
	    BUY,
	    SELL
	}
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private List<Signal> signals = new ArrayList<>();
	private PositionType pt;
	private String sId;
	private int initVolume;
	private int currVolume;
	private double pr;
	private Instant openInst;
	private Instant closeInst;
	private boolean open;
	private double currWinLoss = 0;
	private String uuid;
	
    public static class Builder {
		
		private TimeSeriesI ts;
		private PositionType pt;
		private String sId;
		private int initVolume;
		private double pr;
		private Instant openInst;
		
		public Builder(PositionType pt) {
	        this.pt = pt;
	    }
		
		public Builder securityId(String sId){
	           this.sId = sId; 
	            return this;
	    }
		
		public Builder initialVolume(int initVolume){
	           this.initVolume = initVolume; 
	            return this;
	    }
		
		public Builder price(double pr){
	           this.pr = pr; 
	            return this;
	    }
		
		public Builder openInstant(Instant openInst){
	           this.openInst = openInst; 
	            return this;
	    }
		
		public Position build(){
			 Position position = new Position(); 
			 
			 position.pt = this.pt;
			 position.sId = this.sId;
			 position.initVolume = this.initVolume;
			 position.currVolume = this.initVolume;
			 position.pr = this.pr;
			 position.openInst = this.openInst;
			 position.open = true;
			 position.uuid = UUID.randomUUID().toString();
			 position.addNewSignal(this.pr,this.initVolume,this.openInst);
			 return position;
		}		
	}
    
    public final class Signal {
    	
    	DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd[ HH:mm:ss]")
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter().withZone(ZoneOffset.UTC);
    	
    	private final double volume;
    	private final double price;
    	private final Action action;
    	private final Instant instant;
    	private final String uuid;
    	private final String sId;
    	
    	public Signal(String uuid,String sId,Action action,double price,double volume,Instant instant) {
    		this.action = action;
    		this.price = price;
    		this.volume = volume;
    		this.instant = instant;
    		this.uuid = uuid;
    		this.sId =sId;
    	}
    	
    	public double getVolume() {
    		 return this.volume;
    	 }
    	
    	public double getPrice() {
    		 return this.price;
    	 }
    	
    	public Action getAction() {
    		 return this.action;
    	 }
    	
    	public Instant getInstant() {
    		 return this.instant;
    	 }
    	
    	public String print() {
    		Date date = Date.from(this.instant);
    		String formattedDate = formatter.format(this.instant);
    		String s = ">>> date: " + date.toString() + " ; price: " + String.valueOf(this.price) +" ; volume: "+ String.valueOf(this.volume) + " ; action: " +  String.valueOf(this.action)+"\n";
    	    return s;
    	}
    	
    	public String printCsv() {
    		String formattedDate = formatter.format(this.instant);
    		String s = this.uuid+";"+ this.sId+";"+ formattedDate + ";" + String.valueOf(this.price) +";"+ String.valueOf(this.volume) + ";" +  String.valueOf(this.action)+"\n";
    	    return s;
    	}

    }
    
    public void addNewSignal(double price,double volume,Instant instant) {
    	if(this.open == false) {
    		//throw exception
    	} 
    	if(signals.size() == 0) {
    		if(this.pt == PositionType.LONG) {
    			signals.add(new Signal(this.uuid,this.sId,Action.BUY,price,volume,instant));
    		}
    		else {
    			signals.add(new Signal(this.uuid,this.sId,Action.SELL,price,volume,instant));
    		}
    	}
    	else {
	    	if(this.pt == PositionType.LONG) {
	    		this.currWinLoss += (price-pr)*volume;
	    		this.currVolume -= volume;
	    		open = this.currVolume <= 0 ? false:true;
	    		signals.add(new Signal(this.uuid,this.sId,Action.SELL,price,volume,instant));
	    	}
	    	else {
	    		this.currVolume -= volume;
	    		this.currWinLoss -= (price - pr)*volume;
	    		open = this.currVolume <= 0 ? false:true;
	    		signals.add(new Signal(this.uuid,this.sId,Action.BUY,price,volume,instant));
	    	}
    	}
    }
    
    public int getCurrVolume() {
    	return this.currVolume;
    }
    
    public double getCurrInvestment(double currPrice) {
    	return this.currVolume * currPrice;
    }
    
    public double getCurrWinLoss(double currPrice) {
    	if(this.pt == PositionType.LONG) {
    		return this.currWinLoss + (currPrice-pr)*currVolume;
    	}
    	else {
    		return this.currWinLoss + (pr-currPrice)*currVolume;
    	}
    }
    
    public double getWinLoss() {
    	return this.currWinLoss;
    }
    
    public double getReturnOnInitialCapital() {
    	return this.getWinLoss()/(initVolume * pr);
    }
    
    public String print() {
		Date date = Date.from(this.openInst);
		String formattedDate = formatter.format(date);
		String s = "open date: " + date.toString() + " ; open price: " + String.valueOf(this.pr) +" ; open volume: "+ String.valueOf(this.initVolume) + " ; type: " +  String.valueOf(this.pt)+" ; ROI: "+ String.valueOf(getReturnOnInitialCapital()) +"\n";
		for(Signal sig: signals) {
			s += sig.print();
		}
	    return s;
	}
    
    public String printCsv() {
    	String s = "";
    	for(Signal sig: signals) {
			s += sig.printCsv();
		}
	    return s;
    }
    
    public PositionType getPositionType() {
    	return this.pt;
    }

}
