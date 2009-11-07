package ubc.eece419.pod1.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.joda.time.DateMidnight;
import org.springframework.stereotype.Repository;

import ubc.eece419.pod1.entity.Reservation;
import ubc.eece419.pod1.entity.RoomType;

@Repository
public class RoomTypeDao extends GenericDao<RoomType> implements RoomTypeRepository {
	private static final Logger log = Logger.getLogger(RoomTypeDao.class.getName());

	static class IntDayMap {
		private final DateMidnight[] nights;
		private final int[] values;

		public IntDayMap(Date start, Date end) {
			nights = nights(start, end).toArray(new DateMidnight[] {});
			values = new int[nights.length];
		}

		public int get(Date key) {
			return get(new DateMidnight(key));
		}

		public int get(DateMidnight key) {
			int idx = Arrays.binarySearch(nights, key);
			return values[idx];
		}

		public int put(Date key, int value) {
			return put(new DateMidnight(key), value);
		}

		public int put(DateMidnight key, int value) {
			int idx = Arrays.binarySearch(nights, key);
			int oldval = values[idx];
			values[idx] = value;
			return oldval;
		}

		public void incrementRange(Date startDate, Date endDate) {
			DateMidnight start = new DateMidnight(startDate);
			DateMidnight end = new DateMidnight(endDate);
			int idx = 0;
			while (idx < nights.length && nights[idx].isBefore(start)) {
				idx++;
			}
			while (idx < nights.length && nights[idx].isBefore(end)) {
				values[idx]++;
				idx++;
			}
		}

		public int maxValue() {
			int max = Integer.MIN_VALUE;
			for (int i = 0; i < values.length; i++) {
				if (values[i] > max) {
					max = values[i];
				}
			}
			return max;
		}

		public static List<DateMidnight> nights(Date start, Date end) {
			List<DateMidnight> list = new ArrayList<DateMidnight>();
			DateMidnight d = new DateMidnight(start);
			DateMidnight fin = new DateMidnight(end);
			while (d.isBefore(fin)) {
				list.add(d);
				d = d.plusDays(1);
			}
			return list;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public int numberAvailable(RoomType type, Date checkIn, Date checkOut) {
		// TODO push this all into SQL, combine with findByPriceAndOccupancyAndAttributes

		log.info("checking availability: " + type + " (" + checkIn + " to " + checkOut + ")");

		Query q = em.createQuery("select count(r) from Room r where r.roomType = :type");
		q.setParameter("type", type);
		int total = ((Number) q.getSingleResult()).intValue();

		q = em.createQuery("select r from Reservation r where r.roomType = :type "
				+ "and ((r.checkIn between :checkIn and :checkOut) or (r.checkOut between :checkIn and :checkOut)"
				+ "or (:checkIn between r.checkIn and r.checkOut) or (:checkOut between r.checkIn and r.checkOut))");
		q.setParameter("checkIn", checkIn, TemporalType.DATE);
		q.setParameter("checkOut", checkOut, TemporalType.DATE);
		q.setParameter("type", type);
		List<Reservation> reservations = q.getResultList();

		log.info(reservations.size() + " possibly conflicting reservations found");
		IntDayMap map = new IntDayMap(checkIn, checkOut);
		for (Reservation r : reservations) {
			map.incrementRange(r.getCheckIn(), r.getCheckOut());
		}
		return total - map.maxValue();
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
