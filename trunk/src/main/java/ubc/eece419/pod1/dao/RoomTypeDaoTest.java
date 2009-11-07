package ubc.eece419.pod1.dao;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.joda.time.DateMidnight;
import org.junit.Test;

import ubc.eece419.pod1.dao.RoomTypeDao.IntDayMap;
import ubc.eece419.pod1.entity.Reservation;
import ubc.eece419.pod1.entity.RoomType;

public class RoomTypeDaoTest {

	private static final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	Mockery context = new Mockery();

	public static Date date(String ymd) {
		try {
			return df.parse(ymd);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testNights() {
		// simple
		Date start = date("01/01/2009");
		Date end = date("02/01/2009");
		List<DateMidnight> nights = IntDayMap.nights(start, end);
		assertEquals(1, nights.size());
		assertEquals(start.getTime(), nights.get(0).getMillis());

		// year boundary
		start = date("27/12/2009");
		end = date("02/01/2010");
		nights = IntDayMap.nights(start, end);
		assertEquals(6, nights.size());
		assertEquals(start.getTime(), nights.get(0).getMillis());
		assertEquals(end.getTime() - 1000*60*60*24, nights.get(5).getMillis());
	}

	@Test
	public void testNumberAvailableAllEmpty() {
		final RoomType roomType = new RoomType();

		final RoomTypeDao rtd = new RoomTypeDao();
		rtd.em = context.mock(EntityManager.class);

		final Query totalCountQuery = context.mock(Query.class, "totalCountQuery");
		final Query occupiedQuery = context.mock(Query.class, "occupiedQuery");

		context.checking(new Expectations() {{
			one(rtd.em).createQuery(with(startsWith("select count(r) from Room r")));
			will(returnValue(totalCountQuery));

			one(totalCountQuery).setParameter("type", roomType);
			will(returnValue(totalCountQuery));

			one(totalCountQuery).getSingleResult();
			will(returnValue(5)); // we have 5 rooms

			one(rtd.em).createQuery(with(startsWith("select r from Reservation r")));
			will(returnValue(occupiedQuery));

			allowing(occupiedQuery).setParameter(with(startsWith("check")), with(any(Date.class)), with(TemporalType.DATE));
			will(returnValue(occupiedQuery));

			one(occupiedQuery).setParameter("type", roomType);
			will(returnValue(occupiedQuery));

			one(occupiedQuery).getResultList();
			will(returnValue(Collections.EMPTY_LIST));
		}});

		int num = rtd.numberAvailable(roomType, date("01/01/2009"), date("01/02/2009"));

		assertEquals(5, num);
	}

	static Reservation reservation(String start, String end) {
		Reservation r = new Reservation();
		r.setCheckIn(date(start));
		r.setCheckOut(date(end));
		return r;
	}

	@Test
	public void testNumberAvailable() {
		final RoomType roomType = new RoomType();

		final RoomTypeDao rtd = new RoomTypeDao();
		rtd.em = context.mock(EntityManager.class);

		final Query totalCountQuery = context.mock(Query.class, "totalCountQuery");
		final Query occupiedQuery = context.mock(Query.class, "occupiedQuery");

		context.checking(new Expectations() {{
			one(rtd.em).createQuery(with(startsWith("select count(r) from Room r")));
			will(returnValue(totalCountQuery));

			one(totalCountQuery).setParameter("type", roomType);
			will(returnValue(totalCountQuery));

			one(totalCountQuery).getSingleResult();
			will(returnValue(5)); // we have 5 rooms

			one(rtd.em).createQuery(with(startsWith("select r from Reservation r")));
			will(returnValue(occupiedQuery));

			allowing(occupiedQuery).setParameter(with(startsWith("check")), with(any(Date.class)), with(TemporalType.DATE));
			will(returnValue(occupiedQuery));

			one(occupiedQuery).setParameter("type", roomType);
			will(returnValue(occupiedQuery));

			one(occupiedQuery).getResultList();
			will(returnValue(Arrays.asList(
					reservation("25/12/2008", "25/02/2009"), // entirely outside
					reservation("12/01/2009", "17/01/2009"), // entirely inside
					reservation("25/12/2008", "17/01/2009"), // partially before
					reservation("12/01/2009", "25/02/2009")))); // partially after
		}});

		int num = rtd.numberAvailable(roomType, date("01/01/2009"), date("01/02/2009"));

		assertEquals(1, num); // all 4 reservations cover the 15th of January
	}

}
