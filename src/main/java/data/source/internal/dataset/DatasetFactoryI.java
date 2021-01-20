/**
 * 
 */
package data.source.internal.dataset;

import java.util.List;

import data.source.internal.timeseries.TimeSeriesRequestIdI;

/**
 * @author stefanopenazzi
 *
 */
public interface DatasetFactoryI {
	
	public DatasetI create(List< TimeSeriesRequestIdI> listOfId);

}
