package ubc.eece419.pod1.controller;

import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.easymock.classextension.EasyMock;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import ubc.eece419.pod1.entity.User;
import ubc.eece419.pod1.dao.GenericRepository;
import ubc.eece419.pod1.dao.UserRepository;

public class UserControllerTest{

	@SuppressWarnings("unchecked")
	private GenericRepository repository;
	@SuppressWarnings("unchecked")
	private CRUDController controller;
	private BindingResult bindingResult;

	User getEntity() {
		// TODO Auto-generated method stub
		return new User();
	}

	@Before
	public void setUp() {
		// TODO Auto-generated method stub
		UserRepository userRepository = EasyMock.createMock(UserRepository.class);
		UserController userController = new UserController();
		userController.userRepository = userRepository;

		repository = userRepository;
		controller = userController;
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testList(){
		List<User> entities = new ArrayList<User>();
		entities.add(getEntity());
		entities.add(getEntity());

		EasyMock.expect(repository.findAll()).andReturn(entities);
		EasyMock.replay(repository);

		ModelAndView mav = controller.list();
		EasyMock.verify(repository);

		List<User> model = (List<User>) mav.getModel().get(getEntity().getEntityName() + "s");

		assertEquals(entities.size(), model.size());
	}

	//still working on it
    @Test @Ignore
    public void testSave() {
        User entity = getEntity();

        EasyMock.expect(repository.save(entity)).andReturn(entity);
        EasyMock.expect(repository.findAll()).andReturn(Collections.EMPTY_LIST);
        EasyMock.replay(repository);

        bindingResult = EasyMock.createMock(BindingResult.class);
        EasyMock.expect(bindingResult.hasErrors()).andReturn(false).anyTimes();
        EasyMock.replay(bindingResult);

        ModelAndView mav = controller.save(entity, bindingResult,null);
        EasyMock.verify(repository);
        //EasyMock.verify(bindingResult);

        //assertEquals("redirect:/user/list", mav.getViewName());
    }



}
