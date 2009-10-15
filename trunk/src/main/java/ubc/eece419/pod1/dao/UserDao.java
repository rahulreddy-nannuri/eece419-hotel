package ubc.eece419.pod1.dao;

import javax.persistence.NoResultException;

import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import ubc.eece419.pod1.entity.User;

@Repository
public class UserDao extends GenericDao<User> implements UserRepository {

	// for Spring Security (UserDetailsService)
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			return (User) em.createQuery("select u from User u where u.username = ?").setParameter(1, username).getSingleResult();
		} catch (NoResultException e) {
			throw new UsernameNotFoundException("no such user: " + username);
		}
	}

}
