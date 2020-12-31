/**
 * 
 */
package data.source.external.web.parameter.alphaVantage;

import data.source.external.web.parameter.APIParameterBuilderAbstract;

/**
 * @author stefanopenazzi
 *
 */
public class APIParameterBuilderAlphaVantage extends APIParameterBuilderAbstract {

		@Override
		public String buildPair(String key, String value) {
			String parameter = "&" + key + "=" + value;
			return parameter;
		}
	    

}
