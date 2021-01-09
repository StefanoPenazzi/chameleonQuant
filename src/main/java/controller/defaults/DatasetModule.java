/**
 * 
 */
package controller.defaults;

import controller.AbstractModule;
import data.source.internal.dataset.DatasetFactoryImpl;

/**
 * @author stefanopenazzi
 *
 */
public class DatasetModule extends AbstractModule {

	@Override
	public void install() {
		bindDatasetFactory().to(DatasetFactoryImpl.class);
	}
}
