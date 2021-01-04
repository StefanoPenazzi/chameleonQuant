/**
 * 
 */
package data.source.external.web.parameter.eodhistoricaldatacom.securities;

import data.source.external.web.parameter.APIParameter;

/**
 * @author stefanopenazzi
 *
 */
public class Security implements APIParameter {

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

