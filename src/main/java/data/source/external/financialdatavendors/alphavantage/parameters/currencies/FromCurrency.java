/**
 * 
 */
package data.source.external.financialdatavendors.alphavantage.parameters.currencies;

import data.source.external.financialdatavendors.parameters.APIParameters;

/**
 * @author stefanopenazzi
 *
 */
public class FromCurrency implements APIParameters {
	  String fromCurrency;

	  public FromCurrency(String fromCurrency) {
	    this.fromCurrency = fromCurrency;
	  }

	  @Override
	  public String getKey() {
	    return "from_symbol";
	  }

	  @Override
	  public String getValue() {
	    return fromCurrency;
	  }

	}
