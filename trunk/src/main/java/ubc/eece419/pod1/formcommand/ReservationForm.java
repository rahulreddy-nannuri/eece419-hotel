package ubc.eece419.pod1.formcommand;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ubc.eece419.pod1.entity.PaymentInfo;
import ubc.eece419.pod1.entity.RoomType;

public class ReservationForm {
	int state = 0;

	PaymentInfo paymentInfo = new PaymentInfo();

	// room type id
	int type;
	RoomType roomType;
	double price;

	String checkIn = "";
	String checkOut = "";

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getType() {
		return type;
	}

	public void setType(int id) {
		this.type = id;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}

	public Date getCheckInDate() {
		return parseDateString(checkIn);
	}

	public String getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(String checkOut) {
		this.checkOut = checkOut;
	}

	public Date getCheckOutDate() {
		return parseDateString(checkOut);
	}

	private Date parseDateString(String date)  {
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

		try {
			return dateFormat.parse(date);
		} catch(ParseException e) {
			// we can't do anything without a date, but...
			return null;
		}
	}

	public PaymentInfo getPaymentInfo() {
		return paymentInfo;
	}
}
