/**
 * 
 */
package data.source.external.web.parameter;

/**
 * @author stefanopenazzi
 *
 */
public class APIParameterBuilder {
	
	private StringBuilder urlBuilder;

	  public APIParameterBuilder() {
	    this.urlBuilder = new StringBuilder();
	  }

	  /**
	   * Append an api paramter to the builder.
	   *
	   * @param apiParameter the api parameter to append to the url.
	   * @return an instance of this builder.
	   */
	  public void append(APIParameter apiParameter) {
	    if (apiParameter != null) {
	      append(apiParameter.getKey(), apiParameter.getValue());
	    }
	  }

	  /**
	   * Append raw strings parameters to the builder, key and value.
	   *
	   * @param key in the api paramter key value pair.
	   * @param value in the api parameter key value pair.
	   * @return an instance of this builder.
	   */
	  public void append(String key, String value) {
	    String parameter = "&" + key + "=" + value;
	    this.urlBuilder.append(parameter);
	  }

	  /**
	   * Build the url string for the query in the api call.
	   *
	   * @return the url query string.
	   */
	  public String getUrl() {
	    return this.urlBuilder.toString();
	  }

}
