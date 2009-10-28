package ubc.eece419.pod1.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
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

	
	@JoinColumn(nullable=false)
	@ManyToOne
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	@JoinColumn(nullable=false)
	@ManyToOne
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}



	@Override
	@Transient
	public String getDescription() {
		//TODO: generate dynamic description
		return "2 night stay at room 103";
	}

	@Override
	@Transient
	public String getName() {
		return "Stay";
	}

	@Override
	@Transient
	public Double getPrice() {
		//TODO: calculate price based on check-in and check-out date
		return 100.0;
	}
}
