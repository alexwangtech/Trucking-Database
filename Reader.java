/**
 * To be used with the Dynasty Database GUI Application
 * When reading files, an instance of this class will be created
 * @author AlexanderWang
 */

package dataprocessing;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Reader {
	File fileToRead;
	FileReader in;
	BufferedReader readFile;
	String source;
	ArrayList<String> arrayList;
	String[] stringArray;
	
	//constructor: creates an instance of this class
	public Reader() {
		
	}
	
	//method: reads everything from a file to a String[] array (takes in file name input)
	public void readFromFile(String name) {
		try {
			fileToRead = new File(name);
			in = new FileReader(fileToRead);
			readFile = new BufferedReader(in);
			arrayList = new ArrayList<>();
			
			while (!((source = readFile.readLine()) == null)) {
				arrayList.add(source);
			}
			
			//copy the values to a string array
			stringArray = new String[arrayList.size()];
			for (int i = 0; i < stringArray.length; i++) {
				stringArray[i] = arrayList.get(i);
			}
			
			readFile.close();
			in.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//method: returns the string array that was read from the method 'readFromFile' (if user writes new data before using this method, previous data will be gone!)
	public String[] returnReadData() {
		return stringArray;
	}
}
