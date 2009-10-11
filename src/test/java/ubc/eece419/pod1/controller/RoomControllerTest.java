package ubc.eece419.pod1.controller;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import ubc.eece419.pod1.dao.RoomRepository;
import ubc.eece419.pod1.entity.Room;

/**
 *
 * @author yang
 */
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
        roomController.setRoomRepository(roomRepository);

        repository = roomRepository;
        controller = roomController;
    }

   


}
