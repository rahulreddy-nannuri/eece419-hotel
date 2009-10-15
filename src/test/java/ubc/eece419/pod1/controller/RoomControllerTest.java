package ubc.eece419.pod1.controller;

import org.easymock.EasyMock;
import org.junit.Before;
import ubc.eece419.pod1.dao.RoomRepository;
import ubc.eece419.pod1.entity.Room;

public class RoomControllerTest extends CRUDControllerTest<Room> {

	@Override
	Room getEntity() {
		return new Room();
	}

	@Override
	@Before
	public void setUp() {
		RoomRepository roomRepository = EasyMock.createMock(RoomRepository.class);
		RoomController roomController = new RoomController();
		roomController.roomRepository = roomRepository;

		repository = roomRepository;
		controller = roomController;
	}

}
