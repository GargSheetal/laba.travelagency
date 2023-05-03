/**
 * 
 */
package laba.travelagency.server;

/**
 * @author sheetal
 *
 */
public interface IFlightReservation extends IReservation {
	
	Flight getFlight();
	void setSeat(Seat seat);
	void setNeedSpecialAssistance(boolean needSpecialAssistance);
	void setNeedMealService(boolean needMealService);
}
