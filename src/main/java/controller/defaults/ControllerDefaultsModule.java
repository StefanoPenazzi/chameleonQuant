/**
 * 
 */
package controller.defaults;

import com.google.inject.Binder;

import controller.AbstractModule;

/**
 * @author stefanopenazzi
 *
 */
public class ControllerDefaultsModule extends AbstractModule {

	@Override
	public void install() {
		install(new InternalTimeSeriesCleaningModule());
		
	}

	

}
