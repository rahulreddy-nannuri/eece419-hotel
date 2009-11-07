package ubc.eece419.pod1.dao;

import java.util.List;


import ubc.eece419.pod1.entity.StayRecord;
import ubc.eece419.pod1.entity.User;

public interface StayRecordRepository extends GenericRepository<StayRecord> {
	List<StayRecord> findUncheckedOutStayRecordsByUser(User user);
	List<Object[]> getReserveCountByMonth();
}
