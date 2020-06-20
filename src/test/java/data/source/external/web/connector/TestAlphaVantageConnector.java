/**
 * 
 */
package data.source.external.web.connector;

import org.junit.jupiter.api.Test;

import data.source.external.web.parameter.alphaVantage.functions.Function;
import data.source.external.web.parameter.alphaVantage.output.OutputSize;
import data.source.external.web.parameter.alphaVantage.output.OutputType;
import data.source.external.web.parameter.alphaVantage.symbols.Symbol;

/**
 * @author stefanopenazzi
 *
 */
class TestAlphaVantageConnector {

	@Test
	void test() {
		AlphaVantageConnector avc = new AlphaVantageConnector("84AHX76LXVJ25F65",10000);
		String test = avc.call(Function.TIME_SERIES_DAILY,new Symbol("IBM"),OutputSize.FULL,OutputType.JSON);
		System.out.println();
		
	}

}
