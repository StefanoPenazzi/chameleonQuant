/**
 * 
 */
package data.source.external.web.connector;

import data.source.external.web.parameter.APIParameter;

/**
 * @author stefanopenazzi
 * 
 * This representŝ the connection to an API end point
 *
 */
public interface APIConnector {
	

	/**
	 * This sends a request to an API 
	 * 
	 * @param apiParameters 
	 * @return a string containing the data
	 *
	 */
	
	public String call(APIParameter... apiParameters);

}
