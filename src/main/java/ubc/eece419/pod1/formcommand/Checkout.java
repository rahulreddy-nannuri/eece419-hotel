/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ubc.eece419.pod1.formcommand;

import java.util.List;
import ubc.eece419.pod1.entity.StayRecord;

/**
 *
 * @author yang
 */
public class Checkout {
	String username;
	List<StayRecord> stayRecords;
	long selectedStayRecord;

	public long getSelectedStayRecord() {
		return selectedStayRecord;
	}

	public void setSelectedStayRecord(long selectedStayRecord) {
		this.selectedStayRecord = selectedStayRecord;
	}

	public List<StayRecord> getStayRecords() {
		return stayRecords;
	}

	public void setStayRecords(List<StayRecord> stayRecords) {
		this.stayRecords = stayRecords;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
