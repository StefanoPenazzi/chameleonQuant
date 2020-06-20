/**
 * 
 */
package data.source.external.web.parameter.alphaVantage.intradaytimeseries;

import data.source.external.web.parameter.APIParameter;

/**
 * @author stefanopenazzi
 *
 */
public enum OutputSize implements APIParameter {
	  COMPACT("compact"),
	  FULL("full");

	  private final String outputSize;

	  OutputSize(String outputSize) {
	    this.outputSize = outputSize;
	  }

	  @Override
	  public String getKey() {
	    return "outputsize";
	  }

	  @Override
	  public String getValue() {
	    return outputSize;
	  }
	}
