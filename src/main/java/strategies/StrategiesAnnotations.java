/**
 * 
 */
package strategies;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author stefanopenazzi
 *
 */
public interface StrategiesAnnotations {
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface OutputStrategyReport {
		String name() default "";
		String section() default "";
		int position() default 0;
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface InputStrategyReport {
		String name() default "";
		String section() default "";
		int position() default 0;
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface StrategyVariable {
		String name() default "";
	}

}
