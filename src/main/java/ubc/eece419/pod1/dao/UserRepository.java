package ubc.eece419.pod1.dao;

import org.springframework.security.userdetails.UserDetailsService;

import ubc.eece419.pod1.entity.User;

public interface UserRepository extends GenericRepository<User>, UserDetailsService {

	User loadUserByUsername(String username);
}
