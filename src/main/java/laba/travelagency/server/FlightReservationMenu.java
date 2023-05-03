package laba.travelagency.server;

import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import laba.travelagency.exceptions.InputDoesNotMatchException;
import laba.travelagency.exceptions.InvalidInputException;
import laba.travelagency.exceptions.InvalidStateException;

public class FlightReservationMenu {
	
	private static Scanner scanner = new Scanner(System.in);
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
	private static final Logger logger = LogManager.getLogger(FlightReservationMenu.class);
	
	public static String readOriginAirports() {
		HashSet<String> originAirports = Flight.getAirportCodes();
		logger.info("\nEnter Origin Airport " + originAirports.toString() + " :");
		String selectedOriginAirport = scanner.nextLine();
		try {
			if(!(originAirports.contains(selectedOriginAirport)))
			{
				throw new InvalidInputException("Unknown Airport Code !");
			}
		}
		catch(InvalidInputException e)
		{
			logger.info("InvalidInputException : " + e.getMessage());
			selectedOriginAirport = readOriginAirports();
		}
		
		return selectedOriginAirport;
	}
	
	public static String readDestinationAirports() {
		HashSet<String> destinationAirports = Flight.getAirportCodes();
		logger.info("Enter Destination Airport " + destinationAirports.toString() + " :");
		String selectedDestinationAirport = scanner.nextLine();
		try {
			if(!(destinationAirports.contains(selectedDestinationAirport)))
			{
				throw new InvalidInputException("Unknown Airport Code !");
			}
		}
		catch(InvalidInputException e)
		{
			logger.info("InvalidInputException : " + e.getMessage());
			selectedDestinationAirport = readDestinationAirports();
		}
		
		return selectedDestinationAirport;
	}
	
	public static String readEmailInput(String prompt) {
		
		logger.info(prompt);
		String email = scanner.nextLine();
		
		try {
			if(!(pattern.matcher(email).matches()))
				throw new InvalidInputException("Invalid Email. Please provide correct email !!");
		}
		catch(InvalidInputException e) 
		{
			logger.info("\nInvalidInputException : " + e.getMessage());
			logger.info("Enter Input again...");
			email = readEmailInput(prompt);
		}
		
		return email;
	}
	
	
	public static String requestCustomerEmail() {
		
		String customerEmail = readEmailInput("Customer Email : ");
		String confirmEmail = readEmailInput("Confirm Email : ");
		
		try {
			if(!(customerEmail.equals(confirmEmail)))
			{
				throw new InputDoesNotMatchException("Email Inputs do not match !!");
			}
		}
		catch(InputDoesNotMatchException e)
		{
			logger.info("\nInputDoesNotMatchException : " + e.getMessage());
			logger.info("Enter Inputs again");
			customerEmail = requestCustomerEmail();
		}
		return customerEmail;
		
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
}
