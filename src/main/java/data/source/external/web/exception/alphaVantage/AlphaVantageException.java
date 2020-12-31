/**
 * 
 */
package data.source.external.web.exception.alphaVantage;

import java.io.IOException;

/**
 * @author stefanopenazzi
 *
 */
public class AlphaVantageException extends RuntimeException {
	
	public AlphaVantageException(String message, Throwable e) {
		super(message,e);
		
	}

}
