/**
 * 
 */
package controller;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Binder;
import com.google.inject.Binding;
import com.google.inject.Guice;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.internal.BindingImpl;
import com.google.inject.spi.LinkedKeyBinding;
import com.google.inject.util.Modules;

/**
 * @author stefanopenazzi
 *
 */
public final class Injector {
	
	private static Logger logger = LogManager.getLogger(Injector.class);
	
	
	private Injector(){}
	
	public static com.google.inject.Injector createInjector(Module... modules) {
		//com.google.inject.Injector injector = Guice.createInjector(new Module() {
		//	@Override
		//	public void configure(Binder binder) {
		//		binder.requireExplicitBindings();
		//	}
		//});
		List<com.google.inject.Module> guiceModules = new ArrayList<>();
		for (Module module : modules) {
			//injector.injectMembers(module);
			guiceModules.add(module);
		}
		com.google.inject.Injector injector = Guice.createInjector(guiceModules);
		//com.google.inject.Injector rInjector = injector.createChildInjector(Modules.combine(guiceModules));
		//printInjector(rInjector, logger);
		return injector;
	}
	
	public static void printInjector(com.google.inject.Injector injector, Logger log) {
		Level level = Level.INFO ;
		log.log(level,"=== printInjector start ===") ;
		for (Map.Entry<Key<?>, Binding<?>> entry : injector.getBindings().entrySet()) {
			if ( entry.getKey().toString().contains("type=org.matsim") ) {
				Annotation annotation = entry.getKey().getAnnotation();
				log.log( level, entry.getKey().getTypeLiteral() + " " + (annotation != null ? annotation.toString() : ""));
				log.log(level, "  --> provider: " + entry.getValue().getProvider());
				log.log(level, "  --> source: " + entry.getValue().getSource() );
				if ( entry.getValue() instanceof BindingImpl ) {
					log.log( level, "  --> scope: " + ((BindingImpl<?>)entry.getValue()).getScoping() ) ;
				}
				if ( entry.getValue() instanceof LinkedKeyBinding) {
					log.log( level, "  --> target: " + ((LinkedKeyBinding) entry.getValue()).getLinkedKey() ) ;
				}
				log.log(level, "  ==full==> " + entry.getValue() );
				// yy could probably format the above in a better way. kai, may'16
				log.log(level,  "" );
			}
		}
		log.log(level,"=== printInjector end ===") ;
	}
	
	/*
	 * private static Module insertMapBindings(List<Module> guiceModules) { return
	 * Modules.combine(Modules.combine(guiceModules)); }
	 */

}
