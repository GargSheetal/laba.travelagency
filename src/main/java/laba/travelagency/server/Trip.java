package laba.travelagency.server;

import java.util.UUID;

public class Trip implements ITrip {

	private final String tripId;
	private ReservationList<IFlightReservation> flightReservations = new ReservationList<IFlightReservation>();
	private ReservationList<IHotelReservation> hotelReservations = new ReservationList<IHotelReservation>();
	private ReservationList<ICarReservation> carReservations = new ReservationList<ICarReservation>();
	
	private static double ONLINE_BOOKING_FEE;
	
	static {
		ONLINE_BOOKING_FEE = 0.55;
	}
	
	public Trip() {
		UUID uuid = UUID.randomUUID();
		this.tripId = uuid.toString();
	};

	public String getTripId() {
		return tripId;
	}

	public double getTotalAmount() {
		return (ONLINE_BOOKING_FEE
			+ this.flightReservations.getTotalAmount()
			+ this.hotelReservations.getTotalAmount()
			+ this.carReservations.getTotalAmount()
		);
	}

	public ReservationList<IFlightReservation> getFlightReservations() {
		return this.flightReservations;
	}
	public ReservationList<IHotelReservation> getHotelReservations() {
		return this.hotelReservations;
	}
	public ReservationList<ICarReservation> getCarReservations() {
		return this.carReservations;
	}
	
	@Override
	public String toString() {
		String s = "\n\n============= Receipt ==============";
		if(this.getTripId() == null)
		{
			s += "\n\nNo reservations";
			return s;
		}
		s += "\n\n----- Trip Id : " + this.getTripId() + " -----";
		for(IFlightReservation flightReservation: this.getFlightReservations().getReservationList()) {
			s += "\n" + flightReservation.toString();
		}
		for(IHotelReservation hotelReservation: this.getHotelReservations().getReservationList()) {
			s += "\n" + hotelReservation.toString();
		}
		for(ICarReservation carReservation: this.getCarReservations().getReservationList()) {
			s += "\n" + carReservation.toString();
		}
		s += "\n\n----- Total Amount : $" + this.getTotalAmount() + " -----";
		return s;
	}

}
