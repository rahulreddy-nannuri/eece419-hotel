package ubc.eece419.pod1.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.joda.time.DateTime;
import org.joda.time.Days;

@Entity
public class StayRecord extends AbstractEntity<StayRecord> implements Billable {
	private static final long serialVersionUID = 7095043273136850580L;

	private Date checkInDate;
	private Date checkOutDate;
	private Room room;
	private User user;
	private Reservation reservation;

	@JoinColumn(nullable = false)
	@ManyToOne
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@JoinColumn(nullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
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
	@OneToOne(fetch = FetchType.EAGER)
	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	@Override
	@Transient
	public String getDescription() {
		SimpleDateFormat fmt = new SimpleDateFormat("MMM d, yyyy"); // matches the default <fmt:formatDate>
		return "#" + room.getNumber() + " (" + fmt.format(startDate()) + " - " + fmt.format(endDate()) + ")";
	}

	@Override
	@Transient
	public String getName() {
		Days duration = Days.daysBetween(new DateTime(startDate()), new DateTime(endDate()));
		return duration.getDays() + "-day stay";
	}

	@Override
	@Transient
	public Double getPrice() {
		return getReservation().getPrice();
	}

	@Transient
	public boolean isCheckedOut() {
		return checkOutDate != null;
	}

	private Date startDate() {
		return isCheckedOut() ? checkInDate : reservation.getCheckIn();
	}

	private Date endDate() {
		return isCheckedOut() ? checkOutDate : reservation.getCheckOut();
	}
}
