/**
 * 
 */
package laba.travelagency.enums;

/**
 * @author sheetal
 *
 */
public enum ReservationType {
	
	FLIGHT("Flight",0.44),
	HOTEL("Hotel",0.33),
	CAR("Car",0.22);
	
	private final String displayName;
	private final double bookingFee;
	
	ReservationType(String displayName, double bookingFee) {
		this.bookingFee = bookingFee;
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public double getBookingFee() {
		return bookingFee;
	}
	
	public static ReservationType fromDisplayName(String displayName) {
		for(ReservationType reservationType: ReservationType.values())
		{
			if(reservationType.getDisplayName().equals(displayName))
				return reservationType;
		}
		throw new IllegalArgumentException("Invalid Reservation Type !");
	}

}
