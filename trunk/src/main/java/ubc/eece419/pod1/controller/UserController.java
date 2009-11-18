package ubc.eece419.pod1.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections15.map.ListOrderedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	@ModelAttribute("possibleRoles")
	public Map<String, String> exposePossibleRoles() {
		Map<String, String> roles = ListOrderedMap.decorate(new HashMap<String, String>());
		roles.put(Roles.USER, "User");
		roles.put(Roles.USER + "," + Roles.STAFF, "Staff");
		roles.put(Roles.USER + "," + Roles.STAFF + "," + Roles.ADMIN, "Admin");
		return roles;
	}

	@Override
	public ModelAndView save(User bound, BindingResult errors,
			@RequestParam(value = "view", required = false) String view) {

		User old = null;

		if (bound.isNewEntity()) {
			// don't check this later -- assuming a blank password field means no change
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "entityvalidator.nullable");
		}

		// needs to be set before validation!
		if (!bound.isNewEntity()) {
			old = userRepository.findById(bound.getId());
			// can't change username
			bound.setUsername(old.getUsername());
		}

		if (hasError(bound, errors)) {
			return editView(bound);
		}

		if (!(bound.isNewEntity() || bound.equals(SecurityUtils.getCurrentUser()))) {
			// only admins can edit /other/ users
			SecurityUtils.assertAdmin();
		}

		// TODO: is this a validation step? for now, it'll just take effect silently
		if (!SecurityUtils.isAdmin()) {
			bound.setRoles(Roles.USER);
		}

		if (!bound.isNewEntity()) {
			// handle salting the password
			if (StringUtils.hasText(bound.getPassword())) {
				if (!bound.getPassword().equals(old.getPassword())) {
					bound.setPassword(User.encryptPassword(bound.getPassword(), bound.getUsername()));
				}
			} else {
				bound.setPassword(old.getPassword());
			}
		} else {
			// handle salting the password
			bound.setPassword(User.encryptPassword(bound.getPassword(), bound.getUsername()));
		}
		bound = userRepository.save(bound);

		// login the user if needed
		if (SecurityUtils.currentUserIsAnonymous()) {
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
		// !!! Because we're in a transaction, this will get persisted !!!
		//entity.setPassword("");
		return editView(entity);
	}

	@Override
	public UserRepository getRepository() {
		return userRepository;
	}

	@RequestMapping("/**/login")
	public ModelAndView login(@RequestParam(value = "login_error", required = false) Integer error) {
		log.info("login " + getEntityName());
		ModelAndView mav = new ModelAndView("/user/login");
		if (error != null) {
			mav.addObject("login_error", error);
		}
		return mav;
	}

	@RequestMapping("/**/register")
	public ModelAndView register(User user, BindingResult errors) {
		log.info("register new user");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "entityvalidator.nullable");
		for (Validator v : getValidators()) {
			ValidationUtils.invokeValidator(v, user, errors);
		}

		if (errors.hasErrors()) {
			log.info(errors.toString());
			return registerForm(user);
		}

		user.setPassword(User.encryptPassword(user.getPassword(), user.getUsername()));
		user.setRoles("ROLE_USER");
		user = userRepository.save(user);

		SecurityUtils.login(user);
		ModelAndView mav = new ModelAndView("redirect:/");
		return mav;
	}

	@RequestMapping("/**/registerform")
	public ModelAndView registerForm(User user) {
		return new ModelAndView("/user/register", getEntityName(), user);
	}
}
