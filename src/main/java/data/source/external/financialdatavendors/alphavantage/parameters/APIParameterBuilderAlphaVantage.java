/**
 * 
 */
package data.source.external.financialdatavendors.alphavantage.parameters;

import data.source.external.financialdatavendors.parameters.APIParametersBuilderAbstract;

/**
 * @author stefanopenazzi
 *
 */
public class APIParameterBuilderAlphaVantage extends APIParametersBuilderAbstract {

		@Override
		public String buildPair(String key, String value) {
			String parameter = "&" + key + "=" + value;
			return parameter;
		}
	    

}
