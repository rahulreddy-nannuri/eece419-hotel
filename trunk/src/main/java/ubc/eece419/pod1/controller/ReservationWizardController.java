package ubc.eece419.pod1.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;

import ubc.eece419.pod1.dao.ReservationRepository;
import ubc.eece419.pod1.dao.RoomTypeRepository;
import ubc.eece419.pod1.entity.Reservation;
import ubc.eece419.pod1.security.SecurityUtils;
import ubc.eece419.pod1.entity.RoomType;
import ubc.eece419.pod1.formcommand.ReservationForm;
import ubc.eece419.pod1.entity.User;
import ubc.eece419.pod1.dao.UserRepository;

@Controller
@Transactional
public class ReservationWizardController extends AbstractWizardFormController {
	private static final Logger log = Logger.getLogger(ReservationWizardController.class.getName());

	@Autowired
	RoomTypeRepository roomTypeRepository;
	@Autowired
	ReservationRepository reservationRepository;
	@Autowired
	UserRepository userRepository;

	@Override
	protected int getInitialPage(HttpServletRequest request) {
		// skip the first page if we are already logged in
		if(SecurityUtils.currentUserIsAnonymous()) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request,
			Object reservationData, Errors errors, int page) {

		ReservationForm reservationForm = (ReservationForm)reservationData;

		String type = request.getParameter("type");

		// check to see if the room type was passed as a GET var (this happens when linked
		// from the search list)
		if (type != null) {
			String checkIn = request.getParameter("checkIn");
			String checkOut = request.getParameter("checkOut");

			// update our model with the type, checkIn and checkOut date passed from the search page
			reservationForm.setType(new Integer(type));
			reservationForm.setCheckIn(checkIn);
			reservationForm.setCheckOut(checkOut);

			RoomType roomType = roomTypeRepository.findById(new Integer(type));

			// store the room type and price for the Confirmation page
			reservationForm.setRoomType(roomType);
			reservationForm.setPrice(Reservation.calculatePrice(roomType,
										reservationForm.getCheckInDate(),
										reservationForm.getCheckOutDate()));
		}

		// generate our model for our views
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("reservation", reservationForm);
		model.put("page", page);

		// since this isn't a CRUDController, we have to pass the currentuser ourselves
		model.put("currentuser", SecurityUtils.getCurrentUserOrNull());

		// add errors if they exist
		if(errors.hasErrors()) {
			model.put("errors", errors);
		}
		return model;
	}

	@Override
	protected void postProcessPage(HttpServletRequest request,
			Object reservationData, Errors errors, int page) {

		if (!errors.hasErrors()) {
			// check to see if we have data from the login page
			if (page == 0) {
				ReservationForm reservationForm = (ReservationForm) reservationData;

				String username = reservationForm.getUsername();
				String password = reservationForm.getPassword();
				// load real user so we can get the proper role
				User realUser = userRepository.loadUserByUsername(username);
				SecurityUtils.login(realUser);
			}
		}
	}

	@Override
	protected void validatePage(Object reservationData, Errors errors, int page) {
		switch(page) {
		case 0: // login
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.isnull");
			break;
		}
	}

	@Override
	protected ModelAndView processFinish(HttpServletRequest request,
			HttpServletResponse response, Object reservationData, BindException errors) {

		ReservationForm reservationForm = (ReservationForm) reservationData;

		// we have to load the user from the repository for everything to save properly
		User currentUser = SecurityUtils.getCurrentUserOrNull();
		User user = userRepository.loadUserByUsername(currentUser.getUsername());

		RoomType roomType = reservationForm.getRoomType();

		Date checkIn = reservationForm.getCheckInDate();
		Date checkOut = reservationForm.getCheckOutDate();

		// populate our new reservation
		Reservation reservation = new Reservation(user, roomType, checkIn, checkOut);

		// create our model for the view
		Map<String, Object> model = new HashMap<String, Object>();

		// double check that there are available rooms
		int available = roomTypeRepository.numberAvailable(roomType, checkIn, checkOut);

		if(available <= 0) {
			errors.reject("roomtype.notavailable", "There are no available rooms for the given date range.");
			model.put("errors", errors);
		} else {
			// save the new reservation
			reservationRepository.save(reservation);
		}

		model.put("reservation", reservationForm);
		model.put("page", 3);
		model.put("currentuser", SecurityUtils.getCurrentUserOrNull());

		return new ModelAndView("reservation/complete", model);
	}
}
