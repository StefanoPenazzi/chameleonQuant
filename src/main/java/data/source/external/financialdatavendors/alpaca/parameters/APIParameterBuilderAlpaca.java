package data.source.external.financialdatavendors.alpaca.parameters;

import data.source.external.financialdatavendors.parameters.APIParametersBuilderAbstract;

public class APIParameterBuilderAlpaca extends APIParametersBuilderAbstract {

	@Override
	public String buildPair(String key, String value) {
		String parameter = "&" + key + "=" + value;
		return parameter;
	}
    

}

