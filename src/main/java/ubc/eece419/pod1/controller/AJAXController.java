/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ubc.eece419.pod1.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ubc.eece419.pod1.dao.ImageRepository;
import ubc.eece419.pod1.entity.Image;
import ubc.eece419.pod1.entity.User;
import ubc.eece419.pod1.security.SecurityUtils;

/**
 *
 * @author yang
 */
public class AJAXController {

	@Autowired
	private ImageRepository imageRepository;

	@ModelAttribute("currentuser")
	public User exposeCurrentUser() {
		return SecurityUtils.getCurrentUserOrNull();
	}

	@RequestMapping
	ModelAndView listImage(){
		List<Image> images=imageRepository.findAll();
		return new ModelAndView("/ajax/listImage","images" , images);
	}

}
