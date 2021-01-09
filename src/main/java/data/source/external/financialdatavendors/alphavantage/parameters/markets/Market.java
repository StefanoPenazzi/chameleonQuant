/**
 * 
 */
package data.source.external.financialdatavendors.alphavantage.parameters.markets;

import data.source.external.financialdatavendors.parameters.APIParameters;

/**
 * @author stefanopenazzi
 *
 */
public class Market implements APIParameters{
	
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
