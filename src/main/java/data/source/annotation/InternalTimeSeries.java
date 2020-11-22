/**
 * 
 */
package data.source.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author stefanopenazzi
 *
 */
public interface InternalTimeSeries {

	@Inherited
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface TimeSeriesFromDB {
		public String database() default "";
		public String query() default "";
	}
	
	@Inherited
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface TimeSeriesFromLocalCSV {
		public String directory() default "";
	}
	
	@Inherited
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface TimeSeriesFromWebAPI {
		public String url() default "";
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface Function {
	  String name() default "";
	}

}
