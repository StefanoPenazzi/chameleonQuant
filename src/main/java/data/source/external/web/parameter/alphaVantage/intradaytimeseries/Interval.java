/**
 * 
 */
package data.source.external.web.parameter.alphaVantage.intradaytimeseries;

import data.source.external.web.parameter.APIParameter;

/**
 * @author stefanopenazzi
 *
 */
public enum Interval implements APIParameter {
	  ONE_MIN("1min"),
	  FIVE_MIN("5min"),
	  TEN_MIN("10min"),
	  FIFTEEN_MIN("15min"),
	  THIRTY_MIN("30min"),
	  SIXTY_MIN("60min");

	  private final String interval;

	  Interval(String interval) {
	    this.interval = interval;
	  }

	  @Override
	  public String getKey() {
	    return "interval";
	  }

	  @Override
	  public String getValue() {
	    return interval;
	  }
	}