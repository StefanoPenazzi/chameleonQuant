/**
 * 
 */
package data.source.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author stefanopenazzi
 *
 */
public interface InternalQueryAnnotation {
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface InternalQueryInfo {
		String name() default "";
	}

}
