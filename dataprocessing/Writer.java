/**
 * To be used with the Dynasty Database GUI Application
 * When writing data to a file, an instance of this class will be created
 * @author AlexanderWang
 */

package dataprocessing;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class Writer {
	String fileName;
	File blankFile;
	FileWriter out;
	BufferedWriter writeFile;
	
	//constructor: creates and instance of this class
	public Writer() {
		
	}
	
	//method: writes all of the data to the file (takes in name and data arguments)
	public void writeToFile(String name, String[] dataList) {
		try {
			fileName = name;
			blankFile = new File(fileName);
			out = new FileWriter(blankFile);
			writeFile = new BufferedWriter(out);
			
			//to ensure that the writer does not leave a blank line at the end
			for (int i = 0; i < dataList.length; i++) {
				if (i != dataList.length - 1) {
					writeFile.write(dataList[i]);
					writeFile.newLine();
				} else {
					writeFile.write(dataList[i]);
				}
			}
			
			writeFile.close();
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}