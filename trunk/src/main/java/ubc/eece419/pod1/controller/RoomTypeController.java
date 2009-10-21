package ubc.eece419.pod1.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import ubc.eece419.pod1.dao.RoomTypeRepository;
import ubc.eece419.pod1.entity.RoomType;


@Transactional
@Controller
public class RoomTypeController extends CRUDController<RoomType> {

	@Autowired
    RoomTypeRepository roomTypeRepository;
	
	@Override
	protected RoomType getNewEntity() {
        RoomType r = new RoomType();
        r.setDescription("Room type is created on " + new Date());
        //r.setDailyRate(dailyRate)
        //r.setMaxOccupancy(maxOccupancy)
        //r.setName(name)
        return r;
	}

	@Override
	public RoomTypeRepository getRepository() {
		return roomTypeRepository;
	}

}
