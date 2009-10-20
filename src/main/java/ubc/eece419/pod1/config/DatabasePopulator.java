package ubc.eece419.pod1.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Map.Entry;

import java.util.logging.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import ubc.eece419.pod1.dao.UserRepository;
import ubc.eece419.pod1.entity.User;

public class DatabasePopulator implements InitializingBean {

	private static Logger logger = Logger.getLogger(DatabasePopulator.class.getCanonicalName());
	private List<User> usersToCreate = new ArrayList<User>();
	@Autowired
	UserRepository userRepository;

	public void setUsers(Properties users) {
		for (Entry<Object, Object> u : users.entrySet()) {
			User user = parseUser(u, "ROLE_USER");
			usersToCreate.add(user);
		}
	}

	public void setAdmins(Properties admins) {
		for (Entry<Object, Object> u : admins.entrySet()) {
			User user = parseUser(u, "ROLE_USER,ROLE_STAFF,ROLE_ADMIN");
			usersToCreate.add(user);

		}
	}

	public void setStaff(Properties staff) {
		for (Entry<Object, Object> u : staff.entrySet()) {
			User user = parseUser(u, "ROLE_USER,ROLE_STAFF");
			usersToCreate.add(user);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		for (User u : usersToCreate) {
			userRepository.save(u);
		}
	}

	private User parseUser(Entry<Object, Object> u, String roles) {
		String username = (String) u.getKey();
		String[] values = ((String) u.getValue()).split(",");
		User user = new User(username, values[0], roles);
		user.setEmail(values[1]);
		user.setAddress(values[2]);


		logger.info(String.format("username=%s, password=%s, email=%s, address=%s",
				username, values[0], values[1], values[2]));

		return user;
	}
}
