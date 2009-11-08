package ubc.eece419.pod1.formcommand;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ubc.eece419.pod1.entity.RoomType;
import ubc.eece419.pod1.entity.User;

public class ReservationForm {
	int state = 0;
	String cardNumber = "";
	int expiryMonth = 0;
	int expiryYear = 0;
	// room type id
	int type = 0;
	RoomType roomType;
	double price = 0;
	String securityCode = "";
	String cardType = "";
	String checkIn = "";
	String checkOut = "";
	String username = "";
	String password = "";
	User user;

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

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String creditCardNumber) {
		this.cardNumber = creditCardNumber;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public int getExpiryMonth() {
		return expiryMonth;
	}

	public void setExpiryMonth(int expiryMonth) {
		this.expiryMonth = expiryMonth;
	}

	public int getExpiryYear() {
		return expiryYear;
	}

	public void setExpiryYear(int expiryYear) {
		this.expiryYear = expiryYear;
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
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	private Date parseDateString(String date)  {
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		
		try {
			return dateFormat.parse(date);
		} catch(ParseException e) {
			return new Date();
		}
	}
}
