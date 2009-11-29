package ubc.eece419.pod1.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.servlet.ModelAndView;
import ubc.eece419.pod1.dao.ReservationRepository;
import ubc.eece419.pod1.dao.RoomTypeRepository;
import ubc.eece419.pod1.entity.Reservation;
import ubc.eece419.pod1.security.Roles;
import ubc.eece419.pod1.security.SecurityUtils;
import ubc.eece419.pod1.validator.ReservationFormValidator;
import ubc.eece419.pod1.entity.RoomType;
import ubc.eece419.pod1.formcommand.ReservationForm;
import ubc.eece419.pod1.entity.User;
import ubc.eece419.pod1.dao.UserRepository;

@Controller
public class ReservationWizardController extends BaseWizardFormController {
	private static final Logger log = Logger.getLogger(ReservationWizardController.class.getName());

	@Autowired
	RoomTypeRepository roomTypeRepository;
	@Autowired
	ReservationRepository reservationRepository;
	@Autowired
	UserRepository userRepository;

	public ReservationWizardController() {
		setPages(new String[] {"reservation/payment", "reservation/confirmation"});
		setCommandName("reservation");
		setCommandClass(ReservationForm.class);
		setValidator(new ReservationFormValidator());
	}

	@Override
	@Secured(Roles.USER)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return super.handleRequest(request, response);
	}

	// this shouldn't be necessary
	private void bindGetSearchParams(HttpServletRequest request, ReservationForm reservationForm) {
		String type = request.getParameter("type");
		if (type != null) {
			String checkIn = request.getParameter("checkIn");
			String checkOut = request.getParameter("checkOut");

			// update our model with the type, checkIn and checkOut date passed from the search page
			reservationForm.setType(Integer.parseInt(type));
			reservationForm.setCheckIn(checkIn);
			reservationForm.setCheckOut(checkOut);

			RoomType roomType = roomTypeRepository.findById(Integer.parseInt(type));

			// store the room type and price for the Confirmation page
			reservationForm.setRoomType(roomType);
			reservationForm.setPrice(Reservation.calculatePrice(roomType,
										reservationForm.getCheckInDate(),
										reservationForm.getCheckOutDate()));
		}
	}


	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request,
			Object command, Errors errors, int page) {

		ReservationForm reservationForm = (ReservationForm)command;

		// check to see if the room type was passed as a GET var
		// (this happens when linked from the search list)
		bindGetSearchParams(request, reservationForm);

		// generate our model for our views
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("reservation", reservationForm);
		model.put("page", page);

		// add errors if they exist
		if(errors.hasErrors()) {
			model.put("errors", errors);
		}
		return model;
	}

	@Override
	protected void validatePage(Object reservationData, Errors errors, int page) {
		log.info("validating page " + page);
		ValidationUtils.invokeValidator(getValidator(), reservationData, errors);
	}

	@Override
	protected String getViewName(HttpServletRequest request, Object command, int page) {
		ReservationForm reservationForm = (ReservationForm) command;
		bindGetSearchParams(request, reservationForm);
		if (reservationForm.getRoomType() == null ||
				reservationForm.getCheckInDate() == null ||
				reservationForm.getCheckOutDate() == null) {
			log.info("reserve without search params. redirecting to /");
			return "redirect:/";
		}
		return super.getViewName(request, command, page);
	}

	@Override
	protected ModelAndView processFinish(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors) {

		ReservationForm reservationForm = (ReservationForm) command;

		// we have to load the user from the repository for everything to save properly
		User currentUser = SecurityUtils.getCurrentUserOrNull();
		User user = userRepository.loadUserByUsername(currentUser.getUsername());

		RoomType roomType = reservationForm.getRoomType();

		Date checkIn = reservationForm.getCheckInDate();
		Date checkOut = reservationForm.getCheckOutDate();

		// populate our new reservation
		Reservation reservation = new Reservation(user, reservationForm.getPaymentInfo(), roomType, checkIn, checkOut);

		// create our model for the view
		Map<String, Object> model = new HashMap<String, Object>();

		// double check that there are available rooms
		int available = roomTypeRepository.numberAvailable(roomType, checkIn, checkOut);

		if(available <= 0) {
			errors.reject("roomtype.notavailable", "There are no available rooms for the given date range.");
			model.put("errors", errors);
		} else {
			// save the new reservation
			log.info(String.format("bill %s #%s $%f", reservationForm.getPaymentInfo().getCardType(), reservationForm.getPaymentInfo().getCardNumber(), reservation.getQuotedPrice()));
			reservationRepository.save(reservation);
		}

		model.put("reservation", reservationForm);
		model.put("page", 2);

		return new ModelAndView("reservation/complete", model);
	}

	@Override
	protected ModelAndView processCancel(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors) throws Exception {
		return new ModelAndView("redirect:/");
	}
}
