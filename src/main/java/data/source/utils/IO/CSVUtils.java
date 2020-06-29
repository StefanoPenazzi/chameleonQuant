/**
 * 
 */
package data.source.utils.IO;

/**
 * @author https://mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CSVUtils {

	private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';

    public static List<Map<String,String>> readCSV(String csvFile,boolean firstRowAsHeader,char separators, char customQuote) throws FileNotFoundException {

    	List<Map<String,String>> res = new ArrayList<>();
        Scanner scanner = new Scanner(new File(csvFile));
        
        if(firstRowAsHeader) {
        	List<String> header = null;
        	if(scanner.hasNext()) {
        		header = parseLine(scanner.nextLine(),separators,customQuote);
        	}
        	
        	 while (scanner.hasNext()) {
        		 int i =0;
        		 List<String> ss = parseLine(scanner.nextLine(),separators,customQuote);
        		 Map<String,String> data = new HashMap<>();
                 for(String s: ss) {
                	 data.put(header.get(i),s);
                	 i++;
                 }
                 res.add(data);
             }
             scanner.close();
             return res;
        }
        else {
        	 while (scanner.hasNext()) {
        		 int i =0;
        		 List<String> ss = parseLine(scanner.nextLine(),separators,customQuote);
        		 Map<String,String> data = new HashMap<>();
                 for(String s: ss) {
                	 data.put(Integer.toString(i),s);
                	 i++;
                 }
                 res.add(data);
             }
             scanner.close();
             return res;
        }
    }

    public static List<String> parseLine(String cvsLine) {
        return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators) {
        return parseLine(cvsLine, separators, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators, char customQuote) {

        List<String> result = new ArrayList<>();

        //if empty, return!
        if (cvsLine == null && cvsLine.isEmpty()) {
            return result;
        }

        if (customQuote == ' ') {
            customQuote = DEFAULT_QUOTE;
        }

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuffer curVal = new StringBuffer();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;

        char[] chars = cvsLine.toCharArray();

        for (char ch : chars) {

            if (inQuotes) {
                startCollectChar = true;
                if (ch == customQuote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {

                    //Fixed : allow "" in custom quote enclosed
                    if (ch == '\"') {
                        if (!doubleQuotesInColumn) {
                            curVal.append(ch);
                            doubleQuotesInColumn = true;
                        }
                    } else {
                        curVal.append(ch);
                    }

                }
            } else {
                if (ch == customQuote) {

                    inQuotes = true;

                    //Fixed : allow "" in empty quote enclosed
                    if (chars[0] != '"' && customQuote == '\"') {
                        curVal.append('"');
                    }

                    //double quotes in column will hit this!
                    if (startCollectChar) {
                        curVal.append('"');
                    }

                } else if (ch == separators) {

                    result.add(curVal.toString());

                    curVal = new StringBuffer();
                    startCollectChar = false;

                } else if (ch == '\r') {
                    //ignore LF characters
                    continue;
                } else if (ch == '\n') {
                    //the end, break!
                    break;
                } else {
                    curVal.append(ch);
                }
            }

        }

        result.add(curVal.toString());

        return result;
    }

}
