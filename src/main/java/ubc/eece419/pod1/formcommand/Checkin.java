package ubc.eece419.pod1.formcommand;

import java.util.List;
import ubc.eece419.pod1.entity.Reservation;

public class Checkin {
	String username;
	List<Reservation> reservations;
	long selectedReservation;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public long getSelectedReservation() {
		return selectedReservation;
	}

	public void setSelectedReservation(long selectedReservation) {
		this.selectedReservation = selectedReservation;
	}

	

	
}
