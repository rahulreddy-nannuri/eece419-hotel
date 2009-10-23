package ubc.eece419.pod1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import ubc.eece419.pod1.dao.RoomTypeRepository;
import ubc.eece419.pod1.entity.RoomType;
import ubc.eece419.pod1.validator.ReflectionEntityValidator;


@Transactional
@Controller
public class RoomTypeController extends CRUDController<RoomType> {

	@Autowired
    RoomTypeRepository roomTypeRepository;

	public RoomTypeController() {
		addValidator(new ReflectionEntityValidator<RoomType>(this));
	}

	@Override
	public RoomTypeRepository getRepository() {
		return roomTypeRepository;
	}

}
