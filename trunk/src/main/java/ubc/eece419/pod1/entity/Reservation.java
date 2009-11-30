package ubc.eece419.pod1.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.joda.time.DateTime;
import org.joda.time.Days;

import ubc.eece419.pod1.validator.NonNegative;

@Entity
@NamedQueries({
@NamedQuery(name = "Reservation.findUncheckedInReservationsByUser",
	query = "SELECT r " +
	"FROM Reservation r " +
	"WHERE r.user = :user " +
	"AND r.stayRecord = null"),
@NamedQuery(
	name = "Reservation.findReservationsByUser",
	query = "SELECT r FROM Reservation r WHERE r.user= :user order by r.checkIn desc"),
@NamedQuery(
	name = "Reservation.findAll",
	query = "select r from Reservation r order by r.checkIn desc"),
@NamedQuery(
	name = "Reservation.findNotCheckedIn",
	query = "select r from Reservation r where r.stayRecord is null order by r.checkIn"),
@NamedQuery(
	name = "Reservation.findCheckedIn",
	query = "select r from Reservation r where r.stayRecord.checkOutDate is null order by r.checkIn"),
@NamedQuery(
	name = "Reservation.findCheckedOut",
	query = "select r from Reservation r where r.stayRecord.checkOutDate is not null order by r.checkIn")
})
public class Reservation extends AbstractEntity<Reservation> implements Billable {

	private static final long serialVersionUID = 1L;
	private Double quotedPrice;
	private RoomType roomType;
	private Date checkIn;
	private Date checkOut;
	private User user;

	private PaymentInfo paymentInfo;
	private StayRecord stayRecord;

	private Set<Bill> bills = new HashSet<Bill>();
	private Set<ChargeableItem> chargeableItems = new HashSet<ChargeableItem>();

	protected Reservation() {
		// JPA ctor.
	}

	public Reservation(User user, PaymentInfo paymentInfo, RoomType roomType, Date checkIn, Date checkOut) {
		this.user = user;
		this.paymentInfo = paymentInfo;
		this.roomType = roomType;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.quotedPrice = calculatePrice(roomType, checkIn, checkOut);
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

	@NonNegative
	public Double getQuotedPrice() {
		return quotedPrice;
	}

	public void setQuotedPrice(Double quotedPrice) {
		this.quotedPrice = quotedPrice;
	}

	@Transient
	public Double getPrice() {
		if (this.stayRecord != null) {
			return 0d; // a reservation is free if you use it
		} else {
			return this.getCancellationFee();
		}
	}

	// I'm just making this up
	@Transient
	public Double getCancellationFee() {
		return roomType.getDailyRate() * 0.25;
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

	@OneToMany(fetch=FetchType.EAGER)
	public Set<Bill> getBills() {
		return bills;
	}

	public void setBills(Set<Bill> bills) {
		this.bills = bills;
	}

	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	public Set<ChargeableItem> getChargeableItems() {
		return chargeableItems;
	}

	public void setChargeableItems(Set<ChargeableItem> chargeableItems) {
		this.chargeableItems = chargeableItems;
	}

	@Transient
	public boolean isCheckedIn() {
		return stayRecord != null;
	}

	@Transient
	public boolean isCheckedOut() {
		return stayRecord != null && stayRecord.isCheckedOut();
	}

	@Transient
	public boolean isBillRequired() {
		return false;
	}

}
