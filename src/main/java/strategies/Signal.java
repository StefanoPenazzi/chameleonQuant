/**
 * 
 */
package strategies;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * @author stefanopenazzi
 *
 */
public class Signal {
	
	SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
	
	
	public enum Action {
	    BUY,
	    SELL,
	    STANDBY
	}
	
	private final double volume;
	private final double price;
	private final Action action;
	private final Instant instant;
	
	public Signal(Action action,double price,double volume,Instant instant) {
		this.action = action;
		this.price = price;
		this.volume = volume;
		this.instant = instant;
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
		String formattedDate = formatter.format(date);
		String s = "instant: " + date.toString() + " ; price: " + String.valueOf(this.price) +" ; volume: "+ String.valueOf(this.volume) + " ; action: " +  String.valueOf(this.action)+"\n";
	    return s;
	}

}
