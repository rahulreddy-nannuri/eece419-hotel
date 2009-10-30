package ubc.eece419.pod1.dao;

import javax.persistence.Query;
import org.springframework.stereotype.Repository;

import ubc.eece419.pod1.entity.Room;
import ubc.eece419.pod1.entity.RoomType;

@Repository
public class RoomDao extends GenericDao<Room> implements RoomRepository {

	@Override
	public Room findAvailableRoomByRoomType(RoomType roomType) {
		Query q = em.createNamedQuery("Room.findAvailableRoomByRoomType");
		q.setParameter("roomType", roomType);
		if (q.getResultList().size() == 0) {
			return null;
		}
		return (Room) q.getResultList().get(0);
	}
}
