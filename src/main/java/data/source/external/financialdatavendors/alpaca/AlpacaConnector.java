package data.source.external.financialdatavendors.alpaca;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import data.source.external.httpRequest.APIConnectorAbstract;
import data.source.utils.IO.PropertiesUtils;


public class AlpacaConnector extends APIConnectorAbstract {
	
	public enum url{
		HISTORICAL_DATA,
		REALTIME_DATA,
		PAPER_API
	}
	
	public enum query{
		ORDERS,
		ACCOUNT,
		ASSETS,
		POSITIONS,
	}
	
	public enum histData{
		QUOTES,
		QUOTES_LATEST,
		BARS,
		TRADES,
		TRADES_LATEST,
		SNAPSHOTS,
		SNAPSHOT
	}
	
	public static final class Builder extends APIConnectorAbstract.Builder{
		
		private String qy = null;
		private List<String> symbols = new ArrayList<>();
		private String hd = null;
		private String url = null;
		

		public Builder() {
			super();
			this.addHeader("APCA-API-KEY-ID",PropertiesUtils.getPropertyValue("APCA-API-KEY-ID"));
			this.addHeader("APCA-API-SECRET-KEY",PropertiesUtils.getPropertyValue("APCA-API-SECRET-KEY"));
		}
		
		public Builder setUrl(url ur) {
			switch(ur){
			case HISTORICAL_DATA:
				url = "https://data.alpaca.markets/v2/stocks";
				break;
			case REALTIME_DATA:
				url = "wss://stream.data.alpaca.markets/v2";
				break;
			case PAPER_API:
				url = "https://paper-api.alpaca.markets/v2";
				break;
			}
			
			return this;
		}
		
		public Builder setQuery(query q) {
			switch(q){
			case ORDERS:
				qy = "orders";
				break;
			case ACCOUNT:
				qy = "account";
				break;
			}
			
			return this;
		}
		
		public Builder setSymbols(List<String> symbols) {
			this.symbols = symbols;
			return this;
		}
		public Builder setSymbol(String symbol) {
			this.symbols.add(symbol);
			return this;
		}
		
		public Builder setHistData(histData hd_) {
			
			switch(hd_){
			case QUOTES:
				hd = "quotes";
				break;
			case QUOTES_LATEST:
				hd = "quotes/latest";
				break;
			case BARS:
				hd = "bars";
				break;
			}
			
			return this;
		}

		@Override
		public AlpacaConnector build() {
			this.url = this.url+"/"+this.symbols.get(0)+"/"+this.hd;
			return new AlpacaConnector(this.url,this.parameters,this.headers,this.body);
		}
		
	}
	

	public AlpacaConnector(String url, Map<String, String> parameters, Map<String, String> headers,
			Map<String, String> body) {
		super(url, parameters, headers, body);
	}
	

	@Override
	public Boolean post() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private String getUrlHistoricalData() {
		
		return "";
	}

}