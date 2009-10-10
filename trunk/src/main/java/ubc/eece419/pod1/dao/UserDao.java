package ubc.eece419.pod1.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import ubc.eece419.pod1.entity.User;

@Repository
public class UserDao implements UserRepository {

	@PersistenceContext
	private EntityManager em;

	public User findById(long id) {
		// this will return null, rather than throw an EntityNotFoundEx, or somesuch
		return em.find(User.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		return em.createQuery("select u from User u").getResultList();
	}

	public User save(User entity) {
		return em.merge(entity);
	}

	public void delete(User entity) {
		em.remove(entity);
	}

	// for Spring Security (UserDetailsService)
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = (User) em.createQuery("select u from User u where u.username = ?").setParameter(1, username).getSingleResult();
		if (user == null) {
			throw new UsernameNotFoundException("no such user: " + username);
		}
		return user;
	}

}
