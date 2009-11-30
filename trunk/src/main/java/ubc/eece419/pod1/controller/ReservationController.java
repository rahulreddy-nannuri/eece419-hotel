package ubc.eece419.pod1.controller;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateMidnight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ubc.eece419.pod1.dao.BillRepository;
import ubc.eece419.pod1.dao.ItemTypeRepository;
import ubc.eece419.pod1.dao.ReservationRepository;
import ubc.eece419.pod1.dao.RoomRepository;
import ubc.eece419.pod1.dao.StayRecordRepository;
import ubc.eece419.pod1.entity.Bill;
import ubc.eece419.pod1.entity.ChargeableItem;
import ubc.eece419.pod1.entity.Reservation;
import ubc.eece419.pod1.entity.Room;
import ubc.eece419.pod1.entity.RoomType;
import ubc.eece419.pod1.entity.StayRecord;
import ubc.eece419.pod1.security.Roles;
import ubc.eece419.pod1.security.SecurityUtils;
import ubc.eece419.pod1.validator.ReflectionEntityValidator;

@Transactional
@Controller
public class ReservationController extends CRUDController<Reservation> {

	@Autowired
	ReservationRepository reservationRepository;
	@Autowired
	ItemTypeRepository itemTypeRepository;
	@Autowired
	BillRepository billRepository;
	@Autowired
	RoomRepository roomRepository;
	@Autowired
	StayRecordRepository stayRecordRepository;

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

	@InitBinder
	public void addCustomDateEditor(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), true));
	}

	@Override
	@Secured(Roles.STAFF)
	public ModelAndView list(String filter) {
		return super.list(filter);
	}

	@Override
	@Secured(Roles.STAFF)
	public ModelAndView edit(Long id) {
		return super.edit(id);
	}

	@RequestMapping("/**/cancel")
	public ModelAndView cancel(@RequestParam(value = "id") Long id) {
		log.info("cancel " + getEntityName());
		Reservation reservation = getRepository().findById(id);

		// user can only cancel own reservations
		if (!SecurityUtils.getCurrentUser().equals(reservation.getUser())) {
			SecurityUtils.assertStaff();
		}

		getRepository().delete(reservation);
		Bill bill = new Bill(reservation);
		return new ModelAndView("reservation/cancel", "bill", bill);
	}

	@Override
	@Secured(Roles.STAFF)
	public ModelAndView delete(Long id) {
		return super.delete(id);
	}

	@Override
	@Secured(Roles.STAFF)
	public ModelAndView save(Reservation bound, BindingResult errors, String view) {
		if (!bound.isNewEntity()) {
			Reservation old = reservationRepository.findById(bound.getId());
			bound.setRoomType(old.getRoomType());
			bound.setUser(old.getUser());
			bound.setPaymentInfo(old.getPaymentInfo());
			bound.setStayRecord(old.getStayRecord());
			bound.setBills(old.getBills());
			bound.setChargeableItems(old.getChargeableItems());
			bound.setCheckIn(old.getCheckIn());
			bound.setCheckOut(old.getCheckOut());
		}
		return super.save(bound, errors, view);
	}

	@Secured(Roles.USER)
	@RequestMapping("**/mine")
	public ModelAndView mine() {
		List<Reservation> reservations = reservationRepository.findReservationsByUser(SecurityUtils.getCurrentUser());
		return new ModelAndView("reservation/mine", "reservations", reservations);
	}

	@Secured(Roles.STAFF)
	@RequestMapping("**/charge")
	public ModelAndView charge(@RequestParam(value="reservationId") Long reservationId,
			@RequestParam(value="itemId", required=false) Long itemId,
			ChargeableItem chargeableItem, BindingResult errors,
			HttpServletRequest request) {
		log.info("adding " + chargeableItem + " to Reservation[" + reservationId + "]");

		boolean viewing = "GET".equals(request.getMethod());

		if (itemId != null) {
			chargeableItem = new ChargeableItem(itemTypeRepository.findById(itemId));
			viewing = false;
		} else if (!viewing) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "entityvalidator.nullable");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "entityvalidator.nullable");
		}

		if (viewing || errors.hasErrors() || chargeableItem == null) {
			ModelAndView mav = new ModelAndView("reservation/charge", "reservationId", reservationId);
			mav.addObject(chargeableItem);
			mav.addObject("itemTypes", itemTypeRepository.findAll());
			return mav;
		}

		Reservation reservation = reservationRepository.findById(reservationId);
		reservation.getChargeableItems().add(chargeableItem);
		reservationRepository.save(reservation);
		return new ModelAndView("redirect:/reservation/edit?id=" + reservationId);
	}

	@RequestMapping("**/checkout")
	public ModelAndView checkout(@RequestParam(value = "id") Long id) {
		Reservation reservation = reservationRepository.findById(id);
		reservation.getStayRecord().setCheckOutDate(new Date());

		if (new DateMidnight().isBefore(new DateMidnight(reservation.getCheckOut()))) {
			// early check-out
			// always use reservation start date, not stayRecord check-in, in case they showed up late
			double shortPrice = Reservation.calculatePrice(reservation.getRoomType(),
				reservation.getCheckIn(), new Date());

			if (shortPrice < reservation.getStayRecord().getPrice()) {
				reservation.getStayRecord().setPrice(shortPrice);
			}
		}

		Bill bill = new Bill(reservation);
		bill = billRepository.save(bill);

		return new ModelAndView("checkout/confirm", "bill", bill);
	}

	@RequestMapping("**/checkin")
	public ModelAndView checkin(@RequestParam(value = "id") Long id) {
		Reservation reservation = reservationRepository.findById(id);

		RoomType roomType = reservation.getRoomType();
		Room room = roomRepository.findAvailableRoomByRoomType(roomType);

		StayRecord sr = new StayRecord(reservation, room, new Date());
		stayRecordRepository.save(sr);

		return new ModelAndView("checkin/confirm");
	}

}
