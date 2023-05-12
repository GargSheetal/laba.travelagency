package laba.travelagency.client;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import laba.travelagency.enums.RoomType;
import laba.travelagency.server.Flight;
import laba.travelagency.server.Hotel;
import laba.travelagency.server.Utils;

public class HotelReservationMenu {

	private static Scanner scanner = new Scanner(System.in);
	private static final Logger logger = LogManager.getLogger(FlightReservationMenu.class);
	
	
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
	
//	public static List<Hotel> getHotelSearchResults(String location, String dateOfStay) {
//		List<Hotel> searchHotelResults = Hotel.search(location, dateOfStay);
//		logger.info("\nSearch Results -----");
//	
//		for(Hotel hotel: searchHotelResults)
//		{
//			logger.info(
//				(searchHotelResults.indexOf(hotel) + 1) + " | " +
//				hotel.toString()
//				);
//		}
//		logger.trace("Hotel search results fetched");
//		return searchHotelResults;
//  }
	
//	public static List<Hotel> getFilteredHotelResults(List<Hotel> searchHotelResults, double maxPrice, String roomType) {
//		List<Hotel> filteredHotelResults = Hotel.filter(searchHotelResults, maxPrice, roomType);
//		logger.info("\nFiltered results: ");
//		
//		for(Hotel hotel: filteredHotelResults) 
//		{
//			logger.info(
//					(filteredHotelResults.indexOf(hotel) + 1) + " | " +
//						hotel.toString()
//					);
//		}
//		logger.trace("Hotel search results filtered");
//		return filteredHotelResults;
//	}
	
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
