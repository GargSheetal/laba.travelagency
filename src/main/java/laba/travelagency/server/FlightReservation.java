/**
 * 
 */
package laba.travelagency.server;


import laba.travelagency.exceptions.MissingInputException;

/**
 * @author sheetal
 *
 */
public final class FlightReservation extends Reservation implements IFlightReservation {

	private final Flight flight;
	private boolean needSpecialAssistance;
	private boolean needMealService;
	private Seat seat;
	
	public FlightReservation(Flight flight) {
		this.flight = flight;
	}

	public Flight getFlight() {
		return flight;
	}
	
	public Seat getSeat() {
		return seat;
	}
	
	public void setSeat(Seat seat) {
		this.seat = seat;
	}
	
	public boolean getNeedSpecialAssistance()
	{
		return needSpecialAssistance;
	}
	
	@Override
	public void setNeedSpecialAssistance(boolean needSpecialAssistance) {
		this.needSpecialAssistance = needSpecialAssistance;
	}

	public boolean getNeedMealService()
	{
		return needMealService;
	}
	
	@Override
	public void setNeedMealService(boolean needMealService) {
		this.needMealService = needMealService;
	}

	@Override
	public void confirmReservation() throws MissingInputException {
		
		if(getCustomer().getCustomerName().isEmpty())
		{
			throw new MissingInputException("!! Error... Customer Name is Missing !!");
		}
		if(getCustomer().getCustomerEmail().isEmpty()) 
		{
			throw new MissingInputException("!! Error... Customer Email is Missing !!");
		}
		if(getCustomer().getCustomerPhone().isEmpty()) 
		{
			throw new MissingInputException("!! Error... Customer Phone Number is Missing !!");
		}
		if(seat.getSeatNumber() == null) 
		{
			throw new MissingInputException("!! Error... Seat is Not Selected !!");
		}
		
		System.out.println("\n[SYSTEM] - Flight Reservation Confirmed :");
		System.out.println(this.toString());
		
	}

	@Override
	public void cancelReservation() {
		
		System.out.println("\n[SYSTEM] - Flight Reservation Canceled !!");
		
	}
	
	// overriding toString method to return stringify version of Flight Reservation details
	@Override
	public String toString() {
		String s = 
		"\nFlight Reservation Id : " + this.getReservationId() + " -----" +
		"\n" +
		this.flight.toString() +
		"\n" +
		
				"Customer : " + 
				this.customer.getCustomerName() + " | " + 
				this.customer.getCustomerEmail() + " | " +
				this.customer.getCustomerPhone()
		 +
		"\n" +
		 	this.seat.toString() + 
		"\n" +
		
				"Additional Services : " +
				"\n needMealService : " + this.needMealService +
				"\n needSpecialAssistance : " + this.needSpecialAssistance
		;
		return s;
	}
}
