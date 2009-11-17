package ubc.eece419.pod1.config;

import static java.util.Arrays.asList;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.joda.time.DateTime;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import ubc.eece419.pod1.dao.ImageRepository;
import ubc.eece419.pod1.dao.ReservationRepository;
import ubc.eece419.pod1.dao.RoomRepository;
import ubc.eece419.pod1.dao.RoomTypeRepository;
import ubc.eece419.pod1.dao.StayRecordRepository;
import ubc.eece419.pod1.dao.UserRepository;
import ubc.eece419.pod1.entity.Image;
import ubc.eece419.pod1.entity.Reservation;
import ubc.eece419.pod1.entity.Room;
import ubc.eece419.pod1.entity.RoomType;
import ubc.eece419.pod1.entity.StayRecord;
import ubc.eece419.pod1.entity.User;

public class DatabasePopulator implements InitializingBean {
	private static final Logger log = Logger.getLogger(DatabasePopulator.class.getName());

	public static final String IMAGE_ROOT = "src/main/resources/image";

	private List<User> usersToCreate = new ArrayList<User>();

	private Resource penthouseImage;
	private Resource doubleBedImage;
	private Resource singleBedImage;
	private Resource studentBedImage;
	private Resource welcomeImage;

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
	@Autowired
	ImageRepository imageRepository;

	public void setPenthouseImage(Resource penthouseImage) {
		this.penthouseImage = penthouseImage;
	}

	public void setDoubleBedImage(Resource doubleBedImage) {
		this.doubleBedImage = doubleBedImage;
	}

	public void setSingleBedImage(Resource singleBedImage) {
		this.singleBedImage = singleBedImage;
	}

	public void setStudentBedImage(Resource studentBedImage) {
		this.studentBedImage = studentBedImage;
	}

	public void setWelcomeImage(Resource welcomeImage) {
		this.welcomeImage = welcomeImage;
	}

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
			penthouse.setImageId(2L);
			penthouse.setAttributes(asList("Double", "Double", "Double", "Kitchen"));
			penthouse = roomTypeRepository.save(penthouse);

			RoomType bachelor = new RoomType();
			bachelor.setName("Bachelor Dive");
			bachelor.setDescription("Perfect for starving students.");
			bachelor.setDailyRate(20.0);
			bachelor.setMaxOccupancy(1);
			bachelor.setImageId(3L);
			bachelor.setAttributes(asList("Single", "Shared Bathroom"));
			bachelor = roomTypeRepository.save(bachelor);

			RoomType standard = new RoomType();
			standard.setName("Corporate Econo-box");
			standard.setDescription("Comfort befitting Middle America.");
			standard.setDailyRate(80.0);
			standard.setMaxOccupancy(2);
			standard.setImageId(4L);
			standard.setAttributes(asList("Double"));
			standard = roomTypeRepository.save(standard);

			RoomType doubleStandard = new RoomType();
			doubleStandard.setName("Double Econo-box");
			doubleStandard.setDescription("Twice the fun of a Corporate Econo-box.");
			doubleStandard.setDailyRate(120.0);
			doubleStandard.setMaxOccupancy(4);
			doubleStandard.setImageId(5L);
			doubleStandard.setAttributes(asList("Double", "Double"));
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
			log.info("Database already has Reservations, not adding defaults");
		} else {
			DateTime checkIn = new DateTime().minusDays(500);
			User user = userRepository.loadUserByUsername("sampledata");

			RoomType roomType1 = roomTypeRepository.findById(1);
			RoomType roomType2 = roomTypeRepository.findById(3);

			while(checkIn.isBefore(new DateTime().plusWeeks(1))) {
				DateTime checkOut = checkIn.plusDays((int) Math.ceil(Math.random() * 4));

				Reservation reservation;

				// penthouse in the summer, econo-box in the winter. just because
				if (Math.sin(Math.PI/365 * checkIn.getDayOfYear()) > Math.random()) {
					reservation = new Reservation(user, roomType1, checkIn.toDate(), checkOut.toDate());
				} else {
					reservation = new Reservation(user, roomType2, checkIn.toDate(), checkOut.toDate());
				}

				if (checkIn.isBeforeNow()) {
					StayRecord stayRecord = new StayRecord();

					stayRecord.setCheckInDate(checkIn.toDate());
					if (checkOut.isBeforeNow()) {
						stayRecord.setCheckOutDate(checkOut.toDate());
					}
					stayRecord.setUser(user);
					stayRecord.setRoom(reservation.getRoomType().getRooms().iterator().next());

					stayRecord.setReservation(reservationRepository.save(reservation));
					stayRecordRepository.save(stayRecord);
				} else {
					reservationRepository.save(reservation);
				}

				checkIn = checkOut.plusDays((int) Math.ceil(Math.random() * 4));
			}
		}

		if (imageRepository.findAll().size() > 0) {
			log.info("Database already has images, not adding defaults");
		} else {
			Image welcome = new Image();
			welcome.setName("Welcome");
			welcome.setData(getResourceAsByte(welcomeImage));
			imageRepository.save(welcome);

			Image penthouse = new Image();
			penthouse.setName("Penthouse");
			penthouse.setData(getResourceAsByte(penthouseImage));
			imageRepository.save(penthouse);

			Image studentBed = new Image();
			studentBed.setName("Student");
			studentBed.setData(getResourceAsByte(studentBedImage));
			imageRepository.save(studentBed);

			Image singleBed = new Image();
			singleBed.setName("Single");
			singleBed.setData(getResourceAsByte(singleBedImage));
			imageRepository.save(singleBed);

			Image doubleBed = new Image();
			doubleBed.setName("Double");
			doubleBed.setData(getResourceAsByte(doubleBedImage));
			imageRepository.save(doubleBed);
		}
	}

	private byte[] getResourceAsByte(Resource resource) throws IOException {
		InputStream in = resource.getInputStream();

		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		byte[] buf = new byte[1024];
		int i;
		while ((i = in.read(buf)) > 0) {
			out.write(buf, 0, i);
		}

		in.close();
		return out.toByteArray();
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
