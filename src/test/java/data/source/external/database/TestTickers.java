/**
 * 
 */
package data.source.external.database;

import org.junit.jupiter.api.Test;
import data.source.external.database.tickers.TickersSetFactory;
import data.source.external.database.tickers.TickersSetI;

/**
 * @author stefanopenazzi
 *
 */
class TestTickers {

	@Test
	void test() {
		TickersSetFactory tf = new TickersSetFactory();
		TickersSetI ts = tf.createFromTxtFile("NASDAQ_EOD",System.getProperty("user.dir")+ "/src/main/resources/symbol_list/NASDAQ.txt",true,'\t','"',"Symbol");
		System.out.println();
	}
}
