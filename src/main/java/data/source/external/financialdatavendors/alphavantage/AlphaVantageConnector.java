/**
 * 
 */
package data.source.external.financialdatavendors.alphavantage;


import data.source.external.financialdatavendors.alphavantage.parameters.APIParameterBuilderAlphaVantage;
import data.source.external.financialdatavendors.connector.APIConnectorAbstract;
import data.source.external.financialdatavendors.parameters.APIParameters;
import data.source.external.financialdatavendors.parameters.APIParametersBuilderI;

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
	
	public AlphaVantageConnector(int timeOut) {
		super("AlphaVantage",timeOut);
	}

	@Override
	public String getBaseUrl() {
		return BASE_URL;
	}

	@Override
	public APIParametersBuilderI getAPIParameterBuilder() {
		return new APIParameterBuilderAlphaVantage();
	}

	@Override
	public String getParameters(APIParameters... apiParameters) {
		APIParametersBuilderI urlBuilder = getAPIParameterBuilder();
	     for (APIParameters parameter : apiParameters) {
	       urlBuilder.append(parameter);
	     }
	     urlBuilder.append(apiKeyId,this.apiKey);
	     
	     return urlBuilder.getUrl();
	}

}
