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

	private static com.google.inject.Injector injector;
	//private boolean injectorCreated = false;
	
	private static List<AbstractModule> modules = Collections.singletonList(new ControllerDefaultsModule());
	
	private  static AbstractModule overrides = AbstractModule.emptyModule();

	
	public static void run() {
		injector = Injector.createInjector(AbstractModule.override(Collections.singleton(new AbstractModule() {
			@Override
			public void install() {
				for (AbstractModule module : modules) {
					install(module);
				}
			}
		}), overrides));
	}
	
	public static com.google.inject.Injector getInjector(){
		return injector;
	}
	
	public class BasicModule extends com.google.inject.AbstractModule {
		 
		@Override
	    protected void configure() {	  
			  MapBinder<String,TimeSeriesCleanerI<? extends InternalTimeSeriesPointI>> mapbinderTimeSeriesCleaner = MapBinder.newMapBinder(binder(), new TypeLiteral<String>(){}, new TypeLiteral<TimeSeriesCleanerI<? extends InternalTimeSeriesPointI>>(){});
			  
			  mapbinderTimeSeriesCleaner.addBinding("NULL_INFLUXDB").to((Class<? extends TimeSeriesCleanerI<? extends InternalTimeSeriesPointI>>) TimeSeriesCleanerNullValuesStockInfluxdb.class);
			 
	    }
	}

}
