package ubc.eece419.pod1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ubc.eece419.pod1.dao.RoomTypeRepository;
import ubc.eece419.pod1.security.SecurityUtils;


public class ChartController {

	@Autowired
	RoomTypeRepository roomTypeRepository;
	
	@RequestMapping("/**/view")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("chart/view");
		mav.addObject("currentuser", SecurityUtils.getCurrentUserOrNull());
		mav.addObject("search", new RoomTypeController.Search());
		return mav;
	}
}
