package data.source.external.web.connector;

import org.junit.jupiter.api.Test;

import data.source.external.financialdatavendors.alpaca.AlpacaConnector;

class TestAlpacaConnector {

	@Test
	void test() {
		AlpacaConnector ac =(AlpacaConnector) new AlpacaConnector.Builder()
				.setUrl(AlpacaConnector.url.HISTORICAL_DATA)
				.setQuery(AlpacaConnector.query.STOCKS)
				.setHistData(AlpacaConnector.histData.BARS)
				.setSymbol("GOOGL")
				.addParameters("start","2019-10-12T07:20:50.52Z")
				.addParameters("end","2020-10-12T07:20:50.52Z")
				.addParameters("timeframe","1Day")
				.build();
		
		String a = ac.get();
		System.out.println(a);
	}

}
