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
		
		// get the total count per roomtype
	    List<Object[]> li = StayRecordRepository.getReserveCountByMonth();
		HashMap<String, Integer> hm = new HashMap<String, Integer>();

		for (Object[] row : li) {
			System.out.println(row[0] + "," + row[1] + "," + row[2]);
			Integer temp = hm.get(row[0].toString());
			if(temp != null){
				temp = temp + Integer.parseInt(row[2].toString()); //keep adding the counts
				hm.put(row[0].toString(), temp);
			}
			else{
				hm.put(row[0].toString(), Integer.parseInt(row[2].toString()));
			}
		}

	    // Set chart legend
	    Set set = hm.entrySet();
	    Iterator i = set.iterator();
	    sb.append("&amp;chdl=");
	    while(i.hasNext()){
	      Map.Entry me = (Map.Entry)i.next();
	      sb.append(me.getKey() + "|");
	    }
	    sb.deleteCharAt(sb.length()-1);
	    
	    // Set chart data
	    sb.append("&amp;chd=t:");
	    int counter = 1;
	    String current = li.get(0)[0].toString();
		for (Object[] row : li) {
			if (current != row[0].toString()){ //start of new roomtype data
				sb.deleteCharAt(sb.length()-1);
				sb.append("|");
				current = row[0].toString();
				counter = 1;
			}
			
			while (Integer.parseInt(row[1].toString()) != counter && counter < 13){
				sb.append(0 + ",");
				System.out.println(counter +  ",0" );
				counter++;
				
			}
			sb.append(Integer.parseInt(row[2].toString()) * 2.5 + ","); //multiply data by 2 to account for scaling
			System.out.println(counter +  "," +row[2]);
			counter++;

		}
		sb.deleteCharAt(sb.length()-1);


	    // Set the rest
	    sb.append("&amp;chco=FF0000,00FF00,0000FF" +
				"&amp;chxt=x,x,y" +
				"&amp;chxl=0:|Jan|Feb|March|Apr|May|June|July|Aug|Sept|Oct|Nov|Dec|" +
				"1:|2009|2010|" +
				"2:|0|5|10|15|20|25|30|35|40");
	    System.out.println(sb.toString());
		return sb.toString();
	}
	
}
