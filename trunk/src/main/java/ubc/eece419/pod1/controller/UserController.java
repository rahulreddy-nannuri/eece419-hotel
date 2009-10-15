package ubc.eece419.pod1.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.annotation.Secured;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import ubc.eece419.pod1.dao.GenericRepository;
import ubc.eece419.pod1.dao.UserRepository;
import ubc.eece419.pod1.entity.User;
import ubc.eece419.pod1.security.Roles;
import ubc.eece419.pod1.security.SecurityUtils;

public class UserController extends CRUDController<User> {
	private static final Logger log = Logger.getLogger(UserController.class.getName());

	@Autowired
	UserRepository userRepository;

	public UserController() {
		super(User.class);
	}

	@Override
	@Secured("ROLE_ADMIN")
	public ModelAndView delete(Long id) {
		return super.delete(id);
	}

	@Override
	public ModelAndView save(User bound) {
		if (!(bound.isNewEntity() || bound.equals(SecurityUtils.getCurrentUser()))) {
			// only admins can edit /other/ users
			SecurityUtils.assertAdmin();
		}

		// TODO: is this a validation step? for now, it'll just take effect silently
		if (!SecurityUtils.isAdmin()) {
			bound.setRoles(Roles.USER);
		}

		// TODO: remember to update the SecurityContext if editing the logged-in user

		// right now, password is the only changable field
		if (!bound.isNewEntity()) {
			User old = userRepository.findById(bound.getId());
			if (StringUtils.hasText(bound.getPassword())) {
				if (!bound.getPassword().equals(old.getPassword())) {
					old.setPassword(User.encryptPassword(bound.getPassword(), bound.getUsername()));
					userRepository.save(old);
				}
			}
		} else {
			bound.setPassword(User.encryptPassword(bound.getPassword(), bound.getUsername()));
			userRepository.save(bound);
		}
		return new ModelAndView("redirect:list");
	}


	@Override
	public ModelAndView edit(Long id) {
		log.info("edit " + getEntityName());
		User entity;
		if (id == null) {
			entity = getNewEntity();
		} else {
			entity = getRepository().findById(id);
			if (!entity.equals(SecurityUtils.getCurrentUser())) {
				// only admins can edit /other/ users
				SecurityUtils.assertAdmin();
			}
		}
		entity.setPassword("");
		return editView(entity);
	}


	@Override
	protected User getNewEntity() {
		User u = new User();
		u.setRoles("ROLE_USER"); // default to least privilege
		return u;
	}

	@Override
	protected GenericRepository<User> getRepository() {
		return userRepository;
	}

}
