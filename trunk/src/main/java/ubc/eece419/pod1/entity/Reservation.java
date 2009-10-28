/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ubc.eece419.pod1.entity;

/**
 *
 * @author yang
 */
public class Reservation extends AbstractEntity<Reservation> implements Billable {

	private String name;
	private Double price;
	private String description;

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
