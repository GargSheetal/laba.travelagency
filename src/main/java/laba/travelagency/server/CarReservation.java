/**
 * 
 */
package laba.travelagency.server;

import laba.travelagency.exceptions.MissingInputException;

/**
 * @author sheetal
 *
 */
public class CarReservation extends Reservation implements ICarReservation {

	private final Car car;
	private boolean needCarSeat;
	
	public CarReservation(Car car) {
		this.car = car;
	}
	
	public Car getCar() {
		return car;
	}
	
	public boolean getNeedCarSeat() {
		return needCarSeat;
	}

	@Override
	public void setNeedCarSeat(boolean needCarSeat) {
		this.needCarSeat = needCarSeat;
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
		
		System.out.println("\n[SYSTEM] - Car Reservation Confirmed : ");
		System.out.println(this.toString());
	}
	
	@Override
	public void cancelReservation() {
		System.out.println("\n[SYSTEM] - Car Reservation Cancelled !!");
		
	}

	// overriding toString method to return stringify version of Car Reservation details
	@Override
	public String toString() {
		String s = 
				"\nCar Reservation Id : " + this.getReservationId() + " -----" +
				"\n" +
				this.car.toString() +
				"\n" + 
				
					"Customer : " +
					this.customer.getCustomerName() + " | " +
					this.customer.getCustomerEmail() + " | " +
					this.customer.getCustomerPhone() +
				"\n" + 
				"Additional Services : " +
				"\n needCarSeat : " + this.needCarSeat
				;
		return s;
	}

}
