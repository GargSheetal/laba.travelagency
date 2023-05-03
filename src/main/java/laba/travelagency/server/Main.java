package laba.travelagency.server;

import java.util.List;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import laba.travelagency.exceptions.InvalidStateException;
import laba.travelagency.exceptions.MissingInputException;



public class Main {
	
	static Scanner scanner = new Scanner(System.in);
	static Trip trip = new Trip();
	private static final Logger logger = LogManager.getLogger(Main.class);
	
	
	private static IFlightReservation flightReservationMenuItem() {
		
		logger.info("\nFlight Reservation started -----");
		String originAirport = FlightReservationMenu.readOriginAirports();
		logger.debug("originAirport: {}", originAirport);
	
		String destinationAirport = FlightReservationMenu.readDestinationAirports();
		logger.debug("destinationAirport: {}", destinationAirport);
		
		logger.info("Enter departure date (yyyy-MM-dd) : ");
		String departureDate = scanner.nextLine();
		logger.debug("departureDate: {}", departureDate);
		
		List<Flight> searchFlightResults = Flight.search(originAirport, destinationAirport, departureDate);
		logger.info("\nSearch Results -----");
		for(Flight flight: searchFlightResults)
		{
			logger.info(
					(searchFlightResults.indexOf(flight) + 1) + " | " +
					flight.toString()
					);
		}
		logger.trace("Flight search results fetched");
		
		logger.info("\nSet Filters : ");
		logger.info("Enter max price : ");
		double maxPrice = scanner.nextDouble();scanner.nextLine();
		logger.debug("maxPrice: {}", maxPrice);
		
		logger.info("Enter max number of stops : ");
		int maxNoOfStops = scanner.nextInt();scanner.nextLine();
		logger.debug("maxNoOfStops: {}", maxNoOfStops);
		
		List<Flight> filterFlightResults = Flight.filter(searchFlightResults, maxPrice, maxNoOfStops);
		logger.info("\nFiltered Results -----");
		for(Flight flight: filterFlightResults)
		{
			logger.info(
					(filterFlightResults.indexOf(flight) + 1) + " | " +
					flight.toString()
					);
		}
		logger.trace("Flight search results filtered");
		
		logger.info("Select your flight : ");
		int selectedFlightIdx = scanner.nextInt();scanner.nextLine();
		
		Flight selectedFlight = filterFlightResults.get(selectedFlightIdx - 1);
		logger.info("\nFlight Selected -----");
		logger.info(selectedFlight.toString());
		
		IFlightReservation flightReservation = ReservationFactory.createFlightReservation(selectedFlight);
		logger.debug("Flight reservation initiated : {}", flightReservation.getReservationId());
		
		logger.info("\nCustomer Name : ");
		String customerName = scanner.nextLine();
		flightReservation.getCustomer().setCustomerName(customerName);
		logger.debug("Customer Name: {}", customerName);
		
		String customerEmail = FlightReservationMenu.requestCustomerEmail();
		flightReservation.getCustomer().setCustomerEmail(customerEmail);
		logger.debug("Customer Email: {}", customerEmail);
		
		logger.info("Customer Phone : ");
		String customerPhone = scanner.nextLine();
		flightReservation.getCustomer().setCustomerPhone(customerPhone);
		logger.debug("Customer Phone: {}", customerPhone);
		
		Seat seat = FlightReservationMenu.requestSeatNumber(selectedFlight, customerEmail);
		flightReservation.setSeat(seat);
		logger.debug("Seat Selected: {}", seat.toString());
		
		FlightReservationMenu.requestAddToQueueForBusinessClassSeat(selectedFlight, customerEmail);
		try {
			FlightReservationMenu.requestAddBusinessClassSeat(selectedFlight);
		} catch (InvalidStateException e) {
			logger.error(e.getMessage());
		}
		
		logger.info("\nneedSpecialAssistance : ");
		Boolean needSpecialAssistance = scanner.nextBoolean();scanner.nextLine();
		flightReservation.setNeedSpecialAssistance(needSpecialAssistance);
		logger.debug("needSpecialAssistance: {}", needSpecialAssistance);
		
		logger.info("needMealService : ");
		Boolean needMealService = scanner.nextBoolean();scanner.nextLine();
		flightReservation.setNeedMealService(needMealService);
		logger.debug("needMealService: {}", needMealService);

		try {
			flightReservation.confirmReservation();
//			logger.info("\nFlight is reserved: {}", flightReservation.toString());
		} catch (MissingInputException e) {
			flightReservation = null;
			logger.error("Flight cannot be reserved. MissingInputException : {}", e.getMessage());
		}
		return flightReservation;
	}
	
