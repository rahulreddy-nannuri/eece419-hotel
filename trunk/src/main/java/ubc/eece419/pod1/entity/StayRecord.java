package ubc.eece419.pod1.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class StayRecord extends AbstractEntity<StayRecord> implements Billable {

	private static final long serialVersionUID = 7095043273136850580L;
	Date checkInDate;
	Date checkOutDate;
	String billDescription;
	String billName;
	Room room;
	User user;
	Reservation reservation;

	@JoinColumn(nullable = false)
	@ManyToOne
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@JoinColumn(nullable = false)
	@ManyToOne
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	@Temporal(TemporalType.DATE)
	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	@Temporal(TemporalType.DATE)
	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	@JoinColumn(nullable = false)
	@OneToOne
	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	@Override
	@Transient
	public String getDescription() {
		return calculateDuration() + " night(s) stay at " + room.getRoomType().getName();
	}

	@Override
	@Transient
	public String getName() {
		return "stay record for reservation " + reservation.getName();
	}

	@Override
	@Transient
	public Double getPrice() {
		int nights=calculateDuration();
		double cost=nights*reservation.getRoomType().getDailyRate();
		return cost;
	}

	@Transient
	public boolean isCheckedOut() {
		return checkOutDate != null;
	}

	private int calculateDuration() {
		int nights;
		Date in;
		Date out;
		if (isCheckedOut()) {
			in = checkInDate;
			out = checkOutDate;
		} else {
			in = reservation.getCheckIn();
			out = reservation.getCheckOut();
		}
		nights = (int) ((out.getTime() - in.getTime()) / 1000 / 3600 / 24);
		return nights;
	}
}
