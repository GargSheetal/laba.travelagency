package laba.travelagency.client;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import laba.travelagency.enums.CarType;
import laba.travelagency.enums.ReservationType;
import laba.travelagency.enums.RoomType;
import laba.travelagency.exceptions.InvalidStateException;
import laba.travelagency.exceptions.MissingInputException;
import laba.travelagency.server.Car;
import laba.travelagency.server.Flight;
import laba.travelagency.server.Hotel;
import laba.travelagency.server.ICarReservation;
import laba.travelagency.server.IFlightReservation;
import laba.travelagency.server.IHotelReservation;
import laba.travelagency.server.ReservationFactory;
import laba.travelagency.server.Seat;
import laba.travelagency.server.Trip;



public class Main {
	
	static Scanner scanner = new Scanner(System.in);
	static Trip trip = new Trip();
	private static final Logger logger = LogManager.getLogger(Main.class);
	
	
	private static IFlightReservation flightReservationMenuItem() {
		
		logger.info("\nFlight Reservation started -----");
		
		String originAirport = FlightReservationMenu.readOriginAirports();
	
		String destinationAirport = FlightReservationMenu.readDestinationAirports(originAirport);
		
		String departureDate = MenuHelper.requestDate("Enter departure date (yyyy-MM-dd) : ");
		logger.debug("departureDate: {}", departureDate);
		
		List<Flight> searchFlightResults = FlightReservationMenu.getSearchFlightResults(originAirport, destinationAirport, departureDate);
		logger.info("\nSearch Results -----");
		MenuHelper.printList(searchFlightResults, flight -> System.out.println((searchFlightResults.indexOf(flight) + 1) + " | " + flight.toString()));
		
		logger.info("\nSet Filters : ");
		double maxPrice = MenuHelper.requestMaxPrice();
		
		int maxNoOfStops = FlightReservationMenu.requestMaxNoOfStops();

		List<Flight> filterFlightResults = FlightReservationMenu.getFilteredFlightResults(searchFlightResults, maxPrice, maxNoOfStops);
		logger.info("\nFiltered Results -----");
		MenuHelper.printList(filterFlightResults, flight -> System.out.println((filterFlightResults.indexOf(flight) + 1) + " | " + flight.toString()));
		
		Flight selectedFlight = FlightReservationMenu.requestSelectFlight(filterFlightResults);
		
		IFlightReservation flightReservation = ReservationFactory.createFlightReservation(selectedFlight);
		logger.debug("Flight reservation initiated : {}", flightReservation.getReservationId());
		
		String customerName = MenuHelper.requestCustomerName();
		flightReservation.getCustomer().setCustomerName(customerName);
		
		String customerEmail = MenuHelper.requestCustomerEmail();
		flightReservation.getCustomer().setCustomerEmail(customerEmail);
		
		
		String customerPhone = MenuHelper.requestPhoneNumber();
		flightReservation.getCustomer().setCustomerPhone(customerPhone);
		
		Seat seat = FlightReservationMenu.requestSeatNumber(selectedFlight, customerEmail);
		flightReservation.setSeat(seat);
		
		FlightReservationMenu.requestAddToQueueForBusinessClassSeat(selectedFlight, customerEmail);
		try {
			FlightReservationMenu.requestAddBusinessClassSeat(selectedFlight);
		} catch (InvalidStateException e) {
			logger.error(e.getMessage());
		}
		
		boolean needSpecialAssistance = FlightReservationMenu.requestNeedSpecialAssistance();
		flightReservation.setNeedSpecialAssistance(needSpecialAssistance);
		
		boolean needMealService = FlightReservationMenu.requestNeedMealService();
		flightReservation.setNeedMealService(needMealService);

		try {
			flightReservation.confirmReservation();
		} 
		catch (MissingInputException e) {
			flightReservation = null;
			logger.error("Flight cannot be reserved. MissingInputException : {}", e.getMessage());
		}
		return flightReservation;
	}
	
	private static IHotelReservation hotelReservationMenuItem() {
		
		logger.info("\nHotel Reservation started -----");
		String location = MenuHelper.requestLocation();
		
		String dateOfStay = MenuHelper.requestDate("Enter date of stay (yyyy-MM-dd) : ");
		logger.debug("dateOfStay: {}", dateOfStay);
		
		List<Hotel> searchHotelResults = HotelReservationMenu.getHotelSearchResults(location, dateOfStay);
		logger.info("\nSearch Results -----");
		MenuHelper.printList(searchHotelResults, hotel -> System.out.println((searchHotelResults.indexOf(hotel) + 1) + " | " + hotel.toString()));
		
		logger.info("\nSet Filters : ");
		double maxPrice = MenuHelper.requestMaxPrice();
		
		RoomType roomType = HotelReservationMenu.requestRoomType();
		
		List<Hotel> filteredHotelResults = HotelReservationMenu.getFilteredHotelResults(searchHotelResults, maxPrice, roomType);
		logger.info("\nFiltered results -----");
		MenuHelper.printList(filteredHotelResults, hotel -> System.out.println((filteredHotelResults.indexOf(hotel) + 1) + " | " + hotel.toString()));
		
		Hotel selectedHotel = HotelReservationMenu.requestSelectHotel(filteredHotelResults);
		
		IHotelReservation hotelReservation = ReservationFactory.createHotelReservation(selectedHotel);
		logger.debug("Hotel reservation initiated : {}", hotelReservation.getReservationId());
		
		String customerName = MenuHelper.requestCustomerName();
		hotelReservation.getCustomer().setCustomerName(customerName);
		
		String customerEmail = MenuHelper.requestCustomerEmail();
		hotelReservation.getCustomer().setCustomerEmail(customerEmail);
		
		String customerPhone = MenuHelper.requestPhoneNumber();
		hotelReservation.getCustomer().setCustomerPhone(customerPhone);
		
		boolean needFreeBreakfast = HotelReservationMenu.requestNeedFreeBreakfast();
		hotelReservation.setNeedFreeBreakfast(needFreeBreakfast);
		
		boolean needFreeInternet = HotelReservationMenu.requestNeedFreeInternet();
		hotelReservation.setNeedFreeInternet(needFreeInternet);
	
		try {
			hotelReservation.confirmReservation();
		} catch (MissingInputException e) {
			hotelReservation = null;
			logger.error("Hotel cannot be reserved. MissingInputException : {}", e.getMessage());
		}
		return hotelReservation;
	}
	
