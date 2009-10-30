package ubc.eece419.pod1.config;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Map.Entry;

import java.util.logging.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import ubc.eece419.pod1.dao.ReservationRepository;
import ubc.eece419.pod1.dao.RoomRepository;
import ubc.eece419.pod1.dao.RoomTypeRepository;
import ubc.eece419.pod1.dao.StayRecordRepository;
import ubc.eece419.pod1.dao.UserRepository;
import ubc.eece419.pod1.entity.Reservation;
import ubc.eece419.pod1.entity.Room;
import ubc.eece419.pod1.entity.RoomType;
import ubc.eece419.pod1.entity.StayRecord;
import ubc.eece419.pod1.entity.User;

public class DatabasePopulator implements InitializingBean {

	private static final Logger log = Logger.getLogger(DatabasePopulator.class.getName());
	private List<User> usersToCreate = new ArrayList<User>();
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoomTypeRepository roomTypeRepository;
	@Autowired
	RoomRepository roomRepository;
	@Autowired
	StayRecordRepository stayRecordRepository;
	@Autowired
	ReservationRepository reservationRepository;

	public void setUsers(Properties users) {
		for (Entry<Object, Object> u : users.entrySet()) {
			User user = parseUser(u, "ROLE_USER");
			usersToCreate.add(user);
		}
	}

	public void setAdmins(Properties admins) {
		for (Entry<Object, Object> u : admins.entrySet()) {
			User user = parseUser(u, "ROLE_USER,ROLE_STAFF,ROLE_ADMIN");
			usersToCreate.add(user);

		}
	}

	public void setStaff(Properties staff) {
		for (Entry<Object, Object> u : staff.entrySet()) {
			User user = parseUser(u, "ROLE_USER,ROLE_STAFF");
			usersToCreate.add(user);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		if (roomTypeRepository.findAll().size() > 0) {
			log.info("Database already has RoomTypes, not adding defaults");
		} else {
			RoomType penthouse = new RoomType();
			penthouse.setName("Penthouse Suite");
			penthouse.setDescription("The ultimate in comfort.");
			penthouse.setDailyRate(750.0);
			penthouse.setMaxOccupancy(12);
			penthouse = roomTypeRepository.save(penthouse);

			RoomType bachelor = new RoomType();
			bachelor.setName("Bachelor Dive");
			bachelor.setDescription("Perfect for starving students.");
			bachelor.setDailyRate(20.0);
			bachelor.setMaxOccupancy(1);
			bachelor = roomTypeRepository.save(bachelor);

			RoomType standard = new RoomType();
			standard.setName("Corporate Econo-box");
			standard.setDescription("Comfort befitting Middle America.");
			standard.setDailyRate(80.0);
			standard.setMaxOccupancy(2);
			standard = roomTypeRepository.save(standard);

			RoomType doubleStandard = new RoomType();
			doubleStandard.setName("Double Econo-box");
			doubleStandard.setDescription("Twice the fun of a Corporate Econo-box.");
			doubleStandard.setDailyRate(120.0);
			doubleStandard.setMaxOccupancy(4);
			doubleStandard = roomTypeRepository.save(doubleStandard);

			{
				Room room = new Room();
				room.setNumber(1);
				room.setRoomType(penthouse);
				roomRepository.save(room);
			}

			for (int i = 101; i <= 110; i++) {
				Room room = new Room();
				room.setNumber(i);
				room.setRoomType(bachelor);
				roomRepository.save(room);
			}

			for (int i = 201; i <= 220; i++) {
				Room room = new Room();
				room.setNumber(i);
				room.setRoomType(standard);
				roomRepository.save(room);
			}

			for (int i = 301; i <= 310; i++) {
				Room room = new Room();
				room.setNumber(i);
				room.setRoomType(doubleStandard);
				roomRepository.save(room);
			}
		}

		if (userRepository.findAll().size() > 0) {
			log.info("Database already has Users, not adding defaults");
		} else {
			for (User u : usersToCreate) {
				userRepository.save(u);
			}
		}

		if (reservationRepository.findAll().size() > 0) {
			log.info("Database already has StayRecords, not adding defaults");
		} else {
			Calendar today = Calendar.getInstance();
			Calendar tomorrow = Calendar.getInstance();
			tomorrow.add(Calendar.DAY_OF_MONTH, 1);

			RoomType roomType = roomTypeRepository.findById(1);
			User user = userRepository.loadUserByUsername("user");
			Reservation reservation = new Reservation();
			reservation.setCheckIn(today.getTime());
			reservation.setCheckOut(tomorrow.getTime());
			reservation.setDescription(roomType.getName());
			reservation.setName("Reservation 1");
			reservation.setPrice(750.0);
			reservation.setRoomType(roomType);
			reservation.setUser(user);
			reservationRepository.save(reservation);



			reservation = new Reservation();
			reservation.setCheckIn(today.getTime());
			reservation.setCheckOut(tomorrow.getTime());
			reservation.setDescription(roomType.getName());
			reservation.setName("Reservation 2");
			reservation.setPrice(750.0);
			reservation.setRoomType(roomType);
			reservation.setUser(user);
			reservationRepository.save(reservation);

			reservation = new Reservation();
			roomType = roomTypeRepository.findById(3);
			reservation.setCheckIn(today.getTime());
			reservation.setCheckOut(tomorrow.getTime());
			reservation.setDescription(roomType.getName());
			reservation.setName("Reservation 3");
			reservation.setPrice(100.0);
			reservation.setRoomType(roomType);
			reservation.setUser(user);
			reservationRepository.save(reservation);

		}

		if (stayRecordRepository.findAll().size() > 0) {
			log.info("Database already has StayRecords, not adding defaults");
		} else {
			Calendar yesterday = Calendar.getInstance();
			yesterday.add(Calendar.DAY_OF_MONTH, -1);

			StayRecord stayRecord = new StayRecord();
			stayRecord.setUser(userRepository.loadUserByUsername("user"));
			stayRecord.setRoom(roomRepository.findById(1));
			stayRecord.setCheckInDate(yesterday.getTime());
			stayRecord.setReservation(reservationRepository.findById(1));
			stayRecordRepository.save(stayRecord);

		}
	}

	private User parseUser(Entry<Object, Object> u, String roles) {
		String username = (String) u.getKey();
		String[] values = ((String) u.getValue()).split(",");
		User user = new User(username, values[0], roles);
		user.setEmail(values[1]);
		user.setAddress(values[2]);


		log.info(String.format("username=%s, password=%s, email=%s, address=%s",
				username, values[0], values[1], values[2]));

		return user;
	}
}
