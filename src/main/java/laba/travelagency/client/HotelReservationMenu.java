package laba.travelagency.client;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import laba.travelagency.enums.RoomType;
import laba.travelagency.exceptions.MissingInputException;
import laba.travelagency.server.Hotel;
import laba.travelagency.server.IHotelReservation;
import laba.travelagency.server.ReservationFactory;
import laba.travelagency.server.Utils;

public class HotelReservationMenu{

	private static Scanner scanner = new Scanner(System.in);
	private static final Logger logger = LogManager.getLogger(FlightReservationMenu.class);
	
	
	public static IHotelReservation launch() {
		
		logger.info("\nHotel Reservation started -----");
		String location = MenuHelper.requestLocation();
		
		String dateOfStay = MenuHelper.requestDate("Enter date of stay (yyyy-MM-dd) : ");
		logger.debug("dateOfStay: {}", dateOfStay);
		
		List<Hotel> searchHotelResults = getHotelSearchResults(location, dateOfStay);
		logger.info("\nSearch Results -----");
		MenuHelper.printList(searchHotelResults, hotel -> System.out.println((searchHotelResults.indexOf(hotel) + 1) + " | " + hotel.toString()));
		
		logger.info("\nSet Filters : ");
		double maxPrice = MenuHelper.requestMaxPrice();
		
		RoomType roomType = requestRoomType();
		
		List<Hotel> filteredHotelResults = getFilteredHotelResults(searchHotelResults, maxPrice, roomType);
		logger.info("\nFiltered results -----");
		MenuHelper.printList(filteredHotelResults, hotel -> System.out.println((filteredHotelResults.indexOf(hotel) + 1) + " | " + hotel.toString()));
		
		Hotel selectedHotel = requestSelectHotel(filteredHotelResults);
		
		IHotelReservation hotelReservation = ReservationFactory.createHotelReservation(selectedHotel);
		logger.debug("Hotel reservation initiated : {}", hotelReservation.getReservationId());
		
		String customerName = MenuHelper.requestCustomerName();
		hotelReservation.getCustomer().setCustomerName(customerName);
		
		String customerEmail = MenuHelper.requestCustomerEmail();
		hotelReservation.getCustomer().setCustomerEmail(customerEmail);
		
		String customerPhone = MenuHelper.requestPhoneNumber();
		hotelReservation.getCustomer().setCustomerPhone(customerPhone);
		
		boolean needFreeBreakfast = requestNeedFreeBreakfast();
		hotelReservation.setNeedFreeBreakfast(needFreeBreakfast);
		
		boolean needFreeInternet = requestNeedFreeInternet();
		hotelReservation.setNeedFreeInternet(needFreeInternet);
	
		try {
			hotelReservation.confirmReservation();
		} catch (MissingInputException e) {
			hotelReservation = null;
			logger.error("Hotel cannot be reserved. MissingInputException : {}", e.getMessage());
		}
		return hotelReservation;
	}
	
	
	public static RoomType requestRoomType() {
		logger.info("Enter a room type [SINGLE, DOUBLE, QUEEN, KING, SUITE] : ");
		String inputRoomType = scanner.nextLine();
		RoomType roomType;
		try {
			roomType = RoomType.valueOf(inputRoomType.toUpperCase());
		}
		catch(IllegalArgumentException e)
		{
			System.out.println("IllegalArgumentException : Invalid Room Type !\n");
			roomType = requestRoomType();
		}
		logger.debug("roomType: {}", roomType);
		return roomType;
	}
	
	public static List<Hotel> getHotelSearchResults(String location, String dateOfStay) {
		return Utils.search(new File("./src/main/resources/laba/travelagency/testdata/hotelsData.csv"), 
				values -> new Hotel(values[0], values[1], RoomType.valueOf(values[2]), values[3], Double.parseDouble(values[4])), 
				hotel -> hotel.getLocation().equalsIgnoreCase(location) && hotel.getAvailableDate().equals(dateOfStay)
				);
	}

	public static List<Hotel> getFilteredHotelResults(List<Hotel> searchHotelResults, double maxPrice, RoomType roomType) {
		return Utils.filter(searchHotelResults, hotel -> hotel.getPrice() <= maxPrice && hotel.getRoomType().equals(roomType));
	}
	
	
	public static Hotel requestSelectHotel(List<Hotel> filteredHotelResults) {
		logger.info("Select your hotel : ");
		int selectedHotelIdx = scanner.nextInt();scanner.nextLine();
		
		Hotel selectedHotel = filteredHotelResults.get(selectedHotelIdx - 1);
		logger.info("\nHotel Selected -----");
		logger.info(selectedHotel.toString());
		return selectedHotel;
	}
	
	public static boolean requestNeedFreeBreakfast() {
		logger.info("\nneedFreeBreakfast : ");
		boolean needFreeBreakfast = scanner.nextBoolean(); scanner.nextLine();
		logger.debug("needFreeBreakfast: {}", needFreeBreakfast);
		return needFreeBreakfast;
	}
	
	public static boolean requestNeedFreeInternet() {
		logger.info("needFreeInternet : ");
		boolean needFreeInternet = scanner.nextBoolean(); scanner.nextLine();
		logger.debug("needFreeInternet: {}", needFreeInternet);
		return needFreeInternet;
	}
	
}
