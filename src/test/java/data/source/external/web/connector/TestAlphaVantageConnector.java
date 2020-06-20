/**
 * 
 */
package data.source.external.web.connector;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import data.source.external.web.parameter.alphaVantage.functions.Function;
import data.source.external.web.parameter.alphaVantage.symbols.Symbol;

/**
 * @author stefanopenazzi
 *
 */
class TestAlphaVantageConnector {

	@Test
	void test() {
		AlphaVantageConnector avc = new AlphaVantageConnector("84AHX76LXVJ25F65",10000);
		avc.call(Function.TIME_SERIES_DAILY,new Symbol("IBM"));
		
	}

}
