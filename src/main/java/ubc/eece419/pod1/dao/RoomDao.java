package ubc.eece419.pod1.dao;



import org.springframework.stereotype.Repository;

import ubc.eece419.pod1.entity.Room;

@Repository
public class RoomDao extends GenericDao<Room> implements RoomRepository {

	public RoomDao() {
		super(Room.class);
	}

}
