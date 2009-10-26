package ubc.eece419.pod1.dao;

import java.util.Date;

import ubc.eece419.pod1.entity.RoomType;

public interface RoomTypeRepository extends GenericRepository<RoomType> {

	int numberAvailable(RoomType type, Date checkIn, Date checkOut);

}
