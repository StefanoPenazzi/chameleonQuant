/**
 * 
 */
package data.source.external.financialdatavendors.alphavantage.parameters.currencies;

import data.source.external.financialdatavendors.parameters.APIParameters;

/**
 * @author stefanopenazzi
 *
 */
public class ToCurrency implements APIParameters {
	  private String toCurrency;

	  public ToCurrency(String toCurrency) {
	    this.toCurrency = toCurrency;
	  }

	  @Override
	  public String getKey() {
	    return "to_symbol";
	  }

	  @Override
	  public String getValue() {
	    return toCurrency;
	  }

}
