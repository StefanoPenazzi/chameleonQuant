/**
 * 
 */
package data.source.external.web.connector;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import data.source.external.web.connector.eodhistoricaldatacom.EODHistoricalDataConnector;
import data.source.external.web.parameter.eodhistoricaldatacom.securities.Security;
import data.source.external.web.parameter.eodhistoricaldatacom.symbols.Symbol;
import data.source.utils.IO.TxtUtils;

/**
 * @author stefanopenazzi
 *
 */
class TestEODHistoricalData {

	@Test
	void testEODExchanges() throws FileNotFoundException {
		EODHistoricalDataConnector avc = new EODHistoricalDataConnector(10000);
		String test = avc.call(new Security("eod"), new Symbol("AAPL.US"));
		try {
			TxtUtils.stringToFile(test,System.getProperty("user.dir")+"/output/eod_AAPL_US.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
