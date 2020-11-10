/**
 * 
 */
package data.source.external.web.connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import data.source.external.web.exception.AlphaVantageException;
import data.source.external.web.parameter.APIParameter;
import data.source.external.web.parameter.APIParameterBuilder;

/**
 * @author stefanopenazzi
 *
 */
public class AlphaVantageConnector implements APIConnector {
	  private static final String BASE_URL = "https://www.alphavantage.co/query?";
	  private final String apiKey;
	  private final int timeOut;

	  /**
	   * Creates an AlphaVantageConnector.
	   *
	   * @param apiKey the secret key to access the api.
	   * @param timeOut the timeout for when reading the connection should give up.
	   */
	  public AlphaVantageConnector(String apiKey, int timeOut) {
	    this.apiKey = apiKey;
	    this.timeOut = timeOut;
	  }

	 

	  /**
	   * Builds up the url query from the api parameters used to append to the base url.
	   *
	   * @param apiParameters the api parameters used in the query
	   * @return the query string to use in the url
	   */
	  private String getParameters(APIParameter... apiParameters) {
	    APIParameterBuilder urlBuilder = new APIParameterBuilder();
	    for (APIParameter parameter : apiParameters) {
	      urlBuilder.append(parameter);
	    }
	    urlBuilder.append("apikey", apiKey);
	    return urlBuilder.getUrl();
	  }



	public String call(APIParameter... apiParameters) {
		String params = getParameters(apiParameters);
	    try {
	      URL request = new URL(BASE_URL + params);
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
	         throw new AlphaVantageException("failure sending request",e);
	    }
	}
	}
