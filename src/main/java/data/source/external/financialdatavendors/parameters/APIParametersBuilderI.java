/**
 * 
 */
package data.source.external.financialdatavendors.parameters;

/**
 * @author stefanopenazzi
 *
 */
public interface APIParametersBuilderI {
	
	 public void append(APIParameters apiParameter);
	 public void append(String key, String value);
	 public String getUrl();
}
