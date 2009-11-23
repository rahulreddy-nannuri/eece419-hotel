package ubc.eece419.pod1.dao;

import java.util.List;
import ubc.eece419.pod1.entity.Reservation;
import ubc.eece419.pod1.entity.User;

public interface ReservationRepository extends GenericRepository<Reservation>{

	List<Reservation> findAll(String filter);

	List<Reservation> findUncheckedInReservationsByUser(User user);

	List<Reservation> findReservationsByUser(User user);

}
