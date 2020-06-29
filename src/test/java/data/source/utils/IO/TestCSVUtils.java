/**
 * 
 */
package data.source.utils.IO;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * @author stefanopenazzi
 *
 */
class TestCSVUtils {

	@Test
	void test() throws Exception {
		List<Map<String,String>> result = CSVUtils.readCSV("src/test/resources/data/source/utils/CSVUtilsTest", true,' ' ,' ' );
		System.out.println("");
	}

}
