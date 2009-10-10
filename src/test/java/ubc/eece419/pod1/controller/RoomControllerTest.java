package ubc.eece419.pod1.controller;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.*;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import ubc.eece419.pod1.dao.RoomRepository;
import ubc.eece419.pod1.entity.Room;

/**
 *
 * @author yang
 */
public class RoomControllerTest {

    private RoomController roomController;
    private RoomRepository roomRepository;

    @Before
    public void setUp() {
        roomRepository = EasyMock.createMock(RoomRepository.class);
        roomController = new RoomController();
        roomController.setRoomRepository(roomRepository);
    }

    @Test
    public void testList() {

        List<Room> rooms = new ArrayList<Room>();
        rooms.add(new Room());
        rooms.add(new Room());

        EasyMock.expect(roomRepository.findAll()).andReturn(rooms);
        EasyMock.replay(roomRepository);

        ModelAndView mav = roomController.list();
        EasyMock.verify(roomRepository);

        List<Room> model = (List<Room>) mav.getModel().get("rooms");
        assertEquals(rooms.size(), model.size());

    }

    @Test
    public void testEdit() {

        Room r1 = new Room();

        EasyMock.expect(roomRepository.findById(1)).andReturn(r1);
        EasyMock.replay(roomRepository);

        ModelAndView mav = roomController.edit(null);
        Room model = (Room) mav.getModel().get("room");
        assertNotNull(model);

        mav = roomController.edit(1L);
        EasyMock.verify(roomRepository);

        model = (Room) mav.getModel().get("room");
        assertEquals(r1, model);
    }

    @Test
    public void testSave() {
        Room r1 = new Room();

        EasyMock.expect(roomRepository.save(r1)).andReturn(r1);
        EasyMock.replay(roomRepository);

        ModelAndView mav = roomController.save(r1);
        EasyMock.verify(roomRepository);

        assertEquals("redirect:list", mav.getViewName());
    }

    @Test
    public void testDelete() {
        Room r1 = new Room();

        EasyMock.expect(roomRepository.findById(r1.getId())).andReturn(r1);
        roomRepository.delete(r1);
        EasyMock.replay(roomRepository);

        ModelAndView mav = roomController.delete(r1.getId());
        EasyMock.verify(roomRepository);
        assertEquals("redirect:list", mav.getViewName());

    }
}
