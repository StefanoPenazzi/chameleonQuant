/**
 * 
 */
package data.source.external.web.parameter;

/**
 * @author stefanopenazzi
 *
 */
public interface APIParameter {
	
	/**
	   * Get key for parameter.
	   *
	   * @return the key
	   */
	  String getKey();

	  /**
	   * Get value for parameter.
	   *
	   * @return the value
	   */
	  String getValue();
	

}
