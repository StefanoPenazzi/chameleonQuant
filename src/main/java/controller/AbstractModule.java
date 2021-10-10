/**
 * 
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.util.Modules;
import data.source.internal.dataset.DatasetI;
import data.source.internal.timeseries.TimeSeriesRequestI;
import data.source.internal.timeseries.cleaning.TimeSeriesCleanerI;

/**
 * @author stefanopenazzi
 *
 */
public abstract class AbstractModule implements Module {

	private Binder binder;
	
	private MapBinder<String,TimeSeriesCleanerI> mapbinderTimeSeriesCleaner;
	private MapBinder<String,TimeSeriesRequestI> mapbinderTimeSeriesRequest;
	
	//@Inject
	//com.google.inject.Injector injector;
	
	public AbstractModule() {
		
	}
	
	@Override
	public void configure(Binder binder) {
		// TODO Auto-generated method stub
		this.binder = binder.skipSources(AbstractModule.class);
		
		mapbinderTimeSeriesCleaner
	    = MapBinder.newMapBinder(this.binder, new TypeLiteral<String>(){}, new TypeLiteral<TimeSeriesCleanerI>(){});
		
		mapbinderTimeSeriesRequest
	    = MapBinder.newMapBinder(this.binder, new TypeLiteral<String>(){},new TypeLiteral<TimeSeriesRequestI>(){} );
		
		this.install();
		System.out.println();
	}
	
	public abstract void install();
	
	protected final void install(Module module) {
		//injector.injectMembers(module);
		binder.install(module);
	}
	
	protected final LinkedBindingBuilder<TimeSeriesCleanerI> addInternalTimeSeriesCleaner(final String name ) {
		return mapbinderTimeSeriesCleaner.addBinding(name);
	}
	protected final LinkedBindingBuilder<TimeSeriesRequestI> addTimeSeriesRequest(final String name ) {
		return mapbinderTimeSeriesRequest.addBinding(name);
	}
	
	protected final LinkedBindingBuilder<DatasetI> bindDataset() {
		return bind(DatasetI.class);
	}
	
	protected <T> AnnotatedBindingBuilder<T> bind(Class<T> aClass) {
		return binder.bind(aClass);
	}
	
	protected final Binder binder() {
		return binder;
	}
	
	public static AbstractModule override(final Iterable<? extends AbstractModule> modules, final AbstractModule abstractModule) {
		return new AbstractModule() {
			@Override
			public void install() {
				final List<com.google.inject.Module> guiceModules = new ArrayList<>();
				for (AbstractModule module : modules) {
					//this.injector.injectMembers(module);
					guiceModules.add(module);
				}
				//this.injector.injectMembers(abstractModule);
				binder().install(Modules.override(guiceModules).with(abstractModule));
			}
		};
	}
	
	public static AbstractModule emptyModule() {
		return new AbstractModule() {
			@Override
			public void install() {}
		};
	}
	
}
