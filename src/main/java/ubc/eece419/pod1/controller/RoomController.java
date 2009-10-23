package ubc.eece419.pod1.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import ubc.eece419.pod1.dao.RoomRepository;
import ubc.eece419.pod1.dao.RoomTypeRepository;
import ubc.eece419.pod1.entity.Room;
import ubc.eece419.pod1.entity.RoomType;
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

	@Autowired
	RoomTypeRepository roomTypeRepository;

	@InitBinder
	public void registerRoomTypeEditor(WebDataBinder binder) {
		binder.registerCustomEditor(RoomType.class, new EntityEditor<RoomType>(roomTypeRepository));
	}

	@ModelAttribute("roomTypes")
	public List<RoomType> exposeRoomTypes() {
		return roomTypeRepository.findAll();
	}

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
