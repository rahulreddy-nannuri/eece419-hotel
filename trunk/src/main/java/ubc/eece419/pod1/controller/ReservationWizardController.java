package ubc.eece419.pod1.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
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
public class ReservationWizardController extends AbstractWizardFormController {
	
	@Autowired
	RoomTypeRepository roomTypeRepository;
	
	@Autowired
	ReservationRepository reservationRepository;
	
	@Autowired
	UserRepository userRepository;
	
	protected int getInitialPage(HttpServletRequest request) {
		// skip the first page if we are already logged in
		if(SecurityUtils.currentUserIsAnonymous()) {
			return 0;
		} else {
			return 1;
		}
	}
	
	protected Map<String, Object> referenceData(HttpServletRequest request, 
			Object reservationData, Errors errors, int page) {
		
		ReservationForm reservationForm = (ReservationForm)reservationData;
		
		String type = (String)request.getParameter("type");
		
		// check to see if the room type was passed as a GET var (this happens when linked
		// from the search list)
		if(type != null) {
			String checkIn = (String)request.getParameter("checkIn");
			String checkOut = (String)request.getParameter("checkOut");
			
			// update our model with the type, checkIn and checkOut date passed from the search page
			reservationForm.setType(new Integer(type));
			reservationForm.setCheckIn(checkIn);
			reservationForm.setCheckOut(checkOut);
			
			RoomType roomType = roomTypeRepository.findById(new Integer(type));
			
			// store the room type and price for the Confirmation page
			reservationForm.setRoomType(roomType);
			reservationForm.setPrice(calculatePrice(roomType, 
										reservationForm.getCheckInDate(),
										reservationForm.getCheckOutDate()));
		}
		
		// generate our model for our views
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("reservation", reservationData);
		model.put("page", page);
		// since this isn't a CRUDController, we have to pass the currentuser ourselves
		model.put("currentuser", SecurityUtils.getCurrentUserOrNull());
		
		return model;
	}
	
	protected void postProcessPage(HttpServletRequest request, 
			Object reservationData, Errors errors, int page) {
		
		// check to see if we have data from the login page
		if(page == 0) {
			ReservationForm reservationForm = (ReservationForm) reservationData;
			
			String username = reservationForm.getUsername();
			String password = reservationForm.getPassword();
			// load real user so we can get the proper role
			User realUser = userRepository.loadUserByUsername(username);
			// setup our dummy user for login
			User user = new User(username, password, realUser.getRoles());
			SecurityUtils.login(user);
		}
	}
	
	protected ModelAndView processFinish(HttpServletRequest request, 
			HttpServletResponse response, Object reservationData, BindException errors) {
		
		ReservationForm reservationForm = (ReservationForm) reservationData;
		
		// populate our new reservation
		Reservation reservation = new Reservation();
		
		// we have to load the user from the repository for everything to save properly
		User currentUser = SecurityUtils.getCurrentUserOrNull();
		User user = userRepository.loadUserByUsername(currentUser.getUsername());
		reservation.setUser(user);
		
		RoomType roomType = reservationForm.getRoomType();
		
		reservation.setRoomType(roomType);
		reservation.setName(roomType.getName());
		
		Date checkIn = reservationForm.getCheckInDate();
		Date checkOut = reservationForm.getCheckOutDate();
		
		reservation.setCheckIn(checkIn);
		reservation.setCheckOut(checkOut);
		
		reservation.setPrice(reservationForm.getPrice());
		
		// save the new reservation
		reservationRepository.save(reservation);
		
		// create our model for the view
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("reservation", reservationData);
		model.put("page", 3);
		model.put("currentuser", SecurityUtils.getCurrentUserOrNull());
		
		return new ModelAndView("reservation/complete", model);
	}
	
	private Double calculatePrice(RoomType roomType, Date checkIn, Date checkOut) {
		// calculate difference between checkIn and checkOut in days (does not account for daylight savings)
		Long days = (checkOut.getTime() - checkIn.getTime()) / (24 * 60 * 60 * 1000);
		
		return roomType.getDailyRate() * days;
	}
}
