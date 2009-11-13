package ubc.eece419.pod1.dao;

import java.util.List;
import javax.persistence.Query;
import ubc.eece419.pod1.entity.Reservation;
import ubc.eece419.pod1.entity.User;

public class ReservationDao extends GenericDao<Reservation> implements ReservationRepository {

	@SuppressWarnings("unchecked")
	@Override
	public List<Reservation> findUncheckedInReservationsByUser(User user) {
		Query q = em.createNamedQuery("Reservation.findUncheckedInReservationsByUser");
		q.setParameter("user", user);
		return q.getResultList();
	}

	@Override
	public List<Reservation> findReservationsByUser(User user) {
		Query q = em.createNamedQuery("Reservation.findReservationsByUser");
		q.setParameter("user", user);
		return q.getResultList();
	}
}
