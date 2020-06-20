/**
 * 
 */
package data.source.external.web.parameter.alphaVantage.output;

import data.source.external.web.parameter.APIParameter;

/**
 * @author stefanopenazzi
 *
 */
public enum OutputSize implements APIParameter  {
	
	COMPACT("compact"),
	FULL("full");
	
	 private final String size;

	  OutputSize(String size) {
	    this.size = size;
	  }

	  @Override
	  public String getKey() {
	    return "outputsize";
	  }

	  @Override
	  public String getValue() {
	    return size;
	  }


}
