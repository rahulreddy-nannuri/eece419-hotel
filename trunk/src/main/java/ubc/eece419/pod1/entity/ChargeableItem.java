package ubc.eece419.pod1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ChargeableItem extends AbstractEntity<ChargeableItem> implements Billable {
	private static final long serialVersionUID = 1L;

	private String name;
	private String description;
	private String sku;
	private Double price;

	public ChargeableItem() {
		// JPA ctor.
	}

	public ChargeableItem(ItemType itemType) {
		setName(itemType.getName());
		setDescription(itemType.getDescription());
		setSku(itemType.getSku());
		setPrice(itemType.getPrice());
	}

	@Column(nullable=false)
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

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	@Column(nullable=false)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
