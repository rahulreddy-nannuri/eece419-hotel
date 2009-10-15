package ubc.eece419.pod1.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.annotation.Secured;
import org.springframework.web.servlet.ModelAndView;
import ubc.eece419.pod1.dao.GenericRepository;
import ubc.eece419.pod1.dao.RoomRepository;
import ubc.eece419.pod1.entity.Room;

public class RoomController extends CRUDController<Room> {

    // TODO: validation, handle binding errors

	@Autowired
    RoomRepository roomRepository;

	public RoomController() {
		super(Room.class);
	}

    @Override
    @Secured("ROLE_ADMIN")
    public ModelAndView delete(Long id) {
        return super.delete(id);
    }

    @Override
    protected Room getNewEntity() {
        Room r = new Room();
        r.setDescription("Room is created on " + new Date());
        return r;
    }

    @Override
    protected GenericRepository<Room> getRepository() {
        return roomRepository;
    }
}
