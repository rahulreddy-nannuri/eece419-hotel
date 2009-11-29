package ubc.eece419.pod1.controller;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.anonymous.AnonymousAuthenticationToken;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.servlet.ModelAndView;

import ubc.eece419.pod1.entity.User;
import ubc.eece419.pod1.security.SecurityUtils;
import ubc.eece419.pod1.dao.UserRepository;

public class UserControllerTest {

	Mockery context  = new Mockery();

	UserRepository userRepository;
	UserController userController;

	@Before
	public void setUp() {
		userRepository = context.mock(UserRepository.class);
		userController = new UserController();
		userController.userRepository = userRepository;
	}

	@Test
	public void testSaveEmptyUser(){
		final User user = new User();

		context.checking(new Expectations(){{
			oneOf(userRepository).findAll();
			will(returnValue(Collections.emptyList()));
			}
		});

		BeanPropertyBindingResult errors = new BeanPropertyBindingResult(user, "user");

		ModelAndView mav = userController.save(user, errors, null);

		//how do i know how many errors do i have
		//2 error means two methods fail? getPassword and getAuthority?
		assertEquals(2, errors.getErrorCount());

		User model = (User) mav.getModel().get("user");

		assertEquals(null, model.getAddress());
		assertEquals(null, model.getUsername());
		assertEquals(null, model.getEmail());
		//not sure about getAuthorities()
		//assertEquals(null, model.getAuthorities());
		assertEquals("user", model.getEntityName());
		assertEquals(0, (int)model.getId());
		assertEquals("ROLE_USER", model.getRoles());

		assertEquals(1, mav.getModel().size());
	}

	public static void setLoggedInUser(User user) {
		if (user == null) {
			GrantedAuthority anon = new GrantedAuthorityImpl("ROLE_ANONYMOUS");
			AnonymousAuthenticationToken auth = new AnonymousAuthenticationToken("key", "Anonymous", new GrantedAuthority[] { anon });
			SecurityContextHolder.getContext().setAuthentication(auth);
		} else {
			SecurityUtils.login(user);
		}
	}

	@Test
	public void testUserWithNoMissingValues(){
		final User user = new User("tempName", "tempPwd", "ROLE_ADMIN");

		setLoggedInUser(null);

		context.checking(new Expectations() {{
			exactly(2).of(userRepository).findAll();
			will(returnValue(Collections.emptyList()));

			one(userRepository).save(user);
			will(returnValue(user));
		}});

		BeanPropertyBindingResult errors = new BeanPropertyBindingResult(user, "user");

		ModelAndView mav = userController.save(user, errors, null);

		assertEquals(0, errors.getErrorCount());
		assertEquals("redirect:/", mav.getViewName());
	}

	@Test
	public void testEditUser(){
		final User user = new User();

		context.checking(new Expectations(){{
			oneOf(userRepository).findById(1L);
			will(returnValue(user));

			oneOf(userRepository).findAll();
		}});

		ModelAndView mav = userController.edit(null);

		assertEquals("/user/edit", mav.getViewName());
	}



	@SuppressWarnings("unchecked")
	@Test
	public void testEmptyList(){
		final List<User> entities = new ArrayList<User>();

		context.checking(new Expectations() {{
			one(userRepository).findAll(null);
			will(returnValue(entities));
		}});

		ModelAndView mav = userController.list(null);
		List<User> model = (List<User>) mav.getModel().get("users");

		assertEquals(0, model.size());
	}
}
