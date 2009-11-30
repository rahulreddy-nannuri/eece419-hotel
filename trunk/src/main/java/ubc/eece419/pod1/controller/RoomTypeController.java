package ubc.eece419.pod1.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateMidnight;
import org.joda.time.Days;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.collections15.FactoryUtils;
import org.apache.commons.collections15.map.LazyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ubc.eece419.pod1.dao.RoomTypeRepository;
import ubc.eece419.pod1.entity.RoomType;
import ubc.eece419.pod1.security.Roles;
import ubc.eece419.pod1.validator.ReflectionEntityValidator;

@Transactional
@Controller
public class RoomTypeController extends CRUDController<RoomType> {

	@Autowired
    RoomTypeRepository roomTypeRepository;

	public RoomTypeController() {
		addValidator(new ReflectionEntityValidator<RoomType>(this));
	}

	@Override
	public RoomTypeRepository getRepository() {
		return roomTypeRepository;
	}

	public static class Search {
		int minPrice = 0;
		int maxPrice = 1000;
		Date checkIn;
		Date checkOut;
		int occupancy;
		String attributes;

		public int getMinPrice() {
			return minPrice;
		}
		public void setMinPrice(int minPrice) {
			this.minPrice = minPrice;
		}
		public int getMaxPrice() {
			return maxPrice;
		}
		public void setMaxPrice(int maxPrice) {
			this.maxPrice = maxPrice;
		}
		public Date getCheckIn() {
			return checkIn;
		}
		public void setCheckIn(Date checkIn) {
			this.checkIn = checkIn;
		}
		public Date getCheckOut() {
			return checkOut;
		}
		public void setCheckOut(Date checkOut) {
			this.checkOut = checkOut;
		}
		public int getOccupancy() {
			return occupancy;
		}
		public void setOccupancy(int occupancy) {
			this.occupancy = occupancy;
		}
		public String getAttributes() {
			return attributes;
		}
		public void setAttributes(String attributes) {
			this.attributes = attributes;
		}

		Map<String, Integer> getAttributeMap() {
			Map<String, Integer> map = LazyMap.decorate(new HashMap<String, Integer>(), FactoryUtils.constantFactory(0));
			for (String att : RoomType.splitIntoAttributes(attributes)) {
				map.put(att, map.get(att) + 1);
			}
			return map;
		}
	}

	@InitBinder
	public void addCustomDateEditor(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), true));
	}

	@RequestMapping("/**/search")
	public ModelAndView search(Search search, Errors errors) {

		List<RoomType> filtered = new ArrayList<RoomType>();
		Map<RoomType, Integer> availability = new HashMap<RoomType, Integer>();

		DateMidnight checkIn = new DateMidnight(search.getCheckIn());
		DateMidnight checkOut = new DateMidnight(search.getCheckOut());

		if(Days.daysBetween(checkIn, checkOut).getDays() <= 0) {
			errors.rejectValue("checkOut", "search.invaliddaterange");
		} else {
			if(new DateMidnight().isAfter(checkIn)) {
				errors.rejectValue("checkIn", "search.pastdate");
			}

			if(checkOut.isBeforeNow()) {
				errors.rejectValue("checkOut", "search.pastdate");
			}
		}

		if(!errors.hasErrors()) {
			filtered = roomTypeRepository.findByPriceAndOccupancyAndAttributes(search.getMinPrice(), search.getMaxPrice(), search.getOccupancy(), search.getAttributeMap());

			for (RoomType type : filtered) {
				int numberAvailable = roomTypeRepository.numberAvailable(type, search.checkIn, search.checkOut);
				availability.put(type, numberAvailable);
			}

			// this needs to be in a separate loop to avoid threading errors
			// remove unavailable rooms
			for (Map.Entry<RoomType, Integer> type : availability.entrySet()) {
				int numberAvailable = type.getValue();
				if(numberAvailable <= 0) {
					filtered.remove(type.getKey());
				}
			}
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roomTypes", filtered);
		model.put("search", search);
		model.put("available", availability);

		return new ModelAndView(basePath + "/search", model);
	}

	@ModelAttribute("allAttributes")
	public List<String> exposeAllRoomTypeAttributes() {
		return roomTypeRepository.allAttributes();
	}

	@ModelAttribute("maximumDailyRate")
	public double exposeMaximumDailyRate() {
		return roomTypeRepository.maximumDailyRate();
	}

	@Override
	@Secured(Roles.ADMIN)
	public ModelAndView edit(Long id) {
		return super.edit(id);
	}

	@Override
	@Secured(Roles.ADMIN)
	public ModelAndView delete(Long id) {
		RoomType roomType = roomTypeRepository.findById(id);
		if (roomType != null && roomType.getRooms().size() > 0) {
			return new ModelAndView("roomtype/error", "errorMessage", "Cannot delete a room type while rooms exist using that room type.");
		}
		return super.delete(id);
	}

	@Override
	@Secured(Roles.ADMIN)
	public ModelAndView save(RoomType bound, BindingResult errors, String view) {
		return super.save(bound, errors, view);
	}

}