	private static IHotelReservation hotelReservationMenuItem() {
		logger.info("\nHotel Reservation started -----");
		logger.info("Enter location : ");
		String location = scanner.nextLine();
		logger.debug("location: {}", location);
		
		logger.info("Enter date of stay : ");
		String dateOfStay = scanner.nextLine();
		logger.debug("dateOfStay: {}", dateOfStay);
		
		List<Hotel> searchHotelResults = Hotel.search(location, dateOfStay);
		logger.info("\nSearch Results -----");
		
		for(Hotel hotel: searchHotelResults)
		{
			logger.info(
					(searchHotelResults.indexOf(hotel) + 1) + " | " +
					hotel.toString()
					);
		}
		logger.trace("Hotel search results fetched");
		
		logger.info("\nSet Filters : ");
		logger.info("Enter max price : ");
		double maxPrice = scanner.nextDouble(); scanner.nextLine();
		logger.debug("maxPrice: {}", maxPrice);
		
		logger.info("Enter a room type : ");
		String roomType = scanner.nextLine();
		logger.debug("roomType: {}", roomType);
		
		List<Hotel> filteredHotelResults = Hotel.filter(searchHotelResults, maxPrice, roomType);
		logger.info("\nFiltered results: ");
		
		for(Hotel hotel: filteredHotelResults) 
		{
			logger.info(
					(filteredHotelResults.indexOf(hotel) + 1) + " | " +
						hotel.toString()
					);
		}
		logger.trace("Hotel search results filtered");
		
		logger.info("Select your hotel : ");
		int selectedHotelIdx = scanner.nextInt();scanner.nextLine();
		
		Hotel selectedHotel = filteredHotelResults.get(selectedHotelIdx - 1);
		logger.info("\nHotel Selected -----");
		logger.info(selectedHotel.toString());
		
		IHotelReservation hotelReservation = ReservationFactory.createHotelReservation(selectedHotel);
		logger.debug("Hotel reservation initiated : {}", hotelReservation.getReservationId());
		
		logger.info("\nCustomer Name : ");
		String customerName = scanner.nextLine();
		hotelReservation.getCustomer().setCustomerName(customerName);
		logger.debug("Customer Name: {}", customerName);
		
		String customerEmail = MenuHelper.requestCustomerEmail();
		hotelReservation.getCustomer().setCustomerEmail(customerEmail);
		logger.debug("Customer Email: {}", customerEmail);
		
		logger.info("Customer Phone : ");
		String customerPhone = scanner.nextLine();
		hotelReservation.getCustomer().setCustomerPhone(customerPhone);
		logger.debug("Customer Phone: {}", customerPhone);
		
		logger.info("\nneedFreeBreakfast : ");
		boolean needFreeBreakfast = scanner.nextBoolean(); scanner.nextLine();
		hotelReservation.setNeedFreeBreakfast(needFreeBreakfast);
		logger.debug("needFreeBreakfast: {}", needFreeBreakfast);
		
		logger.info("needFreeInternet : ");
		boolean needFreeInternet = scanner.nextBoolean(); scanner.nextLine();
		hotelReservation.setNeedFreeInternet(needFreeInternet);
		logger.debug("needFreeInternet: {}", needFreeInternet);
	
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
		logger.info("Enter location : ");
		String location = scanner.nextLine();
		logger.debug("location: {}", location);
		
		logger.info("Enter pickupDate : ");
		String pickupDate = scanner.nextLine();
		logger.debug("pickupDate: {}", pickupDate);
		
		logger.info("Enter dropOffDate : ");
		String dropOffDate = scanner.nextLine();
		logger.debug("dropOffDate: {}", dropOffDate);
		
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
		
		logger.info("\nSet Filters : ");
		logger.info("Enter max price : ");
		double maxPrice = scanner.nextDouble(); scanner.nextLine();
		logger.debug("maxPrice: {}", maxPrice);
		
		logger.info("Enter a carType : ");
		String carType = scanner.nextLine();
		logger.debug("carType: {}", carType);
		
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
		
		logger.info("Select your car : ");
		int selectedCarIdx = scanner.nextInt();scanner.nextLine();
		
		Car selectedCar = filteredCarResults.get(selectedCarIdx - 1);
		logger.info("\nCar Selected -----");
		logger.info(selectedCar.toString());
		
		ICarReservation carReservation = ReservationFactory.createCarReservation(selectedCar);
		logger.debug("Car reservation initiated : {}", carReservation.getReservationId());
		
		logger.info("\nCustomer Name : ");
		String customerName = scanner.nextLine();
		carReservation.getCustomer().setCustomerName(customerName);
		logger.debug("Customer Name: {}", customerName);
		
		String customerEmail = MenuHelper.requestCustomerEmail();
		carReservation.getCustomer().setCustomerEmail(customerEmail);
		logger.debug("Customer Email: {}", customerEmail);
		
		logger.info("Customer Phone : ");
		String customerPhone = scanner.nextLine();
		carReservation.getCustomer().setCustomerPhone(customerPhone);
		logger.debug("Customer Phone: {}", customerPhone);
		
		logger.info("\nneedCarSeat : ");
		boolean needCarSeat = scanner.nextBoolean(); scanner.nextLine();
		carReservation.setNeedCarSeat(needCarSeat);
		logger.debug("needCarSeat: {}", needCarSeat);
		
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
		
		logger.info("Presenting Main Menu");
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
