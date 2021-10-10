/**
 * 
 */
package controller.defaults;

import controller.AbstractModule;
import data.source.internal.dataset.DatasetImpl;

/**
 * @author stefanopenazzi
 *
 */
public class DatasetModule extends AbstractModule {
	@Override
	public void install() {
		bindDataset().to(DatasetImpl.class);
	}
}
