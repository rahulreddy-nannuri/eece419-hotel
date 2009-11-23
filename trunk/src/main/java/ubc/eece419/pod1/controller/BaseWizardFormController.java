package ubc.eece419.pod1.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;

import ubc.eece419.pod1.security.SecurityUtils;

/**
 * Add univerally-needed page data, as CRUDController does
 */
public abstract class BaseWizardFormController extends AbstractWizardFormController {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = super.handleRequest(request, response);
		mav.addObject("currentuser", SecurityUtils.getCurrentUserOrNull());
		return mav;
	}

}
