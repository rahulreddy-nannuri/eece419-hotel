package ubc.eece419.pod1.controller;

import org.junit.Before;
import ubc.eece419.pod1.dao.RoomRepository;
import ubc.eece419.pod1.entity.Room;
import ubc.eece419.pod1.entity.RoomType;

public class RoomControllerTest extends CRUDControllerTest<Room> {

	@Override
	Room getEntity() {
		Room room = new Room();
		RoomType roomType = new RoomType();
		room.setRoomType(roomType);
		return room;
	}

	@Override
	@Before
	public void setUp() {
		RoomRepository roomRepository = context.mock(RoomRepository.class);
		RoomController roomController = new RoomController();
		roomController.roomRepository = roomRepository;

		repository = roomRepository;
		controller = roomController;
	}

}
