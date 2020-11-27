/**
 * 
 */
package controller;

import java.util.Collections;
import java.util.List;


/**
 * @author stefanopenazzi
 *
 */
public final class Controller implements ControllerI {

	private com.google.inject.Injector injector;
	private boolean injectorCreated = false;
	
	private List<AbstractModule> modules = Collections.singletonList(new ControllerDefaultsModule());

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
