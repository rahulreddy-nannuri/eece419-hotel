package ubc.eece419.pod1.entity;

import static org.junit.Assert.*;

import org.joda.time.DateMidnight;
import org.junit.Test;

public class ReservationTest {

	@Test
	public void testGetPrice() {
		RoomType rt = new RoomType();
		rt.setDailyRate(100d);

		Reservation res = new Reservation(null, null, rt,
				new DateMidnight(2009, 9, 1).toDate(),
				new DateMidnight(2009, 9, 2).toDate());

		assertEquals(100d, res.getPrice(), 1e-10);

		res = new Reservation(null, null, rt,
				new DateMidnight(2009, 9, 4).toDate(),
				new DateMidnight(2009, 9, 7).toDate());

		assertEquals(300d, res.getPrice(), 1e-10);
	}

}
