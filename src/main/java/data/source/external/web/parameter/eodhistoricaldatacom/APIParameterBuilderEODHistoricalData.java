/**
 * 
 */
package data.source.external.web.parameter.eodhistoricaldatacom;

import data.source.external.web.parameter.APIParameterBuilderAbstract;

/**
 * @author stefanopenazzi
 *
 */
public class APIParameterBuilderEODHistoricalData extends APIParameterBuilderAbstract {

	@Override
	public String buildPair(String key, String value) {
		String parameter = "";
		switch(key) {
			case "api_token":
				parameter = key + "=" + value;
				break;
			case "security_type":
				parameter = value + "/";
				break;
			case "from":
				parameter = "&" + key + "=" + value;
				break;
			case "to":
				parameter = "&" + key + "=" + value;
				break;
			case "intervall":
				parameter = "&" + key + "=" + value;
				break;
			case "symbol":
				 parameter = value +"?";
				break;
		}
		return parameter;
	}

}
