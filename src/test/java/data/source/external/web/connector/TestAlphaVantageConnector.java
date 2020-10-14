/**
 * 
 */
package data.source.external.web.connector;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import data.source.external.web.parameter.alphaVantage.functions.Function;
import data.source.external.web.parameter.alphaVantage.output.OutputSize;
import data.source.external.web.parameter.alphaVantage.output.OutputType;
import data.source.external.web.parameter.alphaVantage.symbols.Symbol;
import data.source.utils.IO.TxtUtils;

/**
 * @author stefanopenazzi
 *
 */
class TestAlphaVantageConnector {

	@Test
	void test() {
		AlphaVantageConnector avc = new AlphaVantageConnector("84AHX76LXVJ25F65",10000);
		String test = avc.call(Function.TIME_SERIES_DAILY,new Symbol("IBM"),OutputSize.FULL,OutputType.JSON);
		try {
			TxtUtils.stringToFile(test,System.getProperty("user.dir")+"/output/test.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(System.getProperty("user.dir")+"/output");
		
	}

}
