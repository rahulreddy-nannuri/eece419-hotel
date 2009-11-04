package ubc.eece419.pod1.dao;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

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

	@Override
	@SuppressWarnings("unchecked")
	public List<RoomType> findByPriceAndOccupancyAndAttributes(double minPrice, double maxPrice, int occupancy, Map<String, Integer> attributes) {

		Set<Long> allowedIds = null;
		for (Entry<String, Integer> attr : attributes.entrySet()) {
			// I couldn't figure out how to do this in EJBQL, since RoomType_attributes isn't an entity...
			List<Number> ids = em.createNativeQuery(
					"select RoomType_id from RoomType_attributes where element = :name group by RoomType_id having count(*) >= :count")
					.setParameter("name", attr.getKey()).setParameter("count", attr.getValue()).getResultList();

			Set<Long> newIds = new HashSet<Long>();
			for (Number id : ids)
				newIds.add(id.longValue());

			if (allowedIds == null) {
				allowedIds = newIds;
			} else {
				allowedIds.retainAll(newIds);
			}
			if (allowedIds.size() == 0) {
				return Collections.emptyList();
			}
		}

		StringBuilder qstr = new StringBuilder();
		qstr.append("select r from RoomType r where r.dailyRate between :minPrice and :maxPrice");
		qstr.append(" and r.maxOccupancy >= :occupancy");
		if (allowedIds != null)	{
			Iterator<Long> idIter = allowedIds.iterator();
			if (!idIter.hasNext()) return Collections.emptyList();

			qstr.append(" and r.id in (").append(idIter.next());
			while (idIter.hasNext()) {
				qstr.append(",").append(idIter.next());
			}
			qstr.append(")");
		}
		Query query = em.createQuery(qstr.toString());
		query.setParameter("minPrice", minPrice);
		query.setParameter("maxPrice", maxPrice);
		query.setParameter("occupancy", occupancy);
		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> allAttributes() {
		// again, a native query
		return em.createNativeQuery("select distinct element from RoomType_attributes").getResultList();
	}

}
