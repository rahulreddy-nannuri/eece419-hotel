package ubc.eece419.pod1.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
	
	@RequestMapping("/reserve")
	public ModelAndView reserve() {
		log.info("reserve " + getEntityName());
		List<RoomType> models = getRepository().findAll();
		String modelName = getEntityName() + "s";
		String viewName = basePath + "/reserve";
		return new ModelAndView(viewName, modelName, models);
	}

}
