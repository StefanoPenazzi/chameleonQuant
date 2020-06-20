/**
 * 
 */
package data.source.external.web.parameter.alphaVantage.output;

import data.source.external.web.parameter.APIParameter;

/**
 * @author stefanopenazzi
 *
 */
public enum OutputType implements APIParameter  {
	
	CSV("csv"),
	JSON("json");
	
	 private final String type;

	 OutputType(String type) {
	    this.type = type;
	  }

	  @Override
	  public String getKey() {
	    return "datatype";
	  }

	  @Override
	  public String getValue() {
	    return type;
	  }


}
