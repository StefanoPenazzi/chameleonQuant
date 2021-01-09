/**
 * 
 */
package data.source.external.financialdatavendors.eodhistoricaldatacom;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import data.source.external.financialdatavendors.connector.APIConnectorAbstract;
import data.source.external.financialdatavendors.eodhistoricaldatacom.parameters.APIParameterBuilderEODHistoricalData;
import data.source.external.financialdatavendors.parameters.APIParameters;
import data.source.external.financialdatavendors.parameters.APIParametersBuilderI;

/**
 * @author stefanopenazzi
 *
 */
public class EODHistoricalDataConnector extends APIConnectorAbstract {

	private String BASE_URL = "https://eodhistoricaldata.com/api/";
	private String apiKeyId = "api_token";
	/**
	 * @param apiKey
	 * @param timeOut
	 * @throws FileNotFoundException 
	 */
	public EODHistoricalDataConnector(int timeOut) {
		super("EODHistoricalData",timeOut);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getBaseUrl() {
		
		return BASE_URL;
	}

	@Override
	public APIParametersBuilderI getAPIParameterBuilder() {
		return new APIParameterBuilderEODHistoricalData();
	}

	@Override
	public String getParameters(APIParameters... apiParameters) {
		Map<String,String> dict = new HashMap<String,String>();
		for (APIParameters parameter : apiParameters) {
		       dict.put(parameter.getKey(), parameter.getValue());
		    }
		APIParametersBuilderI urlBuilder = getAPIParameterBuilder();
		urlBuilder.append("security_type",dict.get("security_type"));
		urlBuilder.append("symbol",dict.get("symbol"));
		urlBuilder.append(this.apiKeyId,this.apiKey);
		dict.remove("security_type");
		dict.remove("symbol");
		//sort the parameters 
	    for (String s: dict.keySet()) {
	       urlBuilder.append(s,dict.get(s));
	    }
	    return urlBuilder.getUrl();
	}
}
