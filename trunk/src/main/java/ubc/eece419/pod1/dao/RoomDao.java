package ubc.eece419.pod1.dao;

import java.util.Date;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

import ubc.eece419.pod1.entity.Room;
import ubc.eece419.pod1.entity.RoomType;

@Repository
public class RoomDao extends GenericDao<Room> implements RoomRepository {

	@Override
	public Room findAvailableRoom(RoomType roomType, Date checkIn, Date checkOut) {
		//TODO: make this right
		Query q = em.createQuery("select r from Room r where r.roomType = :type");
		q.setParameter("type", roomType);

		return (Room)q.getSingleResult();
	}
}
