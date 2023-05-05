package laba.travelagency.client;

import java.util.List;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	
		String destinationAirport = FlightReservationMenu.readDestinationAirports();
		
		String departureDate = MenuHelper.requestDate("Enter departure date (yyyy-MM-dd) : ");
		logger.debug("departureDate: {}", departureDate);
		
		List<Flight> searchFlightResults = FlightReservationMenu.getSearchFlightResults(originAirport, destinationAirport, departureDate);
		
		logger.info("\nSet Filters : ");
		double maxPrice = MenuHelper.requestMaxPrice();
		
		int maxNoOfStops = FlightReservationMenu.requestMaxNoOfStops();

		List<Flight> filterFlightResults = FlightReservationMenu.getFilteredFlightResults(searchFlightResults, maxPrice, maxNoOfStops);
		
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
		
		logger.info("\nSet Filters : ");
		double maxPrice = MenuHelper.requestMaxPrice();
		
		String roomType = HotelReservationMenu.requestRoomType();
		
		List<Hotel> filteredHotelResults = HotelReservationMenu.getFilteredHotelResults(searchHotelResults, maxPrice, roomType);
		
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
		
		logger.info("\nSet Filters : ");
		double maxPrice = MenuHelper.requestMaxPrice();
		
		String carType = CarReservationMenu.requestCarType();
		
		List<Car> filteredCarResults = CarReservationMenu.getFilteredCarResults(searchCarResults, maxPrice, carType);
			
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
		
		logger.info("\nPresenting Main Menu");
		logger.info(
			"\n Main Menu -----" + 
			"\n 1. Flight Reservation" + 
			"\n 2. Hotel Reservation" +
			"\n 3. Car Reservation" + 
			"\n 4. Exit" + 
			"\n Enter your response : "
		);
		int mainMenuResponse = scanner.nextInt(); scanner.nextLine();
		
		switch(mainMenuResponse) {
			case 1:
				IFlightReservation flightReservation = flightReservationMenuItem();
				trip.getFlightReservations().addReservation(flightReservation, flightReservation.getFlight().getPrice()); 
				addMoreTripReservations(); break;
			case 2: 
				IHotelReservation hotelReservation = hotelReservationMenuItem();
				trip.getHotelReservations().addReservation(hotelReservation, hotelReservation.getHotel().getPrice()); 
				addMoreTripReservations(); break;
			case 3: 
				ICarReservation carReservation = carReservationMenuItem();
				trip.getCarReservations().addReservation(carReservation, carReservation.getCar().getPrice()); 
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
