package laba.travelagency.client;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import laba.travelagency.enums.CarType;
import laba.travelagency.exceptions.InvalidInputException;
import laba.travelagency.exceptions.MissingInputException;
import laba.travelagency.server.Car;
import laba.travelagency.server.ICarReservation;
import laba.travelagency.server.ReservationFactory;
import laba.travelagency.server.Utils;

public class CarReservationMenu {
	
	private static Scanner scanner = new Scanner(System.in);
	private static final Logger logger = LogManager.getLogger(FlightReservationMenu.class);
	

	public static ICarReservation launch() {
		
		logger.info("\nCar Reservation started -----");
		String location = MenuHelper.requestLocation();
		
		String pickupDate = MenuHelper.requestDate("Enter pickupDate (yyyy-MM-dd) : ");
		logger.debug("pickupDate: {}", pickupDate);
		
		String dropOffDate = MenuHelper.requestDate("Enter dropOffDate (yyyy-MM-dd) : ");
		logger.debug("dropOffDate: {}", dropOffDate);
		
		List<Car> searchCarResults = getCarSearchResults(location, pickupDate, dropOffDate);
		logger.info("\nSearch Results -----");
		MenuHelper.printList(searchCarResults, car -> System.out.println((searchCarResults.indexOf(car) + 1) + " | " + car.toString()));
		
		logger.info("\nSet Filters : ");
		double maxPrice = MenuHelper.requestMaxPrice();
		
		CarType carType = requestCarType();
		
		List<Car> filteredCarResults = getFilteredCarResults(searchCarResults, maxPrice, carType);
		logger.info("\nFiltered results -----");
		MenuHelper.printList(filteredCarResults, car -> System.out.println((filteredCarResults.indexOf(car) + 1) + " | " + car.toString()));
		
		Car selectedCar = requestSelectCar(filteredCarResults);
		
		ICarReservation carReservation = ReservationFactory.createCarReservation(selectedCar);
		logger.debug("Car reservation initiated : {}", carReservation.getReservationId());
		
		String customerName = MenuHelper.requestCustomerName();
		carReservation.getCustomer().setCustomerName(customerName);
		
		String customerEmail = MenuHelper.requestCustomerEmail();
		carReservation.getCustomer().setCustomerEmail(customerEmail);
		
		String customerPhone = MenuHelper.requestPhoneNumber();
		carReservation.getCustomer().setCustomerPhone(customerPhone);
		
		boolean needCarSeat = requestNeedCarSeat();
		carReservation.setNeedCarSeat(needCarSeat);
		
		try {
			carReservation.confirmReservation();
		} catch (MissingInputException e) {
			carReservation = null;
			logger.error("Car cannot be reserved. MissingInputException : {}", e.getMessage());
		}
		return carReservation;
	}
	
	
	public static List<Car> getCarSearchResults(String location, String pickupDate, String dropOffDate) {
		return Utils.search(new File("./src/main/resources/laba/travelagency/testdata/carsData.csv"), 
				values -> new Car(values[0], values[1], values[2], Double.parseDouble(values[3]), CarType.valueOf(values[4])), 
				car -> car.getLocation().equalsIgnoreCase(location) 
						&& car.getPickupDate().equals(pickupDate)
						&& car.getDropOffDate().equals(dropOffDate)
				);
	}
	
	public static CarType requestCarType() {
		logger.info("Enter a carType [SEDAN, SUV, MINIVAN, PICKUP_TRUCK, CONVERTIBLE] : ");
		String carTypeInput = scanner.nextLine();
		CarType carType;
		try {
			 carType = CarType.valueOf(carTypeInput.toUpperCase());
		}
		catch(IllegalArgumentException e)
		{
			System.out.println("IllegalArgumentException : Invalid Car Type !\n");
			carType = requestCarType();
		}
		logger.debug("carType: {}", carType);
		return carType;
	}
	
	public static List<Car> getFilteredCarResults(List<Car> searchCarResults, double maxPrice, CarType carType) {
		return Utils.filter(searchCarResults, car -> car.getPrice() <= maxPrice && car.getCarType().equals(carType));
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
