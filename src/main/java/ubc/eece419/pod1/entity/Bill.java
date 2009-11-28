package ubc.eece419.pod1.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Bill extends AbstractEntity<Bill> {
	private static final long serialVersionUID = 1L;

	private Reservation reservation;

	private boolean includesReservation = false;
	private boolean includesStayRecord = false;

	private Set<ChargeableItem> chargeableItems = new HashSet<ChargeableItem>();

	protected Bill() {
		// JPA ctor.
	}

	public Bill(Reservation reservation) {
		this.reservation = reservation;

		chargeableItems.addAll(reservation.getChargeableItems());

		for (Bill other : reservation.getBills()) {
			chargeableItems.removeAll(other.getChargeableItems());
			includesReservation |= other.getIncludesReservation();
			includesStayRecord |= other.getIncludesStayRecord();
		}

		// only if no other bill does
		includesReservation = !includesReservation;
		includesStayRecord = (!includesStayRecord && reservation.getStayRecord() != null);

		this.reservation.getBills().add(this);
	}

	@ManyToOne
	@JoinColumn(nullable=false)
	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	@OneToMany
	public Set<ChargeableItem> getChargeableItems() {
		return chargeableItems;
	}

	public void setChargeableItems(Set<ChargeableItem> chargeableItems) {
		this.chargeableItems = chargeableItems;
	}

	public boolean getIncludesReservation() {
		return includesReservation;
	}

	public void setIncludesReservation(boolean includesReservation) {
		this.includesReservation = includesReservation;
	}

	public boolean getIncludesStayRecord() {
		return includesStayRecord;
	}

	public void setIncludesStayRecord(boolean includesStayRecord) {
		this.includesStayRecord = includesStayRecord;
	}

	@Transient
	public List<Billable> getAllBillables() {
		List<Billable> list = new ArrayList<Billable>();
		if (includesReservation) {
			list.add(reservation);
		}
		if (includesStayRecord) {
			list.add(reservation.getStayRecord());
		}
		list.addAll(chargeableItems);
		return list;
	}

	@Transient
	public double getTotal() {
		double total = 0;
		if (includesReservation) {
			total += reservation.getPrice();
		}
		if (includesStayRecord) {
			total += reservation.getStayRecord().getPrice();
		}
		for (Billable item : getChargeableItems()) {
			total += item.getPrice();
		}
		return total;
	}

}
