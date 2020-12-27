/**
 * 
 */
package data.source.internal.dataset.timeseries;

import java.util.function.Supplier;

import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPointAbstract;


/**
 * @author stefanopenazzi
 *
 */
public abstract class InternalTimeSeriesQueryRequestAbstract<T extends InternalTimeSeriesPointAbstract> implements InternalTimeSeriesQueryRequestI<T> {
	
	 private final T genericTypeObject;

	  public InternalTimeSeriesQueryRequestAbstract(Supplier<T> supplier) {
	    genericTypeObject = supplier.get();
	  }

}
