package data.source.external.web.connector;

import org.junit.jupiter.api.Test;

import data.source.external.financialdatavendors.alpaca.AlpacaConnector;

class TestAlpacaConnector {

	@Test
	void test() {
		AlpacaConnector ac =(AlpacaConnector) new AlpacaConnector.Builder()
				.setUrl(AlpacaConnector.url.HISTORICAL_DATA)
				.setHistData(AlpacaConnector.histData.BARS)
				.setSymbol("GOOGL")
				.addParameter("start","2019-10-12T07:20:50.52Z")
				.addParameter("end","2020-10-12T07:20:50.52Z")
				.addParameter("timeframe","1Day")
				.build();
		
		String a = ac.get();
		System.out.println(a);
	}
}
