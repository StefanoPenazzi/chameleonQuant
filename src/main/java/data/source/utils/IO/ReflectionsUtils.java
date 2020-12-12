/**
 * 
 */
package data.source.utils.IO;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import data.source.annotation.InternalQueryAnnotation.InternalQueryInfo;
import data.source.annotation.InternalTimeSeries.TagName;

/**
 * @author stefanopenazzi
 *
 */
public class ReflectionsUtils {
	
	// all the reflections utils should be in the same package not like this
		public static Method getMethodsAnnotatedWith(final Class<?> type, final Class<? extends Annotation> annotation, String name) throws Exception {
		    final List<Method> methods = new ArrayList<Method>();
		    Class<?> klass = type;
		    while (klass != Object.class) { 
		        for (final Method method : klass.getDeclaredMethods()) {
		            if (method.isAnnotationPresent(annotation)) {
		                Annotation annotInstance = method.getAnnotation(annotation);
		                if(annotInstance.annotationType().equals(InternalQueryInfo.class) ) {
		                	InternalQueryInfo aInstance = (InternalQueryInfo)annotInstance;
		                	if(aInstance.name().equals(name)) {
		                		methods.add(method);
		                	}
		                }
		            }
		        }
		        // move to the upper class in the hierarchy in search for more methods
		        klass = klass.getSuperclass();
		    }
		    if(methods.size() != 1) {
		    	throw new Exception(); 
		    }
		    return methods.get(0);
		}
		
		
		// all the reflections utils should be in the same package not like this
				public static Method getMethodsAnnotatedWithTag(final Class<?> type, final Class<? extends Annotation> annotation, String name) throws Exception {
				    final List<Method> methods = new ArrayList<Method>();
				    Class<?> klass = type;
				    while (klass != Object.class) { 
				        for (final Method method : klass.getDeclaredMethods()) {
				            if (method.isAnnotationPresent(annotation)) {
				                Annotation annotInstance = method.getAnnotation(annotation);
				                if(annotInstance.annotationType().equals(TagName.class) ) {
				                	TagName aInstance = (TagName)annotInstance;
				                	if(aInstance.name().equals(name)) {
				                		methods.add(method);
				                	}
				                }
				            }
				        }
				        // move to the upper class in the hierarchy in search for more methods
				        klass = klass.getSuperclass();
				    }
				    if(methods.size() != 1) {
				    	throw new Exception(); 
				    }
				    return methods.get(0);
				}

}
