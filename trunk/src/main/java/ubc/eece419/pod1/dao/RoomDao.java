package ubc.eece419.pod1.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import ubc.eece419.pod1.entity.Room;

@Repository
public class RoomDao implements RoomRepository {

	@PersistenceContext
	private EntityManager em;

	public Room findById(long id) {
		// this will return null, rather than throw an EntityNotFoundEx, or somesuch
		return em.find(Room.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Room> findAll() {
		return em.createQuery("select r from Room r").getResultList();
	}

	public Room save(Room entity) {
		return em.merge(entity);
	}

	public void delete(Room entity) {
		em.remove(entity);
	}

}
