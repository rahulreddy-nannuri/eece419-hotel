package ubc.eece419.pod1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Room extends AbstractEntity<Room> {
	private static final long serialVersionUID = 1L;

	// should this instead be a string, as for '#206A'?
	private int number;
	private String description;

	public Room() {
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
