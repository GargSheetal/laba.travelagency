package laba.travelagency.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @author sheetal
 *
 */

public final class Hotel {

	private final String hotelName;
	private final String location;
	private final String roomType;
	private final String availableDate;
	private final double price;
	
	private static final Logger logger = LogManager.getLogger(Flight.class);
	
	public Hotel(String hotelName, String location, String roomType, String availableDate, double price) {
		
		this.hotelName = hotelName;
		this.location = location;
		this.roomType = roomType;
		this.availableDate = availableDate;
		this.price = price;
	}

	public String getAvailableDate() {
		return availableDate;
	}
	
	public String getRoomType() {
		return roomType;
	}
	
	public String getLocation() {
		return location;
	}
	
	public double getPrice() {
		return price;
	}
	
	public String getHotelName() {
		return hotelName;
	}
	
	public static HashSet<String> getLocations() {
		return Utils.createHashSetFromFileContents("./src/main/resources/laba/travelagency/testdata/locations.csv");
	}

	public static List<Hotel> search(String location, String dateOfStay) {
		
		List<Hotel> matchingHotels = new ArrayList<>();
		
		for(Hotel hotel: readHotelsFromCsv(new File("./src/main/resources/laba/travelagency/testdata/hotelsData.csv")))
		{
			if(hotel.getLocation().equalsIgnoreCase(location) && hotel.getAvailableDate().equals(dateOfStay))
			{
				matchingHotels.add(hotel);
			}	
		}
		return matchingHotels;
				
	}
	
	// method to return the stringify version of the hotel object
	@Override
	public final String toString() {
		String s = (
				getHotelName() + " at " +
				getLocation() + " with " +
				getRoomType() + " Room on " +
				getAvailableDate() + " for $" +
				getPrice() + " per night"
		);	
		return s;
	}
	
	public static List<Hotel> filter(List<Hotel> hotels, double maxPrice, String roomType)
	{
		List<Hotel> matchingHotels = new ArrayList<>();
		
		for(Hotel hotel: hotels)
		{
			if(hotel.getPrice() <= maxPrice && hotel.getRoomType().equalsIgnoreCase(roomType))
			{
				matchingHotels.add(hotel);
			}
		}
		return matchingHotels;
	}
	
	@Override
	public boolean equals(Object o) {
		
		if(this == o)
		{
			return true;
		}
		if(!(o instanceof Hotel))
		{
			return false;
		}
		
		Hotel h = (Hotel) o;
		return this.hotelName.equals(h.hotelName) && this.location.equals(h.location) && 
			   this.roomType.equals(h.roomType) && this.availableDate.equals(h.availableDate) &&
			   this.price == h.price;
		
	}
	
	@Override
	public int hashCode() {
		
		return Objects.hash(hotelName, location, roomType, availableDate, price);
	}
	
	public static List<Hotel> readHotelsFromCsv(File file) {
	    List<Hotel> hotels = new ArrayList<>();

	    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	        String line;

	        while ((line = reader.readLine()) != null) {
	            String[] values = line.split(",");

	            String hotelName = values[0];
	            String location = values[1];
	            String roomType = values[2];
	            String availableDate = values[3];
	            double price = Double.parseDouble(values[4]);

	            Hotel hotel = new Hotel(hotelName, location, roomType, availableDate, price);
	            hotels.add(hotel);
	        }

	    } catch (IOException e) {
	    	System.out.println("Error while fetching Flight List: " + e.getMessage());
	        logger.fatal("Error while fetching Flight List: {}", e.getMessage());
	        System.exit(1);;
	    }

	    return hotels;
	}
	
}
