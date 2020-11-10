/**
 * 
 */
package data.source.external.web.parameter.alphaVantage.intradaytimeseries;

import data.source.external.web.parameter.APIParameter;

/**
 * @author stefanopenazzi
 *
 */
public enum Slice implements APIParameter  {

	YEAR1MONTH1("year1month1"),
	YEAR1MONTH2("year1month2"),
	YEAR1MONTH3("year1month3"),
	YEAR1MONTH4("year1month4"),
	YEAR1MONTH5("year1month5"),
	YEAR1MONTH6("year1month6"),
	YEAR1MONTH7("year1month7"),
	YEAR1MONTH8("year1month8"),
	YEAR1MONTH9("year1month9"),
	YEAR1MONTH10("year1month10"),
	YEAR1MONTH11("year1month11"),
	YEAR1MONTH12("year1month12"),
	YEAR2MONTH1("year2month1"),
	YEAR2MONTH2("year2month2"),
	YEAR2MONTH3("year2month3"),
	YEAR2MONTH4("year2month4"),
	YEAR2MONTH5("year2month5"),
	YEAR2MONTH6("year2month6"),
	YEAR2MONTH7("year2month7"),
	YEAR2MONTH8("year2month8"),
	YEAR2MONTH9("year2month9"),
	YEAR2MONTH10("year2month10"),
	YEAR2MONTH11("year2month11"),
	YEAR2MONTH12("year2month12");
	
	private final String slice;

	 Slice(String slice) {
	    this.slice = slice;
	}
	
	@Override
	public String getKey() {
		return "slice";
	}

	@Override
	public String getValue() {
		return this.slice;
	}

}
