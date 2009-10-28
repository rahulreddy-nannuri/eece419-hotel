package ubc.eece419.pod1.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;
import ubc.eece419.pod1.dao.StayRecordRepository;
import ubc.eece419.pod1.entity.StayRecord;
import ubc.eece419.pod1.formcommand.Checkout;
import ubc.eece419.pod1.security.SecurityUtils;

@Transactional
@Controller
public class CheckOutController extends AbstractWizardFormController {

	@Autowired
	StayRecordRepository stayRecordRepository;

	public CheckOutController() {
		setPages(new String[]{"checkout/user", "checkout/stay"});
		setCommandClass(Checkout.class);
		setCommandName("checkout");
	}


	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav=super.handleRequest(request, response);
		mav.addObject("currentuser", SecurityUtils.getCurrentUserOrNull());
		return mav;
	}


	@Override
	protected void postProcessPage(HttpServletRequest request, Object command, Errors errors, int page) throws Exception {
		Checkout checkout = (Checkout) command;

		switch (page) {
			// user selection
			case 0:

			// stay selection
			case 1:
				StayRecord s1 = new StayRecord();
				s1.setId(1);
				List<StayRecord> stayRecords = new ArrayList<StayRecord>();
				stayRecords.add(s1);
				checkout.setStayRecords(stayRecords);
				break;
		}
	}

	@Override
	protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		return new ModelAndView("checkout/confirm");
	}
}


