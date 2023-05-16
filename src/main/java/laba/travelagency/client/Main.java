package laba.travelagency.client;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import laba.travelagency.enums.ReservationType;
import laba.travelagency.exceptions.MissingInputException;
import laba.travelagency.server.ICarReservation;
import laba.travelagency.server.IFlightReservation;
import laba.travelagency.server.IHotelReservation;
import laba.travelagency.server.Seat;
import laba.travelagency.server.Trip;


public class Main {
	
	static Scanner scanner = new Scanner(System.in);
	static Trip trip = new Trip();
	private static final Logger logger = LogManager.getLogger(Main.class);
	
	
	private static void addMoreTripReservations() throws MissingInputException {
		logger.info("\nDo you want to add more reservations to this trip : ");
		Boolean response = scanner.nextBoolean();scanner.nextLine();
		if(response == true) {
			mainMenu();
		}
	}
	
	private static void mainMenu() throws MissingInputException {
		logger.info("\nPresenting Main Menu -----");
		switch(MenuHelper.requestReservationType()) {
			case FLIGHT:
				IFlightReservation flightReservation = FlightReservationMenu.launch();
				trip.getFlightReservations().addReservation(flightReservation, flightReservation.getFlight().getPrice() + ReservationType.FLIGHT.getBookingFee()); 
				addMoreTripReservations(); break;
			case HOTEL: 
				IHotelReservation hotelReservation = HotelReservationMenu.launch();
				trip.getHotelReservations().addReservation(hotelReservation, hotelReservation.getHotel().getPrice() + ReservationType.HOTEL.getBookingFee()); 
				addMoreTripReservations(); break;
			case CAR: 
				ICarReservation carReservation = CarReservationMenu.launch();
				trip.getCarReservations().addReservation(carReservation, carReservation.getCar().getPrice() + ReservationType.CAR.getBookingFee()); 
				addMoreTripReservations(); break;
			default: break;
		}
	}
	
	public static void main(String[] args) throws MissingInputException {	
//		Flight flight = new Flight();
//		ReflectionClassExample.getClassDetails(flight);
		mainMenu();
		logger.info(trip.toString());
		scanner.close();
	}
}
