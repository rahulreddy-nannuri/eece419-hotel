package ubc.eece419.pod1.dao;

import java.util.Date;
import ubc.eece419.pod1.entity.Room;
import ubc.eece419.pod1.entity.RoomType;

public interface RoomRepository extends GenericRepository<Room> {
	Room findAvailableRoom(RoomType roomType,Date checkIn, Date checkOut);
}
