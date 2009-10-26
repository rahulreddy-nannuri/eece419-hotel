package ubc.eece419.pod1.dao;

import java.util.Date;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import ubc.eece419.pod1.entity.RoomType;


@Repository
public class RoomTypeDao extends GenericDao<RoomType> implements RoomTypeRepository {

	@Override
	public int numberAvailable(RoomType type, Date checkIn, Date checkOut) {
		// TODO update this once we add reservations
		Query q = em.createQuery("select count(r) from Room r where r.roomType = :type");
		q.setParameter("type", type);
		return ((Number) q.getSingleResult()).intValue();
	}

}
