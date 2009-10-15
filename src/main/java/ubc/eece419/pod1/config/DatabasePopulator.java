package ubc.eece419.pod1.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Map.Entry;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import ubc.eece419.pod1.dao.UserRepository;
import ubc.eece419.pod1.entity.User;

public class DatabasePopulator implements InitializingBean {

	private List<User> usersToCreate = new ArrayList<User>();

	@Autowired
	UserRepository userRepository;

	public void setUsers(Properties users) {
		for (Entry<Object, Object> u : users.entrySet()) {
			usersToCreate.add(new User((String) u.getKey(), (String) u.getValue(), "ROLE_USER"));
		}
	}

	public void setAdmins(Properties admins) {
		for (Entry<Object, Object> u : admins.entrySet()) {
			usersToCreate.add(new User((String) u.getKey(), (String) u.getValue(), "ROLE_USER,ROLE_STAFF,ROLE_ADMIN"));
		}
	}

	public void setStaff(Properties staff) {
		for (Entry<Object, Object> u : staff.entrySet()) {
			usersToCreate.add(new User((String) u.getKey(), (String) u.getValue(), "ROLE_USER,ROLE_STAFF"));
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		for (User u : usersToCreate) {
			userRepository.save(u);
		}
	}

}
