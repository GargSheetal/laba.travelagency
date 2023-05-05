/**
 * 
 */
package laba.travelagency.server;

/**
 * @author sheetal
 *
 */
public class ReservationFactory {
	
	public static IFlightReservation createFlightReservation(Flight flight) {	
		return new FlightReservation(flight);
	
	};
	
	public static ICarReservation createCarReservation(Car car) {	
		return new CarReservation(car);
	};
	
	public static IHotelReservation createHotelReservation(Hotel hotel) {
		return new HotelReservation(hotel);
	};
	
	
}
