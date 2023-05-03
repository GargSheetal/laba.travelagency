package laba.travelagency.server;

import java.util.Objects;

public class Seat {
	
	private final String seatNumber;

	public Seat(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public String getSeatNumber() {
		return seatNumber;
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
