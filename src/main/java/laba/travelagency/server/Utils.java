package laba.travelagency.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class Utils {
	
	public static HashSet<String> createHashSetFromFileContents(String fileName) {
		
		HashSet<String> hashset = new HashSet<>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(new File(fileName))))
		{
			String line;
			while((line = br.readLine()) != null)
			{
				hashset.add(line);
			}
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
		return hashset;
	}
	
}
