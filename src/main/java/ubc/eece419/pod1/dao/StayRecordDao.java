package ubc.eece419.pod1.dao;

import java.util.List;

import javax.persistence.Query;
import ubc.eece419.pod1.entity.StayRecord;
import ubc.eece419.pod1.entity.User;

public class StayRecordDao extends GenericDao<StayRecord> implements
		StayRecordRepository {

	@Override
	public List<StayRecord> findUncheckedOutStayRecordsByUser(User user) {
		Query q=em.createQuery("select s from StayRecord s where s.user = :user and s.checkOutDate = null");
		q.setParameter("user", user);
		return (List<StayRecord>)q.getResultList();
	}
}
