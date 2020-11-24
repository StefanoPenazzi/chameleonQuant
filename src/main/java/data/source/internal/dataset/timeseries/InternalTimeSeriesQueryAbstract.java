/**
 * 
 */
package data.source.internal.dataset.timeseries;

import java.util.Objects;

/**
 * @author stefanopenazzi
 *
 */
public abstract class InternalTimeSeriesQueryAbstract implements InternalTimeSeriesQueryI {

	//better using reflection even here
		@Override
		public int hashCode() {
			//TODO get the has code of the query
			return Objects.hash(getId(),getInterval(),getStartDate(),getEndDate());
		}
}
