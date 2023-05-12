package laba.travelagency.client;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import laba.travelagency.exceptions.InvalidInputException;
import laba.travelagency.exceptions.InvalidStateException;
import laba.travelagency.server.Flight;
import laba.travelagency.server.Seat;
import laba.travelagency.server.Utils;

public class FlightReservationMenu {
	
	private static Scanner scanner = new Scanner(System.in);
	private static final Logger logger = LogManager.getLogger(FlightReservationMenu.class);
	
	public static String readOriginAirports() {
		Set<String> originAirports = Flight.getAirportCodes();
		logger.info("\nEnter Origin Airport " + originAirports.toString() + " :");
		String selectedOriginAirport = scanner.nextLine();
		try {
			if(!(originAirports.contains(selectedOriginAirport.toUpperCase())))
			{
				throw new InvalidInputException("Unknown Airport Code !");
			}
		}
		catch(InvalidInputException e)
		{
			logger.info("InvalidInputException : " + e.getMessage());
			selectedOriginAirport = readOriginAirports();
		}
		logger.debug("originAirport: {}", selectedOriginAirport);
		return selectedOriginAirport;
	}
	
	public static String readDestinationAirports(String originAirport) {
		Set<String> destinationAirports = Flight.getAirportCodes();
		logger.info("Enter Destination Airport " + destinationAirports.toString() + " :");
		String selectedDestinationAirport = scanner.nextLine();
		try {
			if(!(destinationAirports.contains(selectedDestinationAirport.toUpperCase())))
			{
				throw new InvalidInputException("Unknown Airport Code !");
			}
			if(selectedDestinationAirport.equalsIgnoreCase(originAirport))
			{
				throw new InvalidInputException("Invalid Input! Destination Airport cannot be same as Origin Airport");
			}
		}
		catch(InvalidInputException e)
		{
			logger.info("InvalidInputException : " + e.getMessage());
			selectedDestinationAirport = readDestinationAirports(originAirport);
		}
		logger.debug("destinationAirport: {}", selectedDestinationAirport);
		return selectedDestinationAirport;
	}
	
	public static List<Flight> getSearchFlightResults(String originAirport, String destinationAirport, String departureDate) {
		
		return Utils.search(new File("./src/main/resources/laba/travelagency/testdata/flightsData.csv"), 
				values -> new Flight(values[0], values[1], values[2], values[3], values[4], Integer.parseInt(values[5]), 
							Double.parseDouble(values[6])), 
				flight -> flight.getOriginAirport().equalsIgnoreCase(originAirport)
							&& flight.getDestinationAirport().equalsIgnoreCase(destinationAirport)
							&& flight.getDepartureTimestamp().substring(0, 10).equals(departureDate)
						);
	}
	
	public static List<Flight> getFilteredFlightResults(List<Flight> searchFlightResults, double maxPrice, int maxNoOfStops) {
		return Utils.filter(searchFlightResults, flight -> flight.getPrice() <= maxPrice && flight.getNoOfStops() <= maxNoOfStops);
	}
	
	public static int requestMaxNoOfStops() {
		logger.info("Enter max number of stops : ");
		int maxNoOfStops = scanner.nextInt();scanner.nextLine();
		logger.debug("maxNoOfStops: {}", maxNoOfStops);
		return maxNoOfStops;
	}
	
	
	public static Flight requestSelectFlight(List<Flight> filterFlightResults) {
		logger.info("Select your flight : ");
		int selectedFlightIdx = scanner.nextInt();scanner.nextLine();
		
		Flight selectedFlight = filterFlightResults.get(selectedFlightIdx - 1);
		logger.info("\nFlight Selected -----");
		logger.info(selectedFlight.toString());
		return selectedFlight;
	}
	
	public static Seat requestSeatNumber(Flight flight, String email) {

		logger.info("Select a seat : ");

		String selectedSeat = scanner.nextLine();
		Seat seat = new Seat(selectedSeat);

		try {
			flight.addSeatOccupancy(seat, email);
			logger.info("\n   | Seat Occupancy for flight " + flight.getFlightNumber() + " : " + flight.seatOccupancyMap.toString());
		} catch (InvalidStateException e) {
			logger.info("\nInvalidStateException : " + e.getMessage());
			logger.info("Enter input again....");
			seat = requestSeatNumber(flight, email);
		}
		logger.debug("Seat Selected: {}", seat.toString());
		return seat;
	}
	
	public static void requestAddToQueueForBusinessClassSeat(Flight flight, String customerEmail) {
		
		logger.info("   | Queue for upgrade to Business Class : " + 
				flight.queueForUpgradeToBusinessClass.toString());
		logger.info("\nDo you want an upgrade to Business Class Seat: ");
		boolean response = scanner.nextBoolean(); scanner.nextLine();
		if(response == true)
		{
			flight.addToQueueForUpgradeToBusinessClass(customerEmail);
		}
	}
	
	public static void requestAddBusinessClassSeat(Flight flight) throws InvalidStateException {
		
		logger.info("   | Queue for upgrade to Business Class : " + 
				flight.queueForUpgradeToBusinessClass.toString());
		logger.info("\nOpen new Business Class Seat for flight " + flight.getFlightNumber() + " :");
		String businessClassSeat = scanner.nextLine();
		String nextCustomerEmail = flight.getNextInQueueForUpgradeToBusinessClass();
		flight.removeSeatOccupancy(nextCustomerEmail);
		flight.addSeatOccupancy(new Seat(businessClassSeat), nextCustomerEmail);
		logger.info("   | Seat Occupancy for flight " + flight.getFlightNumber() + " : " + flight.seatOccupancyMap.toString());
		flight.removeFromQueueForUpgradeToBusinessClass(nextCustomerEmail);
		logger.info("   | Queue for upgrade to Business Class : " + 
				flight.queueForUpgradeToBusinessClass.toString());
		
	}
	
	public static boolean requestNeedSpecialAssistance() {
		logger.info("\nneedSpecialAssistance : ");
		Boolean needSpecialAssistance = scanner.nextBoolean();scanner.nextLine();
		logger.debug("needSpecialAssistance: {}", needSpecialAssistance);
		return needSpecialAssistance;
	}
	
	public static boolean requestNeedMealService() {
		logger.info("needMealService : ");
		boolean needMealService = scanner.nextBoolean();scanner.nextLine();
		logger.debug("needMealService: {}", needMealService);
		return needMealService;
	}
	
//	public static List<Flight> getSearchFlightResults(String originAirport, String destinationAirport, String departureDate) {
//		List<Flight> searchFlightResults = Flight.search(originAirport, destinationAirport, departureDate);
//		logger.info("\nSearch Results -----");
//		for(Flight flight: searchFlightResults)
//		{
//			logger.info(
//					(searchFlightResults.indexOf(flight) + 1) + " | " +
//					flight.toString()
//					);
//		}
//		logger.trace("Flight search results fetched");
//		return searchFlightResults;
//	}
	
//	public static List<Flight> getFilteredFlightResults(List<Flight> searchFlightResults, double maxPrice, int maxNoOfStops) {
//		List<Flight> filterFlightResults = Flight.filter(searchFlightResults, maxPrice, maxNoOfStops);
//		logger.info("\nFiltered Results -----");
//		for(Flight flight: filterFlightResults)
//		{
//			logger.info(
//					(filterFlightResults.indexOf(flight) + 1) + " | " +
//					flight.toString()
//					);
//		}
//		logger.trace("Flight search results filtered");
//		return filterFlightResults;
//	}
}
