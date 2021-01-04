/**
 * 
 */
package data.source.external.web.parameter.eodhistoricaldatacom.symbols;

import data.source.external.web.parameter.APIParameter;

/**
 * @author stefanopenazzi
 *
 */
public class Symbol implements APIParameter {

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
