package data.source.external.httpRequest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public abstract class APIConnectorAbstract implements APIConnector {
	
	private String url;
	private Map<String, String> parameters = new HashMap<>(); 
	private Map<String, String> headers = new HashMap<>(); 
	private Map<String, String> body = new HashMap<>(); 
	
	protected static abstract class Builder {
			
		protected Map<String, String> parameters = new HashMap<>();
		protected Map<String, String> headers = new HashMap<>(); 
		protected Map<String, String> body = new HashMap<>(); 
		protected String url;
			
		public Builder(String url) {
	        this.url = url;
	    }
		
		public Builder addParameters(String key, String value){
            this.parameters.put(key, value);
            return this;
        }
		
		public Builder addHeader(String key, String value){
            this.headers.put(key, value);
            return this;
        }
		
		public Builder addBodyElement(String key, String value){
            this.body.put(key, value);
            return this;
        }
		
		public abstract APIConnectorAbstract build();
					
		}
	  
	public APIConnectorAbstract(String url, Map<String, String> parameters,Map<String, String> headers,Map<String, String> body) {
		
		this.url = url;
		this.parameters = parameters; 
		this.headers = headers;
		this.body = body;
	}

	@Override
	public String get() {
		String params;
		try {
			 params = ParametesStringBuilder(this.parameters);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    URL request = null;
		try {
			request = new URL(this.url+ParametesStringBuilder(this.parameters));
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection)request.openConnection();
			connection.setRequestMethod("GET");
		    connection.setDoOutput(true);
		    connection.setConnectTimeout(5000);
		    connection.setReadTimeout(5000);
		    for(Map.Entry<String, String> entry : headers.entrySet()) {
	    	  connection.setRequestProperty(entry.getKey(), entry.getValue());
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
	        
		
		
	    BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	String inputLine;
    	StringBuffer content = new StringBuffer();
    	try {
			while ((inputLine = in.readLine()) != null) {
				    content.append(inputLine);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return content.toString();
	   
	}
	
	protected void updateUrl(String s) {
		this.url = this.url+ "/" + s;
	}
	
	public String ParametesStringBuilder(Map<String, String> params) throws UnsupportedEncodingException {
		
		if(params.isEmpty()) return "";
		
		StringBuilder result = new StringBuilder();
		result.append("?");	
		for (Map.Entry<String, String> entry : params.entrySet()) {
		    result.append(ParameteStringBuilder(entry.getKey(),entry.getValue()));  
		}
		String resultString = result.toString();
		return resultString.length() > 0
		    ? resultString.substring(0, resultString.length() - 1)
		    : resultString;
	}
	
	public String ParameteStringBuilder(String key, String value) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
			
		result.append(URLEncoder.encode(key, "UTF-8"));
	    result.append("=");
	    result.append(URLEncoder.encode(value, "UTF-8"));
	    result.append("&");
			
	    String resultString = result.toString();
	    return resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1): resultString;
	}
	
}
