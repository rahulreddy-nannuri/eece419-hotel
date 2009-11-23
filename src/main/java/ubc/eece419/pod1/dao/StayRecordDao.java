package ubc.eece419.pod1.dao;

import java.util.List;

import javax.persistence.Query;
import ubc.eece419.pod1.entity.StayRecord;
import ubc.eece419.pod1.entity.User;

public class StayRecordDao extends GenericDao<StayRecord> implements StayRecordRepository {

	@Override
	@SuppressWarnings("unchecked")
	public List<StayRecord> findUncheckedOutStayRecordsByUser(User user) {
		Query q=em.createQuery("select s from StayRecord s where s.reservation.user = :user and s.checkOutDate = null");
		q.setParameter("user", user);
		return q.getResultList();
	}


	@SuppressWarnings("unchecked")
	public List<Object[]> getReserveCountByMonthByType(int year) {
		Query q=em.createQuery("select sr.room.roomType.name, month(sr.checkInDate), count(*) from StayRecord sr where year(sr.checkInDate) = :year group by month(sr.checkInDate),sr.room.roomType.name order by sr.room.roomType.name, month(sr.checkInDate) asc").setParameter("year", year);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getReserveCountByMonth(int year) {
		Query q=em.createQuery("select month(sr.checkInDate), count(*) from StayRecord sr where year(sr.checkInDate) = :year group by month(sr.checkInDate) order by month(sr.checkInDate) asc").setParameter("year", year);
		return q.getResultList();
	}
}
