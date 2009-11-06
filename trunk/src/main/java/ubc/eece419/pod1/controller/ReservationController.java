package ubc.eece419.pod1.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ubc.eece419.pod1.dao.ReservationRepository;
import ubc.eece419.pod1.dao.RoomTypeRepository;
import ubc.eece419.pod1.entity.Reservation;
import ubc.eece419.pod1.security.Roles;
import ubc.eece419.pod1.validator.ReflectionEntityValidator;
import ubc.eece419.pod1.security.SecurityUtils;
import ubc.eece419.pod1.entity.RoomType;

@Transactional
@Controller
public class ReservationController extends CRUDController<Reservation> {

	String[] reservationStateViews = new String[] { "login", "payment", "confirmation", "complete" };

	@Autowired
	ReservationRepository reservationRepository;

	@Autowired
	RoomTypeRepository roomTypeRepository;

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

	public static class ReservationForm {
		int state = 0;
		String cardNumber = "";
		int expiryMonth = 0;
		int expiryYear = 0;
		// room type id
		int type = 0;
		String securityCode = "";
		String cardType = "";

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

		public int getType() {
			return type;
		}

		public void setType(int id) {
			this.type = id;
		}

		public String getCardNumber() {
			return cardNumber;
		}

		public void setCardNumber(String creditCardNumber) {
			this.cardNumber = creditCardNumber;
		}

		public String getSecurityCode() {
			return securityCode;
		}

		public void setSecurityCode(String securityCode) {
			this.securityCode = securityCode;
		}

		public String getCardType() {
			return cardType;
		}

		public void setCardType(String cardType) {
			this.cardType = cardType;
		}

		public int getExpiryMonth() {
			return expiryMonth;
		}

		public void setExpiryMonth(int expiryMonth) {
			this.expiryMonth = expiryMonth;
		}

		public int getExpiryYear() {
			return expiryYear;
		}

		public void setExpiryYear(int expiryYear) {
			this.expiryYear = expiryYear;
		}
	}

	@RequestMapping("/reserve")
	public ModelAndView reserve(ReservationForm reservationForm, Errors errors) {
		Map<String, Object> model = new HashMap<String, Object>();
		RoomType roomType = null;
		int currentState = 0;
		String currentView = reservationStateViews[0];

		if (reservationForm != null) {
			currentState = reservationForm.state;

			model.put("cardNumber", reservationForm.cardNumber);
			model.put("maskedCardNumber", reservationForm.cardNumber);
			model.put("expiryMonth", reservationForm.expiryMonth);
			model.put("expiryYear", reservationForm.expiryYear);
			model.put("securityCode", reservationForm.securityCode);
			model.put("cardType", reservationForm.cardType);
			model.put("type", reservationForm.type);

			roomType = roomTypeRepository.findById(reservationForm.getType());
			if (roomType != null) {
				model.put("roomTypeName", roomType.getName());
				model.put("roomTypePrice", roomType.getDailyRate());
				model.put("roomTypeDescription", roomType.getDescription());
			}

			// check to see if we are on the finished state
			if (currentState == 3) {
				// create the new reservation and save it
				Reservation newReservation = getNewEntity();
				newReservation.setRoomType(roomType);
				newReservation.setPrice(roomType.getDailyRate());
				newReservation.setUser(SecurityUtils.getCurrentUser());
				reservationRepository.save(newReservation);
			}
		}

		// if we are at the login state, and logged in, go to the next state
		if (currentState == 0 && !SecurityUtils.currentUserIsAnonymous()) {
			currentState = 1;
		}

		model.put("state", currentState);
		model.put("type", reservationForm.type);
		currentView = reservationStateViews[currentState];

		return new ModelAndView(basePath + "/" + currentView, model);
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

}
