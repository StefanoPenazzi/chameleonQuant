/**
 * 
 */
package data.source.external.web.parameter.alphaVantage.currencies;

import data.source.external.web.parameter.APIParameter;

/**
 * @author stefanopenazzi
 *
 */
public class ToCurrency implements APIParameter {
	  private String toCurrency;

	  public ToCurrency(String toCurrency) {
	    this.toCurrency = toCurrency;
	  }

	  @Override
	  public String getKey() {
	    return "to_currency";
	  }

	  @Override
	  public String getValue() {
	    return toCurrency;
	  }

}
