/**
 * 
 */
package data.source.external.financialdatavendors.alphavantage;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import data.source.SourceI;
import data.source.external.financialdatavendors.alphavantage.mirrors.StockEODTimeSeriesPointAlphaVantage;
import data.source.external.financialdatavendors.alphavantage.parameters.functions.Function;
import data.source.external.financialdatavendors.alphavantage.parameters.output.OutputSize;
import data.source.external.financialdatavendors.alphavantage.parameters.output.OutputType;
import data.source.external.financialdatavendors.alphavantage.parameters.symbols.Symbol;
import data.source.internal.timeseries.TimeSeriesRequestI;
import data.source.internal.timeseries.TimeSeriesRequestIdI;
import data.source.internal.timeseries.Utils;
import data.source.internal.timeseries.point.TimeSeriesPointI;
import data.source.utils.IO.CSVUtils;

/**
 * @author stefanopenazzi
 *
 */
public class TimeSeriesRequestAlphaVantage implements TimeSeriesRequestI  {

	@Override
	public List<TimeSeriesPointI> getTimeSeries(TimeSeriesRequestIdI iq,SourceI source) {
		TimeSeriesRequestIdAlphaVantage tsrId = (TimeSeriesRequestIdAlphaVantage)iq;
		AlphaVantageConnector avc = new AlphaVantageConnector(60000);
		String apiRes = avc.call(tsrId.getExchange(),new Symbol(tsrId.getId()),OutputSize.FULL,OutputType.CSV);
		apiRes = apiRes.replaceFirst("timestamp", "time");
		List<Map<String,String>> apiResMap = null;
		try {
			apiResMap = CSVUtils.parseCsv2Map(apiRes, true,',','"');
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<TimeSeriesPointI> res = Utils.map2PointsList(apiResMap,iq.getTimeSeriesPoint());
		return res;
	}

	@Override
	public TimeSeriesPointI getLastPoint(TimeSeriesRequestIdI iqp,SourceI source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends TimeSeriesPointI> getTimeSeries(TimeSeriesRequestIdI iq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TimeSeriesPointI getLastPoint(TimeSeriesRequestIdI iqp) {
		// TODO Auto-generated method stub
		return null;
	}
}
