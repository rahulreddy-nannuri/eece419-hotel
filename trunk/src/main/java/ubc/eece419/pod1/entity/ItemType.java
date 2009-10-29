package ubc.eece419.pod1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;


@Entity
public class ItemType extends AbstractEntity<ItemType> {

	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private String sku;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(unique=true)
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	private Double price;

	public ItemType() {
	}



}

