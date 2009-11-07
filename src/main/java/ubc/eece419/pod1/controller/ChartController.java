package ubc.eece419.pod1.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import ubc.eece419.pod1.dao.RoomTypeRepository;
import ubc.eece419.pod1.dao.StayRecordRepository;
import ubc.eece419.pod1.entity.RoomType;
import ubc.eece419.pod1.entity.StayRecord;
import ubc.eece419.pod1.security.SecurityUtils;


public class ChartController {

	@Autowired
	RoomTypeRepository roomTypeRepository;
	
	@Autowired
	StayRecordRepository StayRecordRepository;
	
	@RequestMapping("/**/view")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("chart/view");
		mav.addObject("currentuser", SecurityUtils.getCurrentUserOrNull());
		mav.addObject("search", new RoomTypeController.Search());
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	@ModelAttribute("getURL")
	public String getURL() {
		StringBuffer sb = new StringBuffer();
		sb.append("http://chart.apis.google.com/chart?" +
			"chs=500x300" +
			"&amp;chtt=Reservation+Per+RoomTypes|For+Different+Seasons" +
			"&amp;chg=9,12.5" +
			"&amp;cht=lc");
		
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		List<StayRecord> li = StayRecordRepository.findAll();
		for (StayRecord rec : li) {
			Integer temp = hm.get(rec.getRoom().getRoomType().getName());
			if(temp != null){
				temp++;
				hm.put(rec.getRoom().getRoomType().getName(), temp);
			}
			else{
				hm.put(rec.getRoom().getRoomType().getName(), 1);
			}
		}
		
	    Set set = hm.entrySet();
	    Iterator i = set.iterator();
	    // Set chart legend
	    sb.append("&amp;chdl=");
	    while(i.hasNext()){
	      Map.Entry me = (Map.Entry)i.next();
	      sb.append(me.getKey() + "|");
	    }
	    sb.deleteCharAt(sb.length()-1);
	        
	    // Set the reservation count per month
	    List<Object[]> li2 = StayRecordRepository.getReserveCountByMonth();
		HashMap<Integer, Integer> hm2 = new HashMap<Integer, Integer>();
	    for (Object[] row : li2) { 
	    	hm2.put(Integer.parseInt(row[0].toString()),Integer.parseInt(row[1].toString()));
	    }
	    sb.append("&amp;chd=t:");
	    for (int x=1; x <= 12; x++){
	    	if (hm2.get(x) == null){
	    		sb.append("0,");
	    	}
	    	else{
	    		sb.append(hm2.get(x)+",");
	    	}
	    }
	    sb.deleteCharAt(sb.length()-1);
	    
	    
	    // Set the rest
	    sb.append("&amp;chco=FF0000,00FF00,0000FF" +	
				"&amp;chxt=x,x,y" +
				"&amp;chxl=0:|Jan|Feb|March|Apr|May|June|July|Aug|Sept|Oct|Nov|Dec|" +
				"1:|2009|2010");
	    System.out.println(sb.toString());
		return sb.toString();
	}
}
