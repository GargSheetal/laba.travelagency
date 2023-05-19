package laba.travelagency.server;

import java.io.File;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

public class Seat {
	
	private final String seatNumber;
	private static LinkedList<String[]> seats;

	public Seat(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public String getSeatNumber() {
		return seatNumber;
	}
	
	public static LinkedList<String[]> getSeatAvailability() {
		return Utils.readDataFromCsv(new File("./src/main/resources/laba/travelagency/testdata/seatAvailabilityData.csv"));
	}
	
	public static LinkedList<String> getAvailableSeats() {
		LinkedList<String> myLinkedList = getSeatAvailability().stream()
				.filter(seat -> seat.length == 1)
				.map(seat -> seat[0]).collect(Collectors.toCollection(LinkedList::new));
		return myLinkedList;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(seatNumber);
	}
	
	public boolean equals(Object o) {
		if(this == o)
		{
			return true;
		}
		if(!(o instanceof Seat))
		{
			return false;
		}
			
		Seat s = (Seat) o;
		
		return Objects.equals(seatNumber, s.seatNumber);
	}
	
	public String toString() {
		return "Seat - " + seatNumber;
	}
}