	private static ICarReservation carReservationMenuItem() throws MissingInputException {
		
		logger.info("\nCar Reservation started -----");
		String location = MenuHelper.requestLocation();
		
		String pickupDate = MenuHelper.requestDate("Enter pickupDate (yyyy-MM-dd) : ");
		logger.debug("pickupDate: {}", pickupDate);
		
		String dropOffDate = MenuHelper.requestDate("Enter dropOffDate (yyyy-MM-dd) : ");
		logger.debug("dropOffDate: {}", dropOffDate);
		
		List<Car> searchCarResults = CarReservationMenu.getCarSearchResults(location, pickupDate, dropOffDate);
		logger.info("\nSearch Results -----");
		MenuHelper.printList(searchCarResults, car -> System.out.println((searchCarResults.indexOf(car) + 1) + " | " + car.toString()));
		
		logger.info("\nSet Filters : ");
		double maxPrice = MenuHelper.requestMaxPrice();
		
		CarType carType = CarReservationMenu.requestCarType();
		
		List<Car> filteredCarResults = CarReservationMenu.getFilteredCarResults(searchCarResults, maxPrice, carType);
		logger.info("\nFiltered results -----");
		MenuHelper.printList(filteredCarResults, car -> System.out.println((filteredCarResults.indexOf(car) + 1) + " | " + car.toString()));
		
		Car selectedCar = CarReservationMenu.requestSelectCar(filteredCarResults);
		
		ICarReservation carReservation = ReservationFactory.createCarReservation(selectedCar);
		logger.debug("Car reservation initiated : {}", carReservation.getReservationId());
		
		String customerName = MenuHelper.requestCustomerName();
		carReservation.getCustomer().setCustomerName(customerName);
		
		String customerEmail = MenuHelper.requestCustomerEmail();
		carReservation.getCustomer().setCustomerEmail(customerEmail);
		
		String customerPhone = MenuHelper.requestPhoneNumber();
		carReservation.getCustomer().setCustomerPhone(customerPhone);
		
		boolean needCarSeat = CarReservationMenu.requestNeedCarSeat();
		carReservation.setNeedCarSeat(needCarSeat);
		
		try {
			carReservation.confirmReservation();
		} catch (MissingInputException e) {
			carReservation = null;
			logger.error("Car cannot be reserved. MissingInputException : {}", e.getMessage());
		}
		return carReservation;
	}
	
	private static void addMoreTripReservations() throws MissingInputException {
		logger.info("\nDo you want to add more reservations to this trip : ");
		Boolean response = scanner.nextBoolean();scanner.nextLine();
		
		if(response == true) {
			mainMenu();
		}
	}
	
	private static void mainMenu() throws MissingInputException {
		
//		logger.info(
//			"\n Main Menu -----" + 
//			"\n 1. Flight Reservation" + 
//			"\n 2. Hotel Reservation" +
//			"\n 3. Car Reservation" + 
//			"\n 4. Exit" + 
//			"\n Enter your response : "
//		);
//
//		ReservationType[] reservationTypes = ReservationType.values();
//		String reservationTypeList = "";
//	    for (ReservationType reservationType : reservationTypes) {
//	    	reservationTypeList = reservationTypeList + reservationType.getDisplayName() + " ";
//	    }
//		
//		logger.info("\nEnter Reservation Type - " + reservationTypeList + " : ");		
//		String reservationTypeInput = scanner.nextLine();
		logger.info("\nPresenting Main Menu");
		
		switch(MenuHelper.requestReservationType()) {
			case FLIGHT:
				IFlightReservation flightReservation = flightReservationMenuItem();
				trip.getFlightReservations().addReservation(flightReservation, flightReservation.getFlight().getPrice() + ReservationType.FLIGHT.getBookingFee()); 
				addMoreTripReservations(); break;
			case HOTEL: 
				IHotelReservation hotelReservation = hotelReservationMenuItem();
				trip.getHotelReservations().addReservation(hotelReservation, hotelReservation.getHotel().getPrice() + ReservationType.HOTEL.getBookingFee()); 
				addMoreTripReservations(); break;
			case CAR: 
				ICarReservation carReservation = carReservationMenuItem();
				trip.getCarReservations().addReservation(carReservation, carReservation.getCar().getPrice() + ReservationType.CAR.getBookingFee()); 
				addMoreTripReservations(); break;
			default: break;
		}
	}
	
	
	public static void main(String[] args) throws MissingInputException {	
		
		mainMenu();
		logger.info(trip.toString());
		scanner.close();
				
	}

}
