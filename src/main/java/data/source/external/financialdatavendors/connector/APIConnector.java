/**
 * 
 */
package data.source.external.financialdatavendors.connector;

import data.source.SourceI;
import data.source.external.financialdatavendors.parameters.APIParameters;

/**
 * @author stefanopenazzi
 * 
 * This representŝ the connection to an API end point
 *
 */
public interface APIConnector extends SourceI {
	

	/**
	 * This sends a request to an API 
	 * 
	 * @param apiParameters 
	 * @return a string containing the data
	 *
	 */
	
	public String call(APIParameters... apiParameters);

}
