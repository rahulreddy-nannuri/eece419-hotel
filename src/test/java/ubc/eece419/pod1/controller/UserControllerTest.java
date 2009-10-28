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
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.servlet.ModelAndView;

import ubc.eece419.pod1.entity.User;
import ubc.eece419.pod1.dao.UserRepository;

public class UserControllerTest{

	Mockery context  = new Mockery();
	
	private UserRepository userRepository;
	private UserController userController;

	User getEntity() {
		// TODO Auto-generated method stub
		return new User();
	}

	@Before
	public void setUp() {
		// TODO Auto-generated method stub
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
	
	@Test
	public void testUserWithNoMissingValus(){
		final User user = new User("tempName", "tempPwd", "ROLE_ADMIN");
		
		context.checking(new Expectations(){
			{
				oneOf(userRepository).findAll();
				will(returnValue(Collections.emptyList()));
				
				//oneOf(userRepository).save(user);
				//will(returnValue(user));
			}
		});
		
		BeanPropertyBindingResult errors = new BeanPropertyBindingResult(user, "user");

		ModelAndView mav = userController.save(user, errors, null);
		
		assertEquals(0, errors.getErrorCount());
		
		User model = (User) mav.getModel().get("user");
		
		assertEquals("tempName", model.getUsername());
		assertEquals("tempPwd", model.getPassword());
		assertEquals("ROLE_ADMIN", model.getRoles());
	}
	
	@Test
	public void testEditUser(){
		final User user = new User();
		
		context.checking(new Expectations(){
			{
				oneOf(userRepository).findById(1L);
				will(returnValue(user));
				
				oneOf(userRepository).findAll();
			}
		});
	
		ModelAndView mav = userController.edit(null);
		
		assertEquals("/user/edit", mav.getViewName());
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testEmptyList(){
		List<User> entities = new ArrayList<User>();
		
		context.checking(new Expectations(){
			{
				oneOf(userRepository).findAll();
			}
		});

		ModelAndView mav = userController.list();
		List<User> model = (List<User>) mav.getModel().get(getEntity().getEntityName() + "s");
		assertEquals(entities.size(), model.size());
		assertEquals(model.size(), 0);
	}
	
	
}
