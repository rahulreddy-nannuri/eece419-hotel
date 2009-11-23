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
import org.springframework.web.servlet.mvc.AbstractWizardFormController;
import ubc.eece419.pod1.dao.ReservationRepository;
import ubc.eece419.pod1.dao.RoomRepository;
import ubc.eece419.pod1.dao.StayRecordRepository;
import ubc.eece419.pod1.dao.UserRepository;
import ubc.eece419.pod1.entity.Reservation;
import ubc.eece419.pod1.entity.Room;
import ubc.eece419.pod1.entity.RoomType;
import ubc.eece419.pod1.entity.StayRecord;
import ubc.eece419.pod1.entity.User;
import ubc.eece419.pod1.formcommand.Checkin;
import ubc.eece419.pod1.security.SecurityUtils;

@Transactional
@Controller
public class CheckInController extends AbstractWizardFormController {

	@Autowired
	StayRecordRepository stayRecordRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoomRepository roomRepository;
	@Autowired
	ReservationRepository reservationRepository;

	public CheckInController() {
		setPages(new String[]{"checkin/user", "checkin/reservation"});
		setCommandClass(Checkin.class);
		setCommandName("checkin");
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = super.handleRequest(request, response);
		mav.addObject("currentuser", SecurityUtils.getCurrentUserOrNull());
		return mav;
	}

	@Override
	protected void postProcessPage(HttpServletRequest request, Object command, Errors errors, int page) throws Exception {
		Checkin checkin = (Checkin) command;
		if (!errors.hasErrors()) {
			switch (page) {
				case 0:
					User user = userRepository.loadUserByUsername(checkin.getUsername());
					List<Reservation> reservations = reservationRepository.findUncheckedInReservationsByUser(user);
					checkin.setReservations(reservations);
					break;
			}
		}
	}

	@Override
	protected void validatePage(Object command, Errors errors, int page) {
		Checkin checkin = (Checkin) command;
		switch (page) {
			case 0:
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.isnull");
				if (!errors.hasErrors()) {
					try {
						userRepository.loadUserByUsername(checkin.getUsername());
					} catch (UsernameNotFoundException e) {
						errors.rejectValue("username","user.notexist");
					}
				}
				break;
			case 1:
				Reservation reservation = reservationRepository.findById(checkin.getSelectedReservation());
				if (reservation == null) {
					errors.reject("checkin.reservation.isnull");
				} else {
					RoomType roomType = reservation.getRoomType();
					Room room = roomRepository.findAvailableRoomByRoomType(roomType);
					if (room == null) {
						errors.reject("checkin.room.unavailable");
					}
				}
				break;
		}
	}

	@Override
	protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {

		Checkin checkin = (Checkin) command;

		Reservation reservation = reservationRepository.findById(checkin.getSelectedReservation());
		RoomType roomType = reservation.getRoomType();
		Room room = roomRepository.findAvailableRoomByRoomType(roomType);

		StayRecord sr = new StayRecord(reservation, room, new Date());
		stayRecordRepository.save(sr);

		return new ModelAndView("checkin/confirm");
	}
}


