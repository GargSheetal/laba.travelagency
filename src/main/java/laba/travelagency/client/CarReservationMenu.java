package laba.travelagency.client;

import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import laba.travelagency.server.Car;

public class CarReservationMenu {
	
	private static Scanner scanner = new Scanner(System.in);
	private static final Logger logger = LogManager.getLogger(FlightReservationMenu.class);
	
	
	public static List<Car> getCarSearchResults(String location, String pickupDate, String dropOffDate) {

		List<Car> searchCarResults = Car.search(location, pickupDate, dropOffDate);
		logger.info("\nSearch Results -----");
		for(Car car: searchCarResults)
		{
			logger.info(
					(searchCarResults.indexOf(car) + 1) + " | " +
							car.toString()
					);
		}
		logger.trace("Car search results filtered");
		return searchCarResults;
	}
	
	public static String requestCarType() {
		logger.info("Enter a carType : ");
		String carType = scanner.nextLine();
		logger.debug("carType: {}", carType);
		return carType;
	}
	
	public static List<Car> getFilteredCarResults(List<Car> searchCarResults, double maxPrice, String carType) {
		List<Car> filteredCarResults = Car.filter(searchCarResults, maxPrice, carType);
		logger.info("\nFiltered results: ");

		for(Car car: filteredCarResults)
		{
			logger.info(
					(filteredCarResults.indexOf(car) + 1) + " | " +
					car.toString()
					);
		}
		logger.trace("Car search results filtered");
		return filteredCarResults;
	}
	
	public static Car requestSelectCar(List<Car> filteredCarResults) {
		logger.info("\nSelect your car : ");
		int selectedCarIdx = scanner.nextInt();scanner.nextLine();
		
		Car selectedCar = filteredCarResults.get(selectedCarIdx - 1);
		logger.info("\nCar Selected -----");
		logger.info(selectedCar.toString());
		return selectedCar;
	}

	public static boolean requestNeedCarSeat() {
		logger.info("\nneedCarSeat : ");
		boolean needCarSeat = scanner.nextBoolean(); scanner.nextLine();
		logger.debug("needCarSeat: {}", needCarSeat);
		return needCarSeat;
	}
	
	
}
