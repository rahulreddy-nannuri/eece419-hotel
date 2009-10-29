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
import ubc.eece419.pod1.dao.ReservationRepository;
import ubc.eece419.pod1.dao.RoomRepository;
import ubc.eece419.pod1.dao.StayRecordRepository;
import ubc.eece419.pod1.dao.UserRepository;
import ubc.eece419.pod1.entity.Reservation;
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


		switch (page) {
			// user selection
			case 0:
			// reservation selection
			case 1:
				Reservation r1 = new Reservation();
				r1.setId(1);
				r1.setDescription("reservation 1");
				Reservation r2 = new Reservation();
				r2.setId(2);
				r2.setDescription("reservation 2");
				List<Reservation> reservations = new ArrayList<Reservation>();
				reservations.add(r1);
				reservations.add(r2);

				// TODO: uncomment when the real code is done
//				User user=userRepository.loadUserByUsername(checkin.getUsername());
//				List<Reservation> reservations=reservationRepository.findReservationsByUser(user);

				checkin.setReservations(reservations);
				break;

		}
	}

	@Override
	protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		// TODO: uncomment when the real code is done
//		Checkin checkin=(Checkin)command;
//		User user=userRepository.loadUserByUsername(checkin.getUsername());
//		Reservation reservation=reservationRepository.findById(checkin.getSelectedReservation());
//		RoomType roomType=reservation.getRoomType();
//		Date checkOut=reservation.getCheckOut();
//		Date today=Calendar.getInstance().getTime();
//		Room room=roomRepository.findAvailableRoom(roomType,today,checkOut);
//
//		StayRecord sr=new StayRecord();
//		sr.setCheckInDate(today);
//		sr.setUser(user);
//		sr.setRoom(room);
//		stayRecordRepository.save(sr);

		return new ModelAndView("checkin/confirm");
	}

	public void setStayRecordRepository(StayRecordRepository stayRecordRepository) {
		this.stayRecordRepository = stayRecordRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void setRoomRepository(RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}

	public void setReservationRepository(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}
}


