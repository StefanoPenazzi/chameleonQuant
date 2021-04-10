/**
 * 
 */
package or.solver;

import or.model.ModelI;

/**
 * @author stefanopenazzi
 *
 */
public interface SolverI<T extends ModelI> {
	
	public T getModel();
	public void run();

}
