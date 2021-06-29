package data.source.external.httpRequest;

import java.net.MalformedURLException;

public interface APIConnector {
	
	/**
	 * This sends a request to an API 
	 * 
	 * @param apiParameters 
	 * @return a string containing the data
	 * @throws MalformedURLException 
	 *
	 */
	
	public String get() throws MalformedURLException;
	public Boolean post();

}
