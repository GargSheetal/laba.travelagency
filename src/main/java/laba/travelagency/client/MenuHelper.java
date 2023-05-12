package laba.travelagency.client;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import laba.travelagency.enums.ReservationType;
import laba.travelagency.exceptions.InputDoesNotMatchException;
import laba.travelagency.exceptions.InvalidInputException;
import laba.travelagency.server.Hotel;

public class MenuHelper {
	
	private static Scanner scanner = new Scanner(System.in);
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
	private static final Logger logger = LogManager.getLogger(FlightReservationMenu.class);
	
	
	public static <T> void printList(List<T> list, Consumer<T> consumer) {
		list.forEach(consumer);
	}
	
	public static ReservationType requestReservationType() {
		ArrayList<String> reservationTypeList = new ArrayList<String>();
	    for (ReservationType reservationType : ReservationType.values()) {
	    	reservationTypeList.add(reservationType.getDisplayName());
	    	
	    }
	    ReservationType input = null;
	    try {
			logger.info("\nEnter Reservation Type - " + reservationTypeList + " : ");		
			String reservationTypeInput = scanner.nextLine();
			input = ReservationType.fromDisplayName(reservationTypeInput);
	    }
	    catch(IllegalArgumentException e) {
	    	System.out.println("IllegalArgumentException : " + e.getMessage());
	    	input = requestReservationType();
	    }
	    return input;
	}
	
	public static String requestLocation() {
		
		Set<String> locations = Hotel.getLocations();
		logger.info("\nEnter location " + locations.toString() + " :");
		String location = scanner.nextLine();
		try {
			if(!(locations.contains(location)))
			{
				throw new InvalidInputException("Invalid Location !");
			}
		}
		catch(InvalidInputException e)
		{
			System.out.println("InvalidInputException : " + e.getMessage());
			location = requestLocation();
		}
		logger.debug("location: {}", location);
		return location;
	}
	
	public static String requestDate(String prompt) {
		logger.info(prompt);
		String date = scanner.nextLine();
		try {
			LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		catch(DateTimeParseException e)
		{
			InvalidInputException x = new InvalidInputException("Invalid Date !");
			logger.info("InvalidInputException : " + x.getMessage());
			date = requestDate(prompt);
		}
		return date;
	}
	
	public static double requestMaxPrice() {
		logger.info("Enter max price : ");
		double maxPrice = scanner.nextDouble();scanner.nextLine();
		logger.debug("maxPrice: {}", maxPrice);
		return maxPrice;
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
	
	public static String requestCustomerName() {
		logger.info("\nCustomer Name : ");
		String customerName = scanner.nextLine();
		logger.debug("Customer Name: {}", customerName);
		return customerName;
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
		logger.debug("Customer Email: {}", customerEmail);
		return customerEmail;
	}

	public static String requestPhoneNumber() {
		System.out.println(("Customer Phone : "));
		String customerPhone = scanner.nextLine();
		try {
			if(!(customerPhone.matches("^[0-9]{10}$")))
			{
				throw new InvalidInputException("Invalid Phone Number !");
			}
		}
		catch(InvalidInputException e)
		{
			logger.info("InvalidInputException : " + e.getMessage());
			customerPhone = requestPhoneNumber();
		}
		logger.debug("Customer Phone: {}", customerPhone);
		return customerPhone;
	}
	
}
