/**
 * 
 */
package data.source.internal.dataset;

import java.util.List;

import data.source.external.database.influxdb.TimeSeriesId;

/**
 * @author stefanopenazzi
 *
 */
public interface DatasetFactoryI {
	
	public DatasetI create(List<TimeSeriesId> listOfId);

}
