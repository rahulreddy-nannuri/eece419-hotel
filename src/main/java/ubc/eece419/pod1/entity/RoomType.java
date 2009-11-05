package ubc.eece419.pod1.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.CollectionOfElements;

@Entity
public class RoomType extends AbstractEntity<RoomType> {
	private static final long serialVersionUID = 1L;

	private Integer maxOccupancy;
	private String description;
	private Double dailyRate;
	private String name;
	private Set<Room> rooms;
	private List<String> attributes;
	private Long imageId;

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	
	@Column(nullable=false)
	public Integer getMaxOccupancy() {
		return maxOccupancy;
	}

	public void setMaxOccupancy(Integer maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}

	@Column(nullable=false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(nullable=false)
	public Double getDailyRate() {
		return dailyRate;
	}

	public void setDailyRate(Double dailyRate) {
		this.dailyRate = dailyRate;
	}

	@Column(unique=true, nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(mappedBy="roomType")
	public Set<Room> getRooms() {
		return rooms;
	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}

	// this is Hibernate-specific, not JPA-spec
	// eager load, because we don't have an OpenSessionInViewFilter configured
	@CollectionOfElements(fetch=FetchType.EAGER)
	public List<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<String> attributes) {
		this.attributes = attributes;
	}

	@Transient
	public String getAttributesText() {
		if (getAttributes() == null) return "";

		StringBuilder buf = new StringBuilder();
		for (String ra : getAttributes()) {
			if (buf.length() > 0) {
				buf.append("\n");
			}
			buf.append(ra);
		}
		return buf.toString();
	}

	public void setAttributesText(String text) {
		attributes = new ArrayList<String>();
		if (text != null) {
			String[] parts = text.split("\n+");
			for (String part : parts) {
				part = part.trim();
				if (part.length() > 0)
					attributes.add(part);
			}
		}
	}

}
