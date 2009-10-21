package ubc.eece419.pod1.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ubc.eece419.pod1.dao.UserRepository;
import ubc.eece419.pod1.entity.User;
import ubc.eece419.pod1.security.Roles;
import ubc.eece419.pod1.security.SecurityUtils;
import ubc.eece419.pod1.validator.ReflectionEntityValidator;

@Transactional
@Controller
public class UserController extends CRUDController<User> {

	public UserController() {
		addValidator(new ReflectionEntityValidator<User>(this));
	}
	@Autowired
	UserRepository userRepository;

	@Override
	@Secured(Roles.ADMIN)
	public ModelAndView delete(Long id) {
		return super.delete(id);
	}

	@Override
	public ModelAndView save(User bound, BindingResult errors,
			@RequestParam(value = "view", required = false) String view) {

		if (hasError(bound, errors)) {
			return editView(bound);
		}

		// TODO Auto-generated method stub
		if (!(bound.isNewEntity() || bound.equals(SecurityUtils.getCurrentUser()))) {
			// only admins can edit /other/ users
			SecurityUtils.assertAdmin();
		}

		// TODO: is this a validation step? for now, it'll just take effect silently
		if (!SecurityUtils.isAdmin()) {
			bound.setRoles(Roles.USER);
		}

		// TODO: remember to update the SecurityContext if editing the logged-in user

		// right now, password is the only changeable field
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

		// login the user if neede
		if(SecurityUtils.currentUserIsAnonymous()){
			SecurityUtils.login(bound);
		}

		// provide custom view support
		if (view != null && view.length() > 0) {
			return new ModelAndView("redirect:" + view);
		}
		return redirectToListView();
	}

	@Override
	public ModelAndView edit(Long id) {
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
		u.setRoles(Roles.USER); // default to least privilege
		return u;
	}

	@Override
	public UserRepository getRepository() {
		return userRepository;
	}

	@RequestMapping("/**/login")
	public ModelAndView login(@RequestParam(value = "login_error", required = false) Integer error) {
		log.info("login " + getEntityName());
		ModelAndView mav = new ModelAndView("user/login");
		if (error != null) {
			mav.addObject("login_error", error);
		}
		return mav;
	}

	@RequestMapping("/**/register")
	public ModelAndView register() {
		log.info("register new user");
		ModelAndView mav = new ModelAndView("user/register");
		mav.addObject(getEntityName(), getNewEntity());
		mav.addObject("view", "/");
		return mav;

	}
}
