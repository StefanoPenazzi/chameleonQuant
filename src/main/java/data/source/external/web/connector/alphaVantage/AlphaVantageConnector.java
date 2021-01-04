/**
 * 
 */
package data.source.external.web.connector.alphaVantage;

import data.source.external.web.parameter.alphaVantage.APIParameterBuilderAlphaVantage;

import java.io.FileNotFoundException;

import data.source.external.web.connector.APIConnectorAbstract;
import data.source.external.web.parameter.APIParameter;
import data.source.external.web.parameter.APIParameterBuilderI;

/**
 * @author stefanopenazzi
 *
 */
public class AlphaVantageConnector extends APIConnectorAbstract {

	/**
	 * @param apiKey
	 * @param timeOut
	 */
	
	private String BASE_URL = "https://www.alphavantage.co/query?";
	private String apiKeyId = "apikey";
	
	public AlphaVantageConnector(String apiKey,int timeOut) {
		super(apiKey,timeOut);
	}

	@Override
	public String getBaseUrl() {
		return BASE_URL;
	}

	@Override
	public APIParameterBuilderI getAPIParameterBuilder() {
		return new APIParameterBuilderAlphaVantage();
	}

	@Override
	public String getParameters(APIParameter... apiParameters) {
		APIParameterBuilderI urlBuilder = getAPIParameterBuilder();
	     for (APIParameter parameter : apiParameters) {
	       urlBuilder.append(parameter);
	     }
	     urlBuilder.append(apiKeyId,this.apiKey);
	     
	     return urlBuilder.getUrl();
	}
}
