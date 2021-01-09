/**
 * 
 */
package data.source.external.financialdatavendors.alphavantage.parameters.output;

import data.source.external.financialdatavendors.parameters.APIParameters;

/**
 * @author stefanopenazzi
 *
 */
public enum OutputSize implements APIParameters  {
	
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
