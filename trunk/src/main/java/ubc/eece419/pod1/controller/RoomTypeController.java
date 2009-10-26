package ubc.eece419.pod1.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
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
	}

	@RequestMapping("/**/search")
	public ModelAndView search(Search search, Errors errors) {
		List<RoomType> roomTypes = getRepository().findAll();

		// TODO: do this in SQL.
		List<RoomType> filtered = new ArrayList<RoomType>();
		for (RoomType type : roomTypes) {
			if (type.getDailyRate() < search.getMinPrice() || type.getDailyRate() > search.getMaxPrice())
				continue;
			if (type.getMaxOccupancy() < search.getOccupancy())
				continue;
			filtered.add(type);
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roomTypes", filtered);
		model.put("search", search);

		return new ModelAndView(basePath + "/search", model);
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
	public ModelAndView save(RoomType bound, BindingResult errors, String view) {
		return super.save(bound, errors, view);
	}

}
