package laba.travelagency.server;

import java.util.ArrayList;
import java.util.List;

public class ReservationList<T> {
	private List<T> reservationList;
	private double totalAmount;

	public ReservationList() {
		reservationList = new ArrayList<>();
	}

	public void addReservation(T reservation, double amount) {
		if (reservation != null) {
			reservationList.add(reservation);
			this.setTotalAmount(this.totalAmount + amount);
		}
	}
	
	public void removeReservation(T reservation) {
		if(reservationList.contains(reservation))
		{
			reservationList.remove(reservation);
		}
	}

	public List<T> getReservationList() {
		return reservationList;
	}
	
	private void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public double getTotalAmount() {
		return this.totalAmount;
	} 
}
