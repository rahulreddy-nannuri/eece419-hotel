package ubc.eece419.pod1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Room extends AbstractEntity<Room> {
	private static final long serialVersionUID = 1L;

	// should this instead be a string, as for '#206A'?
	private int number;
	private String description;
	private RoomType roomType;

	public Room() {
	}

	@JoinColumn(nullable=false)
	@ManyToOne
	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		// can't do this, because the EntityEditor will call setRoomType outside of a transaction
//		if (roomType != null) {
//			roomType.getRooms().add(this);
//		}
		this.roomType = roomType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(unique=true)
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}
