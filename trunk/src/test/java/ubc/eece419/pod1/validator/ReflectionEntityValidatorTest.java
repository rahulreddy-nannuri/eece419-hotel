package ubc.eece419.pod1.validator;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;

import ubc.eece419.pod1.dao.GenericRepository;
import ubc.eece419.pod1.entity.AbstractEntity;

public class ReflectionEntityValidatorTest {

	@Entity
	static class ExampleEntity extends AbstractEntity<ExampleEntity> {
		private static final long serialVersionUID = 1L;

		public String unique;

		@Column(nullable = false)
		public String notNull;

		@Column(unique = true)
		public String getUnique() {
			return unique;
		}
	}

	ReflectionEntityValidator<ExampleEntity> rev;
	GenericRepository<ExampleEntity> repo;
	Errors errors;

	@Before
	public void setUp() {
		repo = EasyMock.createMock(GenericRepository.class);
		errors = EasyMock.createMock(Errors.class);

		rev = new ReflectionEntityValidator<ExampleEntity>(repo);
	}

	@Test
	public void testSupports() {
		assertTrue(rev.supports(ExampleEntity.class));
		assertFalse(rev.supports(String.class));
	}

	@Test
	public void testValidateOk() {

		ExampleEntity entity = new ExampleEntity();
		entity.notNull = "NotNull";
		entity.unique = "Unique";

		EasyMock.expect(repo.findAll()).andReturn(Collections.EMPTY_LIST);

		EasyMock.replay(repo);
		EasyMock.replay(errors);

		rev.validate(entity, errors);

		EasyMock.verify(repo);
		EasyMock.verify(errors);
	}

	@Test
	public void testValidateFail() {

		ExampleEntity entity = new ExampleEntity();
		entity.notNull = null;
		entity.unique = "Unique";

		ExampleEntity duplicate = new ExampleEntity();
		duplicate.notNull = "7";
		duplicate.unique = "Unique";

		EasyMock.expect(repo.findAll()).andReturn(Arrays.asList(duplicate));
		errors.rejectValue("notNull", "entityvalidator.nullable");
		EasyMock.expectLastCall();
		errors.rejectValue("unique", "entityvalidator.unique");
		EasyMock.expectLastCall();

		EasyMock.replay(repo);
		EasyMock.replay(errors);

		rev.validate(entity, errors);

		EasyMock.verify(repo);
		EasyMock.verify(errors);
	}

}
