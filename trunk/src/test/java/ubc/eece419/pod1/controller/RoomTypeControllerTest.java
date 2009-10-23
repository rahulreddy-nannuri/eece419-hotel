package ubc.eece419.pod1.controller;

import java.util.Collections;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.servlet.ModelAndView;

import ubc.eece419.pod1.dao.RoomTypeRepository;
import ubc.eece419.pod1.entity.AbstractEntity;
import ubc.eece419.pod1.entity.RoomType;

import static org.junit.Assert.*;

public class RoomTypeControllerTest {

	Mockery context = new Mockery();
	RoomTypeRepository repo;
	RoomTypeController controller;

	@Before
	public void setUp() {
		repo = context.mock(RoomTypeRepository.class);
		controller = new RoomTypeController();
		controller.roomTypeRepository = repo;
	}

	public static boolean arrayContains(Object[] arr, Object toFind) {
		for (Object o : arr) {
			if (toFind == null) {
				if (o == null) return true;
			} else {
				if (toFind.equals(o)) return true;
			}
		}
		return false;
	}

	@Test
	public void saveRoomTypeWithNoNameFailsValidationTest() {
		RoomType nameless = new RoomType();
		nameless.setName(null);
		nameless.setDescription("description");
		nameless.setDailyRate(100.5);
		nameless.setMaxOccupancy(2);

		context.checking(new Expectations() {{
			// validator checks for duplicates
			one(repo).findAll();
			will(returnValue(Collections.emptyList()));

			// we won't see a repo.save(nameless);
		}});

		// I've not seen where Spring sets the BindingResult objectName, but AbstractEntity.entityName tries to match it.
		BeanPropertyBindingResult errors = new BeanPropertyBindingResult(nameless, "roonType");

		ModelAndView mav = controller.save(nameless, errors, null);

		assertEquals(1, errors.getErrorCount());
		assertTrue(arrayContains(errors.getFieldError("name").getCodes(), "entityvalidator.nullable"));

		assertEquals("/roomtype/edit", mav.getViewName());

		RoomType model = (RoomType) mav.getModel().get("roomType");
		assertEquals(null, model.getName());
		assertEquals("description", model.getDescription());
		assertEquals(100.5, model.getDailyRate(), 1e-9);
		assertEquals(2, model.getMaxOccupancy());
	}

	@Test
	public void saveRoomTypeWithNoMissingValuesShouldSucceed() {
		final RoomType nameless = new RoomType();
		nameless.setName("name");
		nameless.setDescription("description");
		nameless.setDailyRate(100.5);
		nameless.setMaxOccupancy(2);

		context.checking(new Expectations() {{
			// validator checks for duplicates
			one(repo).findAll();
			will(returnValue(Collections.emptyList()));

			one(repo).save(nameless);
			will(returnValue(nameless));
		}});

		BeanPropertyBindingResult errors = new BeanPropertyBindingResult(nameless, AbstractEntity.entityName(RoomType.class));

		ModelAndView mav = controller.save(nameless, errors, null);

		assertEquals(0, errors.getErrorCount());
		assertEquals("redirect:/roomtype/list", mav.getViewName());
	}

}
