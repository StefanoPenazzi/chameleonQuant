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
public abstract class InternalTimeSeriesIdAbstract implements InternalTimeSeriesIdI {

		@Override
		public int hashCode() {
			//TODO get the has code of the query
			String sd = getStartInstant() == null ? "0" : getStartInstant().toString();
			String ed = getEndInstant() == null ? "0" : getEndInstant().toString();
			return Objects.hash(getId(),getInterval(),sd,ed);
		}
		
		@Override
		public boolean equals(Object o) {
			if(o == this) {
				return true;
			}
			if(!(o instanceof InternalTimeSeriesIdAbstract) ) {
				return false;
			}
			InternalTimeSeriesIdAbstract ob = (InternalTimeSeriesIdAbstract)o;
			
			String sd = getStartInstant() == null ? "0" : getStartInstant().toString();
			String ed = getEndInstant() == null ? "0" : getEndInstant().toString();
			String osd = ob.getStartInstant() == null ? "0" : ob.getStartInstant().toString();
			String oed = ob.getEndInstant() == null ? "0" : ob.getEndInstant().toString();
			
			if(getId().equals(ob.getId()) && sd.equals(osd) && ed.equals(oed) && getInterval().equals(ob.getInterval())) {
				return true;
			}
			return false;
		}
		
		@Override
		public String getString() {
			//String s = "id: " + this.getId() + " start date:"+ this.getStartDate().toString() + " end date:"+ this.getEndDate().toString() + " interval:"+ this.getInterval();
			String s = "id: " + this.getId();
			return s;
		}
}
