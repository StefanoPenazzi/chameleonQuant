package data.source.utils.IO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {
	
	private static Properties prop_;
	
	private static void loadProperties()
    {
        Properties prop = new Properties();
 
        // ClassLoader loader = Thread.currentThread().getContextClassLoader();
        ClassLoader loader = PropertiesUtils.class.getClassLoader();
 
        try (InputStream stream = loader.getResourceAsStream("apiKeys.properties"))
        {
            if (stream == null) {
                throw new FileNotFoundException();
            }
            prop.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
         prop_ =prop;
    }
    
     
    public static String getPropertyValue(String key){
    	loadProperties();
        return prop_.getProperty(key);
    }

}
