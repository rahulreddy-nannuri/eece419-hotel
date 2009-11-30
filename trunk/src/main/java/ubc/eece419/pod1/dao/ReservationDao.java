package ubc.eece419.pod1.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import ubc.eece419.pod1.entity.Reservation;
import ubc.eece419.pod1.entity.User;

public class ReservationDao extends GenericDao<Reservation> implements ReservationRepository {
	
	@Autowired
	UserRepository userRepository;

	@SuppressWarnings("unchecked")
	@Override
	public List<Reservation> findAll(String filter) {
		if(filter != null) {
			if(filter.equals("past")) {
				return em.createNamedQuery("Reservation.findCheckedOut").getResultList();
			} else if (filter.equals("future")) {
				return em.createNamedQuery("Reservation.findNotCheckedIn").getResultList();
			} else if (filter.equals("current")) {
				return em.createNamedQuery("Reservation.findCheckedIn").getResultList();
			} else if (filter.startsWith("u|") && filter.length() > 2) {
				String username = filter.substring(2);
				User user = userRepository.loadUserByUsername(username);
				return findReservationsByUser(user);
			}
		}
		return findAll();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Reservation> findAll() {
		return em.createNamedQuery("Reservation.findAll").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Reservation> findUncheckedInReservationsByUser(User user) {
		Query q = em.createNamedQuery("Reservation.findUncheckedInReservationsByUser");
		q.setParameter("user", user);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Reservation> findReservationsByUser(User user) {
		Query q = em.createNamedQuery("Reservation.findReservationsByUser");
		q.setParameter("user", user);
		return q.getResultList();
	}
}
