/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ubc.eece419.pod1.entity;

import java.util.Date;

/**
 *
 * @author yang
 */
public class Reservation extends AbstractEntity<Reservation> implements Billable {

	private String name;
	private Double price;
	private String description;
	private RoomType roomType;
	private Date checkIn;
	private Date checkOut;

	public Date getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}

	public Date getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}

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
