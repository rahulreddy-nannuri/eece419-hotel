package ubc.eece419.pod1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import ubc.eece419.pod1.validator.NonNegative;

@Entity
public class ItemType extends AbstractEntity<ItemType> {
	private static final long serialVersionUID = 1L;

	private String name;
	private String description;
	private String sku;
	private Double price;

	public ItemType() {
	}

	@Column(nullable = false)
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

	@Column(unique=true, nullable=false)
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	@NonNegative
	@Column(nullable=false)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}

