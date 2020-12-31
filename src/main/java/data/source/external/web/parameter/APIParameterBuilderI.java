/**
 * 
 */
package data.source.external.web.parameter;

/**
 * @author stefanopenazzi
 *
 */
public interface APIParameterBuilderI {
	
	 public void append(APIParameter apiParameter);
	 public void append(String key, String value);
	 public String getUrl();
}
