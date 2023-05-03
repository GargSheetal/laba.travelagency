package laba.travelagency.server;


import java.util.UUID;

import laba.travelagency.exceptions.MissingInputException;

/**
 * @author sheetal
 *
 */

public abstract class Reservation implements IReservation {
	
	private final String reservationId;
	protected Customer customer = new Customer();

	protected Reservation() {
		UUID uuid = UUID.randomUUID();
		this.reservationId = uuid.toString();
	}

	public String getReservationId() {
		return reservationId;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public abstract void confirmReservation() throws MissingInputException;
	public abstract void cancelReservation();
	public abstract String toString();

	
}
