/**
 * 
 */
package data.source.internal.timeseries.point;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import data.source.annotation.InternalTimeSeries.TagName;
import data.source.utils.IO.ReflectionsUtils;

/**
 * @author stefanopenazzi
 *
 */
public abstract class TimeSeriesPointAbstract implements TimeSeriesPointI  {
	
	@Override
	public Method getTagMethod(String tagName){
		Method method = null;
		try {
			method = ReflectionsUtils.getMethodsAnnotatedWithTag(this.getClass(),TagName.class,tagName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return method;
	}
	
	@Override
	public Object getTagValue(String tagName) {
		Object res = null;
		try {
			res = getTagMethod(tagName).invoke(this);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	@Override
	public String getString() {
		String s = "";
		Map<String,Object> pointTagsMap = null;
		try {
			pointTagsMap = ReflectionsUtils.getPointTagsAsMap(this.getClass(),TagName.class,this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(String ss: pointTagsMap.keySet()) {
			s = s + ss + ": " + pointTagsMap.get(ss).toString() + "  ";
		}
		return s;
	}

}
