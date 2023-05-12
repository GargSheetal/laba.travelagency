package laba.travelagency.server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {
	
	private static final Logger logger = LogManager.getLogger(Flight.class);
	
	
	public static Set<String> createHashSetFromFileContents(String fileName) {
		Set<String> mySet = new HashSet<>();
		try {
			 mySet = FileUtils.readLines(new File(fileName), "UTF-8").stream()
					.flatMap(line -> Stream.of(StringUtils.split(line)))
					.filter(word -> !word.isEmpty() && StringUtils.isAlpha(word))
					.collect(Collectors.toSet());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mySet;
	}
	
	
	// using stream api to stream over the collection, filtering based on criteria and collecting the resulting stream as List
	public static <T> List<T> filter(List<T> recordList, Predicate<T> doMatchFilterCriteria) {
		return recordList.stream().filter(doMatchFilterCriteria).collect(Collectors.toList());
	}
	
	
	public static <T> List<T> search(File dataFile, Function<String[], T> parseFileDataToObject, Predicate<T> doMatchSearchCriteria) {
		return readDataFromCsv(dataFile, parseFileDataToObject).stream().filter(doMatchSearchCriteria).collect(Collectors.toList());
	}
	
	public static <T> List<T> readDataFromCsv(File dataFile, Function<String[], T> parseFileDataToObject) {
		List<T> myList = new ArrayList<>();
		try {
			myList = FileUtils.readLines(dataFile, "UTF-8").stream()
					.map(line -> line.split(","))
					.map(parseFileDataToObject)
					.collect(Collectors.toList());
		} catch (IOException e) {
			System.out.println("Error while fetching Flight List: " + e.getMessage());
			logger.fatal("Error while fetching Flight List: {}", e.getMessage());
	        System.exit(1);
		}
		return myList;
				
	}	
	
}
