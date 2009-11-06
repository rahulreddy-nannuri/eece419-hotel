package ubc.eece419.pod1.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
@NamedQuery(name="Reservation.findUncheckedInReservationsByUser",
            query="SELECT r " +
                  "FROM Reservation r " +
                  "WHERE r.user = :user " +
                  "AND r NOT IN ( SELECT s.reservation "+
				  "					FROM StayRecord s "+
				  "					WHERE s.user = :user )")
})
public class Reservation extends AbstractEntity<Reservation> implements Billable {
	private static final long serialVersionUID = 1L;

	private String name;
	private Double price;
	private String description;
	private RoomType roomType;
	private Date checkIn;
	private Date checkOut;
	private User user;

	@JoinColumn(nullable = false)
	@ManyToOne
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Temporal(TemporalType.DATE)
	public Date getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}

	@Temporal(TemporalType.DATE)
	public Date getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}

	@JoinColumn(nullable = false)
	@ManyToOne
	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public void setPrice(Double Price) {
		this.price = Price;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Double getPrice() {
		return price;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
