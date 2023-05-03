/**
 * 
 */
package laba.travelagency.server;

import laba.travelagency.exceptions.MissingInputException;

/**
 * @author sheetal
 * 
 *
 */
public interface IReservation {

	void confirmReservation() throws MissingInputException;
	void cancelReservation();
	String getReservationId();
	String toString();
	Customer getCustomer();
}
