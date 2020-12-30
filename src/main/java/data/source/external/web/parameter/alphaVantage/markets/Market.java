/**
 * 
 */
package data.source.external.web.parameter.alphaVantage.markets;

import data.source.external.web.parameter.APIParameter;

/**
 * @author stefanopenazzi
 *
 */
public class Market implements APIParameter{
	
	private String market;

	  public Market(String market) {
	    this.market = market;
	  }

	  @Override
	  public String getKey() {
	    return "market";
	  }

	  @Override
	  public String getValue() {
	    return market;
	  }

}
