/**
 * 
 */
package data.source.external.financialdatavendors.connector;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import data.source.external.financialdatavendors.alphavantage.AlphaVantageExceptions;
import data.source.external.financialdatavendors.parameters.APIParameters;
import data.source.external.financialdatavendors.parameters.APIParametersBuilderI;

/**
 * @author stefanopenazzi
 *
 */
public abstract class APIConnectorAbstract implements APIConnector {
	 
	  private final int timeOut;
	  protected final String apiKey;

	  /**
	   * Creates an AlphaVantageConnector.
	   *
	   * @param apiKey the secret key to access the api.
	   * @param timeOut the timeout for when reading the connection should give up.
	 * @throws FileNotFoundException 
	   */
	  public APIConnectorAbstract(String apiKey,int timeOut) {
		  
		  Properties properties = new Properties();
		  InputStream inputStream = getClass().getClassLoader().getResourceAsStream("apiKeys.properties");
			
			try {
				properties.load(inputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		  
		  this.timeOut = timeOut;
		  this.apiKey = properties.getProperty(apiKey);
	  }
	  
	  public abstract String getBaseUrl();
	  public abstract APIParametersBuilderI getAPIParameterBuilder();
	  public abstract String getParameters(APIParameters... apiParameters);


	  //TODO it is better using a builder pattern not a list of APIParams
	public String call(APIParameters... apiParameters) {
		String params = getParameters(apiParameters);
	    try {
	      URL request = new URL(getBaseUrl() + params);
	      URLConnection connection = request.openConnection();
	      connection.setConnectTimeout(timeOut);
	      connection.setReadTimeout(timeOut);

	      InputStreamReader inputStream = new InputStreamReader(connection.getInputStream(), "UTF-8");
	      BufferedReader bufferedReader = new BufferedReader(inputStream);
	      StringBuilder responseBuilder = new StringBuilder();

	      String line;
	      while ((line = bufferedReader.readLine()) != null) {
	    	  //TODO /n is necessary only for csv files
	        responseBuilder.append(line+"\n");
	        //responseBuilder.append("");
	      }
	      bufferedReader.close();
	      return responseBuilder.toString();
	    } catch (IOException e) {
	    	//TODO exception based on provider
	         throw new AlphaVantageExceptions("failure sending request",e);
	    }
	}
	}
