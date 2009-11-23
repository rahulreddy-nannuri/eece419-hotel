package ubc.eece419.pod1.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.joda.time.DateTime;
import org.joda.time.Days;

@Entity
@NamedQueries({
	@NamedQuery(name = "Reservation.findUncheckedInReservationsByUser",
	query = "SELECT r " +
	"FROM Reservation r " +
	"WHERE r.user = :user " +
	"AND r.stayRecord = null"),
	@NamedQuery(name = "Reservation.findReservationsByUser",
	query = "SELECT r FROM Reservation r "+
			"WHERE r.user= :user")
})
public class Reservation extends AbstractEntity<Reservation> implements Billable {

	private static final long serialVersionUID = 1L;
	private Double price;
	private RoomType roomType;
	private Date checkIn;
	private Date checkOut;
	private User user;

	private PaymentInfo paymentInfo;
	private StayRecord stayRecord;

	protected Reservation() {
		// JPA ctor.
	}

	public Reservation(User user, PaymentInfo paymentInfo, RoomType roomType, Date checkIn, Date checkOut) {
		this.user = user;
		this.paymentInfo = paymentInfo;
		this.roomType = roomType;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.price = calculatePrice(roomType, checkIn, checkOut);
	}

	public static double calculatePrice(RoomType roomType, Date checkIn, Date checkOut) {
		Days duration = Days.daysBetween(new DateTime(checkIn), new DateTime(checkOut));
		return roomType.getDailyRate() * duration.getDays();
	}

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

	@Override
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double Price) {
		this.price = Price;
	}

	@Column(nullable=false)
	@Embedded
	public PaymentInfo getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(PaymentInfo paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	@Transient
	@Override
	public String getName() {
		Days duration = Days.daysBetween(new DateTime(checkIn), new DateTime(checkOut));
		return duration.getDays() + "-day reservation";
	}

	@Transient
	public String getDescription() {
		SimpleDateFormat fmt = new SimpleDateFormat("MMM d, yyyy"); // matches the default <fmt:formatDate>
		return roomType.getName() + " (" + fmt.format(checkIn) + " - " + fmt.format(checkOut) + ")";
	}

	@OneToOne(fetch = FetchType.EAGER)
	public StayRecord getStayRecord() {
		return stayRecord;
	}

	public void setStayRecord(StayRecord stayRecord) {
		if (this.stayRecord != stayRecord) {
			this.stayRecord = stayRecord;
			this.stayRecord.setReservation(this);
		}
	}
}
