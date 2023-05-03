/**
 * 
 */
package laba.travelagency.server;

/**
 * @author sheetal
 *
 */
public interface ICarReservation extends IReservation {
	
	Car getCar();
	void setNeedCarSeat(boolean needCarSeat);
}
