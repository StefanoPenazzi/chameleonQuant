/**
 * 
 */
package data.source.external.web.connector;

import data.source.external.web.parameter.alphaVantage.APIParameterBuilderAlphaVantage;
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
	public AlphaVantageConnector(String apiKey, int timeOut) {
		super(apiKey, timeOut);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getBaseUrl() {
		String BASE_URL = "https://www.alphavantage.co/query?";
		return BASE_URL;
	}

	@Override
	public APIParameterBuilderI getAPIParameterBuilder() {
		return new APIParameterBuilderAlphaVantage();
	}
}
