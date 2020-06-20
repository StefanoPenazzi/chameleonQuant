/**
 * 
 */
package data.source.external.web.parameter.alphaVantage.currencies;

import data.source.external.web.parameter.APIParameter;

/**
 * @author stefanopenazzi
 *
 */
public class FromCurrency implements APIParameter {
	  String fromCurrency;

	  public FromCurrency(String fromCurrency) {
	    this.fromCurrency = fromCurrency;
	  }

	  @Override
	  public String getKey() {
	    return "from_currency";
	  }

	  @Override
	  public String getValue() {
	    return fromCurrency;
	  }

	}
