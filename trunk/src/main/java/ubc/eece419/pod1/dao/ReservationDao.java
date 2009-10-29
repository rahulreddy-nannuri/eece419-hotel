/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ubc.eece419.pod1.dao;

import java.util.List;
import ubc.eece419.pod1.entity.Reservation;
import ubc.eece419.pod1.entity.User;

/**
 *
 * @author yang
 */
public class ReservationDao extends GenericDao<Reservation> implements ReservationRepository{

	@Override
	public List<Reservation> findReservationsByUser(User user) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
