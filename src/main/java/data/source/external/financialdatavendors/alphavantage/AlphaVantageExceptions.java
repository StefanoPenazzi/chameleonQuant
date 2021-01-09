/**
 * 
 */
package data.source.external.financialdatavendors.alphavantage;

import java.io.IOException;

/**
 * @author stefanopenazzi
 *
 */
public class AlphaVantageExceptions extends RuntimeException {
	
	public AlphaVantageExceptions(String message, Throwable e) {
		super(message,e);
		
	}

}
