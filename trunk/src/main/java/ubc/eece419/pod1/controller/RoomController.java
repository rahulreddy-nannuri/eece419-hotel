package ubc.eece419.pod1.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import ubc.eece419.pod1.dao.RoomRepository;
import ubc.eece419.pod1.entity.Room;
import ubc.eece419.pod1.validator.ReflectionEntityValidator;

// apparently @Controller and InitalizingBean don't play nicely together
@Transactional
@Controller
public class RoomController extends CRUDController<Room> {

	// TODO: validation, handle binding errors
	public RoomController() {
		addValidator(new ReflectionEntityValidator<Room>(this));
	}

	@Autowired
    RoomRepository roomRepository;


    @Override
    protected Room getNewEntity() {
        Room r = new Room();
        r.setDescription("Room is created on " + new Date());
        return r;
    }

    @Override
    public RoomRepository getRepository() {
        return roomRepository;
    }
}
