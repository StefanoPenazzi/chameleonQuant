/**
 * 
 */
package data.source.external.financialdatavendors.alphavantage.parameters.output;

import data.source.external.financialdatavendors.parameters.APIParameters;

/**
 * @author stefanopenazzi
 *
 */
public enum OutputType implements APIParameters  {
	
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
