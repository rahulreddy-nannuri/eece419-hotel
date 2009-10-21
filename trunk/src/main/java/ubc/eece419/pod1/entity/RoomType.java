package ubc.eece419.pod1.entity;
import javax.persistence.Entity;


@Entity
public class RoomType extends AbstractEntity<RoomType> {
	private static final long serialVersionUID = 1L;
	
	private Integer maxOccupancy;
	private String description;
	private Double dailyRate;
	private String name;
	
	public RoomType() {
		
	}

	public Integer getMaxOccupancy() {
		return maxOccupancy;
	}

	public void setMaxOccupancy(Integer maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getDailyRate() {
		return dailyRate;
	}

	public void setDailyRate(Double dailyRate) {
		this.dailyRate = dailyRate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
