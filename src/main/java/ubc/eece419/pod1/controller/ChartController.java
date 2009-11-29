package ubc.eece419.pod1.controller;

import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ubc.eece419.pod1.dao.StayRecordRepository;
import ubc.eece419.pod1.entity.User;
import ubc.eece419.pod1.security.SecurityUtils;

@Controller
public class ChartController {

	@Autowired
	StayRecordRepository StayRecordRepository;
	String colors[] ={"FF00FF","00FF00","C0C0C0","00FFFF","FFFF00","0000FF","FF0000","000000"};

	@RequestMapping({ "/**/", "/**/index" })
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("chart/index");
		return mav;
	}

	@RequestMapping("/**/view")
	public ModelAndView index(@RequestParam(value = "year", required = false) Integer year) {
		if (year == null) {
			year = new DateTime().getYear();
		}
		ModelAndView mav = new ModelAndView("chart/view");
		mav.addObject("getURL", getURL(year));
		mav.addObject("getURL2", getURL2(year));
		return mav;
	}

	@ModelAttribute("currentuser")
	public User exposeCurrentUser() {
		return SecurityUtils.getCurrentUserOrNull();
	}

	private String getURL(int year) {
		StringBuilder sb = new StringBuilder();

		sb.append("http://chart.apis.google.com/chart?"
				+ "chs=500x300"
				+ "&amp;chtt=Reservation+Count+by+Room+Type+in+" + year
				+ "&amp;chg=9,12.5"
				+ "&amp;cht=lc");

		// get the total count per roomtype and set label and set color

		StringBuilder color = new StringBuilder();
		sb.append("&amp;chdl=");
		List<Object[]> li = StayRecordRepository.getReserveCountByMonthByType(year);
		if (li.size() == 0) {
			return "";
		}

		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		int colIdx = 0;


		for (Object[] row : li) {
			Integer temp = hm.get(row[0].toString());
			if (temp != null) {
				temp = temp + Integer.parseInt(row[2].toString()); // keep adding the counts
				hm.put(row[0].toString(), temp);
			} else {
				hm.put(row[0].toString(), Integer.parseInt(row[2].toString()));
				sb.append(row[0].toString() + "|");
				color.append(colors[colIdx] + ",");
				colIdx++;
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		color.deleteCharAt(color.length() - 1);

		// Set chart data
		sb.append("&amp;chd=t:");
		int counter = 1;
		String current = li.get(0)[0].toString();

		for (Object[] row : li) {
			if (current != row[0].toString()) { // start of new roomtype data
				while (counter < 13) {
					sb.append(0 + ",");
					counter++;

				}
				sb.deleteCharAt(sb.length() - 1);
				sb.append("|");
				current = row[0].toString();
				counter = 1;
			}

			while (Integer.parseInt(row[1].toString()) != counter && counter < 13) {
				sb.append(0 + ",");
				counter++;
			}
			if (counter != 13) {
				sb.append(Integer.parseInt(row[2].toString()) * 2.5 + ","); // multiply data by 2 to account for scaling
				counter++;
			}
		}
		while (counter < 13) {
			sb.append(0 + ",");
			counter++;
		}
		sb.deleteCharAt(sb.length() - 1);

		// Set the rest
		sb.append("&amp;chco=" + color.toString() + "&amp;chxt=x,y"
				+ "&amp;chxl=0:|Jan|Feb|March|Apr|May|June|July|Aug|Sept|Oct|Nov|Dec|" + "1:|0|5|10|15|20|25|30|35|40");

		return sb.toString();
	}

	private String getURL2(int year) {
		StringBuilder sb = new StringBuilder();

		sb.append("http://chart.apis.google.com/chart?"
				+ "chs=500x300"
				+ "&amp;chtt=Total+Reservations+by+Month+" + year
				+ "&amp;chg=100,12.5"
				+ "&amp;cht=bvg");

		List<Object[]> li = StayRecordRepository.getReserveCountByMonth(year);
		if (li.size() == 0) {
			return "";
		}

		// Set chart data
		sb.append("&amp;chd=t:");
		int counter = 1;
		int current = 0;
		for (Object[] row : li) {
			current = Integer.parseInt(row[0].toString());
			while (counter != current && counter < 13) {
				sb.append("0,");
				counter++;
			}
			if (counter < 13) {
				sb.append(Integer.parseInt(row[1].toString()) * 2.5 + ",");
				counter++;
			}
		}
		while (counter < 13) {
			sb.append("0,");
			counter++;
		}
		sb.deleteCharAt(sb.length() - 1);

		// Set the rest
		sb.append("&amp;chxt=x,y"
				+ "&amp;chxl=0:|Jan|Feb|March|Apr|May|June|July|Aug|Sept|Oct|Nov|Dec|"
				+ "1:|0|5|10|15|20|25|30|35|40");
		return sb.toString();
	}
}
