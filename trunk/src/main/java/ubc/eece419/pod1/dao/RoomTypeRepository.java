package ubc.eece419.pod1.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import ubc.eece419.pod1.entity.RoomType;

public interface RoomTypeRepository extends GenericRepository<RoomType> {

	int numberAvailable(RoomType type, Date checkIn, Date checkOut);

	List<RoomType> findByPriceAndOccupancyAndAttributes(double minPrice, double maxPrice, int occupancy, Map<String, Integer> attributes);

	List<String> allAttributes();

}
