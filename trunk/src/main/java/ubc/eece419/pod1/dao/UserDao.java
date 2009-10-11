package ubc.eece419.pod1.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import ubc.eece419.pod1.entity.User;

@Repository
public class UserDao extends GenericDao<User> implements UserRepository {

    // for Spring Security (UserDetailsService)
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = (User) em.createQuery("select u from User u where u.username = ?").setParameter(1, username).getSingleResult();
        if (user == null) {
            throw new UsernameNotFoundException("no such user: " + username);
        }
        return user;
    }

    @Override
    Class<User> getClazz() {
        return User.class;
    }
    
}
