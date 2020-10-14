/**
 * 
 */
package data.source.utils.IO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author stefanopenazzi
 *
 */
public class TxtUtils {
	
	public static void stringToFile(String str, String fileName) 
			  throws IOException {
			    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			    writer.write(str);
			    writer.close();
			}

}
