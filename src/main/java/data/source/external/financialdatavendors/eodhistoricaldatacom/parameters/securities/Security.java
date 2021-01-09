/**
 * 
 */
package data.source.external.financialdatavendors.eodhistoricaldatacom.parameters.securities;

import data.source.external.financialdatavendors.parameters.APIParameters;

/**
 * @author stefanopenazzi
 *
 */
public class Security implements APIParameters {

	private String security;

	  public Security(String security) {
	    this.security = security;
	  }

	  @Override
	  public String getKey() {
	    return "security_type";
	  }

	  @Override
	  public String getValue() {
	    return security;
	  }

}

