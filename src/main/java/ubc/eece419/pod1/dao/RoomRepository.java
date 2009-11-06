package ubc.eece419.pod1.dao;

import ubc.eece419.pod1.entity.Room;
import ubc.eece419.pod1.entity.RoomType;

public interface RoomRepository extends GenericRepository<Room> {
	Room findAvailableRoomByRoomType(RoomType roomType);
}
