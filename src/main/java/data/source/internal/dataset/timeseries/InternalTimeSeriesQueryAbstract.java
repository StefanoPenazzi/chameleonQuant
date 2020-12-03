/**
 * 
 */
package data.source.internal.dataset.timeseries;

import java.util.Date;
import java.util.Objects;

/**
 * @author stefanopenazzi
 *
 */
public abstract class InternalTimeSeriesQueryAbstract implements InternalTimeSeriesQueryI {

		@Override
		public int hashCode() {
			//TODO get the has code of the query
			String sd = getStartDate() == null ? "0" : getStartDate().toString();
			String ed = getEndDate() == null ? "0" : getEndDate().toString();
			return Objects.hash(getId(),getInterval(),sd,ed);
		}
		
		@Override
		public boolean equals(Object o) {
			if(o == this) {
				return true;
			}
			if(!(o instanceof InternalTimeSeriesQueryAbstract) ) {
				return false;
			}
			InternalTimeSeriesQueryAbstract ob = (InternalTimeSeriesQueryAbstract)o;
			
			String sd = getStartDate() == null ? "0" : getStartDate().toString();
			String ed = getEndDate() == null ? "0" : getEndDate().toString();
			String osd = ob.getStartDate() == null ? "0" : ob.getStartDate().toString();
			String oed = ob.getEndDate() == null ? "0" : ob.getEndDate().toString();
			
			if(getId().equals(ob.getId()) && sd.equals(osd) && ed.equals(oed) && getInterval().equals(ob.getInterval())) {
				return true;
			}
			return false;
		}
}
