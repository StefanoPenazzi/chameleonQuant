/**
 * 
 */
package data.source.external.financialdatavendors.alphavantage.parameters.symbols;

import data.source.external.financialdatavendors.parameters.APIParameters;

/**
 * @author stefanopenazzi
 *
 */
public class Symbol implements APIParameters {

	private String symbol;

	  public Symbol(String symbol) {
	    this.symbol = symbol;
	  }

	  @Override
	  public String getKey() {
	    return "symbol";
	  }

	  @Override
	  public String getValue() {
	    return symbol;
	  }

}
