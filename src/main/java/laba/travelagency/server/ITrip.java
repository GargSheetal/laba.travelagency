/**
 * 
 */
package laba.travelagency.server;

/**
 * @author sheetal
 *
 */
public interface ITrip {
	String getTripId();
	double getTotalAmount();
	ReservationList<IFlightReservation> getFlightReservations();
	ReservationList<IHotelReservation> getHotelReservations();
	ReservationList<ICarReservation> getCarReservations();
}
