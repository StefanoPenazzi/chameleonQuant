/**
 * 
 */
package data.source.external.database.tickers;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import data.source.utils.IO.CSVUtils;

/**
 * @author stefanopenazzi
 *
 */
public class TickersSetFactory {
	
	public TickersSetFactory() {
		
	}
	
	public TickersSetI createFromTxtFile(String name,String dir,boolean firstRowAsHeader,char separators, char customQuote, String header) {
		List<Map<String,String>> res;
		List<String> tickers = new ArrayList<String>();
		//TODO check if the header is present
		try {
			//res = CSVUtils.readCSV(System.getProperty("user.dir")+ "/src/main/resources/symbol_list/NASDAQ.txt",true,'\t','"');
			res = CSVUtils.readCSV(dir,firstRowAsHeader,separators, customQuote);
			//conert this in a list of string 
		    for(Map<String,String> m: res) {
		    	tickers.add(m.get(header))
;		    }
			
		    System.out.println();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new TickersSetImpl(name,tickers);
	}

}
