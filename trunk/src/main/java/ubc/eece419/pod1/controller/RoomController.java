package ubc.eece419.pod1.controller;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.annotation.Secured;
import org.springframework.web.servlet.ModelAndView;

import ubc.eece419.pod1.dao.RoomRepository;
import ubc.eece419.pod1.entity.Room;

public class RoomController extends CRUDController<Room> {
	private final Logger log = Logger.getLogger(RoomController.class.getName());

	// TODO: validation, handle binding errors

	private RoomRepository roomRepository;

	@Autowired
	public void setRoomRepository(RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}

	@Override
	public ModelAndView list() {
		log.info("/room/list");

		List<Room> rooms = roomRepository.findAll();
		log.info("room list len=" + rooms.size());

		return new ModelAndView("room/list", "rooms", rooms);
	}

	@Override
	public ModelAndView edit(Long id) {
		log.info("/room/edit?id=" + id);

		Room room = new Room();
		if (id == null) {
			// creating new room
			room = new Room();
			room.setDescription("this room was created at " + new Date());
		} else {
			room = roomRepository.findById(id);
		}
		return new ModelAndView("room/edit", "room", room);
	}

	@Override
	public ModelAndView save(Room room) {
		log.info("/room/save");

		room = roomRepository.save(room);
		return new ModelAndView("redirect:list");
	}

	@Override
	@Secured("ROLE_ADMIN")
	public ModelAndView delete(Long id) {
		log.info("/room/delete?id=" + id);

		roomRepository.delete(roomRepository.findById(id));
		return new ModelAndView("redirect:list");
	}
}
