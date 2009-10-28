package ubc.eece419.pod1.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import ubc.eece419.pod1.dao.RoomTypeRepository;
import ubc.eece419.pod1.security.SecurityUtils;

@Controller
public class WelcomeController extends AbstractController {

	@Autowired
	RoomTypeRepository roomTypeRepository;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("currentuser", SecurityUtils.getCurrentUserOrNull());
		mav.addObject("search", new RoomTypeController.Search());
		mav.addObject("allAttributes", roomTypeRepository.allAttributes());
		return mav;
	}

}
