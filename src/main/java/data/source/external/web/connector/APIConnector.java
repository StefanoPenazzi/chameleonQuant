/**
 * 
 */
package data.source.external.web.connector;

import data.source.external.web.parameter.APIParameter;

/**
 * @author stefanopenazzi
 *
 */
public interface APIConnector {
	
	String call(APIParameter... apiParameters);

}
