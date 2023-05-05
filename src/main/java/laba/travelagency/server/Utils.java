package laba.travelagency.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Utils {
	
//	public static HashSet<String> createHashSetFromFileContents(String fileName) {
//		
//		HashSet<String> hashset = new HashSet<>();
//		
//		try(BufferedReader br = new BufferedReader(new FileReader(new File(fileName))))
//		{
//			String line;
//			while((line = br.readLine()) != null)
//			{
//				hashset.add(line);
//			}
//		}
//		catch(IOException e)
//		{
//			System.out.println(e.getMessage());
//		}
//		return hashset;
//	}
	
	// Using FileUtils utility class
	public static HashSet<String> createHashSetFromFileContents(String fileName) {

		HashSet<String> hashset = new HashSet<>();

		File file = new File(fileName);
		try {
			String fileContent = FileUtils.readFileToString(file, "UTF-8");
			String[] lines = fileContent.split("\n");
			for(String line: lines)
			{
				hashset.add(line);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return hashset;
	}
	
	
	
}
