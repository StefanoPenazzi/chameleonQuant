/**
 * 
 */
package controller;

import java.util.Collections;
import java.util.List;

import com.google.inject.Guice;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;

import controller.defaults.ControllerDefaultsModule;
import data.source.external.database.influxdb.TimeSeriesCleanerNullValuesStockInfluxdb;
import data.source.internal.dataset.timeseries.cleaning.TimeSeriesCleanerI;
import data.source.internal.dataset.timeseries.point.InternalTimeSeriesPointI;


/**
 * @author stefanopenazzi
 *
 */
public final class Controller implements ControllerI {

	private com.google.inject.Injector injector;
	//private boolean injectorCreated = false;
	
	private List<AbstractModule> modules = Collections.singletonList(new ControllerDefaultsModule());
	
	private AbstractModule overrides = AbstractModule.emptyModule();

	
	@Override
	public void run() {
		injector = Injector.createInjector(AbstractModule.override(Collections.singleton(new AbstractModule() {
			@Override
			public void install() {
				for (AbstractModule module : modules) {
					install(module);
				}
			}
		}), overrides));
	}
	
	public com.google.inject.Injector getInjector(){
		return this.injector;
	}
	
	public class BasicModule extends com.google.inject.AbstractModule {
		 
		@Override
	    protected void configure() {	  
			  MapBinder<String,TimeSeriesCleanerI<? extends InternalTimeSeriesPointI>> mapbinderTimeSeriesCleaner = MapBinder.newMapBinder(binder(), new TypeLiteral<String>(){}, new TypeLiteral<TimeSeriesCleanerI<? extends InternalTimeSeriesPointI>>(){});
			  
			  mapbinderTimeSeriesCleaner.addBinding("NULL_INFLUXDB").to((Class<? extends TimeSeriesCleanerI<? extends InternalTimeSeriesPointI>>) TimeSeriesCleanerNullValuesStockInfluxdb.class);
			 
	    }
	}

}
