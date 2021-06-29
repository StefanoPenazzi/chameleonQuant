package data.source.external.web.connector;

import org.junit.jupiter.api.Test;

import data.source.external.financialdatavendors.alpaca.AlpacaConnector;

class TestAlpacaConnector {

	@Test
	void test() {
		AlpacaConnector ac =new AlpacaConnector.Builder()
				.setQuery(AlpacaConnector.query.STOCKS)
				.setHistData(AlpacaConnector.histData.QUOTES_LATEST)
				.setSymbol("GOOGL")
				.build();
		
		String a = ac.get();
		System.out.println(a);
	}

}
