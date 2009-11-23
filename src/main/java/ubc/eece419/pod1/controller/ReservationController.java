package ubc.eece419.pod1.controller;

import java.text.SimpleDateFormat;

import java.util.List; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ubc.eece419.pod1.dao.ReservationRepository;
import ubc.eece419.pod1.dao.UserRepository;
import ubc.eece419.pod1.entity.Reservation;
import ubc.eece419.pod1.entity.User;
import ubc.eece419.pod1.security.Roles;
import ubc.eece419.pod1.validator.ReflectionEntityValidator;

@Transactional
@Controller
public class ReservationController extends CRUDController<Reservation> {

	@Autowired
	ReservationRepository reservationRepository;
	@Autowired
	UserRepository userRepository;

	@ModelAttribute("dateFormat")
	public SimpleDateFormat exposeDateFormat() {
		return new SimpleDateFormat("dd/MM/yyyy");
	}

	public ReservationController() {
		addValidator(new ReflectionEntityValidator<Reservation>(this));
	}

	@Override
	public ReservationRepository getRepository() {
		return reservationRepository;
	}

	@Override
	@Secured(Roles.ADMIN)
	public ModelAndView edit(Long id) {
		return super.edit(id);
	}

	@Override
	@Secured(Roles.ADMIN)
	public ModelAndView delete(Long id) {
		return super.delete(id);
	}

	@Override
	@Secured(Roles.ADMIN)
	public ModelAndView save(Reservation bound, BindingResult errors, String view) {
		return super.save(bound, errors, view);
	}

	@RequestMapping("**/view")
	public ModelAndView view(@RequestParam(value = "userId", required = false) Long userId) {
		List<Reservation> reservations = null;
		if (userId == null) {
			reservations = reservationRepository.findAll();
		} else {
			User user = userRepository.findById(userId);
			reservations = reservationRepository.findReservationsByUser(user);
		}
		return new ModelAndView("reservation/view", "reservations", reservations);
	}
}
