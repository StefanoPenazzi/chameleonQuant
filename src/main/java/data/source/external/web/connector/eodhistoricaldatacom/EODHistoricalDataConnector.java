/**
 * 
 */
package data.source.external.web.connector.eodhistoricaldatacom;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import data.source.external.web.connector.APIConnectorAbstract;
import data.source.external.web.parameter.APIParameter;
import data.source.external.web.parameter.APIParameterBuilderI;
import data.source.external.web.parameter.eodhistoricaldatacom.APIParameterBuilderEODHistoricalData;

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
	public EODHistoricalDataConnector(String apiKey, int timeOut) {
		super(apiKey,timeOut);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getBaseUrl() {
		
		return BASE_URL;
	}

	@Override
	public APIParameterBuilderI getAPIParameterBuilder() {
		return new APIParameterBuilderEODHistoricalData();
	}

	@Override
	public String getParameters(APIParameter... apiParameters) {
		Map<String,String> dict = new HashMap<String,String>();
		for (APIParameter parameter : apiParameters) {
		       dict.put(parameter.getKey(), parameter.getValue());
		    }
		APIParameterBuilderI urlBuilder = getAPIParameterBuilder();
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
