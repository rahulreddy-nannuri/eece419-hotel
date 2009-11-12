package ubc.eece419.pod1.dao;

import java.util.List;

import javax.persistence.Query;
import ubc.eece419.pod1.entity.StayRecord;
import ubc.eece419.pod1.entity.User;

public class StayRecordDao extends GenericDao<StayRecord> implements StayRecordRepository {

	@Override
	@SuppressWarnings("unchecked")
	public List<StayRecord> findUncheckedOutStayRecordsByUser(User user) {
		Query q=em.createQuery("select s from StayRecord s where s.user = :user and s.checkOutDate = null");
		q.setParameter("user", user);
		return q.getResultList();
	}
	

	public List<Object[]> getReserveCountByMonthByType() {
		Query q=em.createQuery("select sr.room.roomType.name, month(sr.checkInDate), count(*) from StayRecord sr group by month(sr.checkInDate),sr.room.roomType.name order by sr.room.roomType.name, month(sr.checkInDate) asc");
		return q.getResultList();
	}
	
	public List<Object[]> getReserveCountByMonth() {
		Query q=em.createQuery("select month(sr.checkInDate), count(*) from StayRecord sr group by month(sr.checkInDate) order by month(sr.checkInDate) asc");
		return q.getResultList();
	}
}
