package ubc.eece419.pod1.validator;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;

import ubc.eece419.pod1.dao.GenericRepository;
import ubc.eece419.pod1.entity.AbstractEntity;
import ubc.eece419.pod1.entity.User;

import static org.easymock.EasyMock.*;

public class ReflectionEntityValidatorTest {

	@Entity
	static class ExampleEntity extends AbstractEntity<ExampleEntity> {
		private static final long serialVersionUID = 1L;

		public Integer notNull = 7;
		public String notEmpty = "text";
		public String unique;

		@Column(nullable = false)
		public Integer getNotNull() {
			return notNull;
		}

		@Column(nullable = false)
		public String getNotEmpty() {
			return notEmpty;
		}

		@Column(unique = true)
		public String getUnique() {
			return unique = "unique";
		}
	}

	ReflectionEntityValidator<ExampleEntity> rev;
	GenericRepository<ExampleEntity> repo;
	Errors errors;

	@Before
	@SuppressWarnings("unchecked")
	public void setUp() {
		repo = createMock(GenericRepository.class);
		errors = createMock(Errors.class);

		expect(repo.getEntityClass()).andStubReturn(ExampleEntity.class);

		replay(repo);

		rev = new ReflectionEntityValidator<ExampleEntity>(repo);

		reset(repo);
	}

	@Test
	public void testSupports() {
		replay(repo, errors);

		assertTrue(rev.supports(ExampleEntity.class));
		assertFalse(rev.supports(String.class));
		assertFalse(rev.supports(User.class));

		verify(repo, errors);
	}

	@Test
	public void testValidateOk() {
		ExampleEntity entity = new ExampleEntity();

		expect(repo.findAll()).andReturn(Collections.EMPTY_LIST);

		replay(repo, errors);

		rev.validate(entity, errors);

		verify(repo, errors);
	}

	@Test
	public void testValidateNullFail() {
		ExampleEntity entity = new ExampleEntity();
		entity.notNull = null;

		expect(repo.findAll()).andReturn(Collections.EMPTY_LIST);
		errors.rejectValue("notNull", "entityvalidator.nullable");

		replay(repo, errors);

		rev.validate(entity, errors);

		verify(repo, errors);
	}

	@Test
	public void testValidateUniqueFail() {
		ExampleEntity entity = new ExampleEntity();
		entity.unique = "duplicate";

		ExampleEntity duplicate = new ExampleEntity();
		duplicate.unique = "duplicate";

		expect(repo.findAll()).andReturn(Arrays.asList(duplicate));
		errors.rejectValue("unique", "entityvalidator.unique");

		replay(repo, errors);

		rev.validate(entity, errors);

		verify(repo, errors);
	}

	@Test
	public void testValidateEmptyFail() {
		ExampleEntity entity = new ExampleEntity();
		entity.notEmpty = "";


		expect(repo.findAll()).andReturn(Collections.EMPTY_LIST);
		errors.rejectValue("notEmpty", "entityvalidator.nullable");

		replay(repo, errors);

		rev.validate(entity, errors);

		verify(repo, errors);
	}

}
