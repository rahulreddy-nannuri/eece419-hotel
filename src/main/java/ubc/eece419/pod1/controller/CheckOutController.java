package ubc.eece419.pod1.controller;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.servlet.ModelAndView;
import ubc.eece419.pod1.dao.StayRecordRepository;
import ubc.eece419.pod1.dao.UserRepository;
import ubc.eece419.pod1.entity.StayRecord;
import ubc.eece419.pod1.entity.User;
import ubc.eece419.pod1.formcommand.Checkout;

@Transactional
@Controller
public class CheckOutController extends BaseWizardFormController {

	@Autowired
	StayRecordRepository stayRecordRepository;
	@Autowired
	UserRepository userRepository;

	public CheckOutController() {
		setPages(new String[]{"checkout/user", "checkout/stay"});
		setCommandClass(Checkout.class);
		setCommandName("checkout");
	}

	@Override
	protected void postProcessPage(HttpServletRequest request, Object command, Errors errors, int page) throws Exception {
		Checkout checkout = (Checkout) command;
		if (!errors.hasErrors()) {
			switch (page) {
				case 0:
					User user = userRepository.loadUserByUsername(checkout.getUsername());
					List<StayRecord> stayRecords = stayRecordRepository.findUncheckedOutStayRecordsByUser(user);

					checkout.setStayRecords(stayRecords);
					break;
			}
		}
	}

	@Override
	protected void validatePage(Object command, Errors errors, int page) {
		Checkout checkout = (Checkout) command;
		switch (page) {
			case 0:
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.isnull");
				if (!errors.hasErrors()) {
					try {
						userRepository.loadUserByUsername(checkout.getUsername());
					} catch (UsernameNotFoundException e) {
						errors.rejectValue("username","user.notexist");
					}
				}
				break;
			case 1:
				StayRecord stayRecord = stayRecordRepository.findById(checkout.getSelectedStayRecord());
				if (stayRecord == null) {
					errors.reject("checkout.stayrecord.isnull");
				}
				break;
		}
	}

	@Override
	protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		Checkout checkout = (Checkout) command;

		StayRecord stayRecord = stayRecordRepository.findById(checkout.getSelectedStayRecord());
		stayRecord.setCheckOutDate(new Date());
		stayRecordRepository.save(stayRecord);

		return new ModelAndView("checkout/confirm");
	}
}


