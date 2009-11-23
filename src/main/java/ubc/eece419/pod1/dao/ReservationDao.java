package ubc.eece419.pod1.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ubc.eece419.pod1.entity.Reservation;
import ubc.eece419.pod1.entity.User;

public class ReservationDao extends GenericDao<Reservation> implements ReservationRepository {
	
	@Override
	public List<Reservation> findAll(String filter) {
		if(filter != null) {
			if(filter.equals("past")) {
				return findPastReservations();
			} else if(filter.equals("future")) {
				return findFutureReservations();
			}
		}
		
		return findAll();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Reservation> findAll() {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(getEntityClass());
		return criteria.addOrder(Order.desc("checkIn")).list();
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Reservation> findPastReservations() {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(getEntityClass());
		return criteria.add(Restrictions.lt("checkIn", (new Date())))
			.addOrder(Order.desc("checkIn"))
			.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Reservation> findFutureReservations() {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(getEntityClass());
		return criteria.add(Restrictions.ge("checkIn", (new Date())))
			.addOrder(Order.desc("checkIn"))
			.list();
	}
}
