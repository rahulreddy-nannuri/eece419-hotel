package ubc.eece419.pod1.validator;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;

import ubc.eece419.pod1.dao.GenericRepository;
import ubc.eece419.pod1.entity.AbstractEntity;
import ubc.eece419.pod1.entity.User;

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

	Mockery context = new Mockery();

	ReflectionEntityValidator<ExampleEntity> rev;
	GenericRepository<ExampleEntity> repo;
	Errors errors;

	@Before
	@SuppressWarnings("unchecked")
	public void setUp() {
		repo = context.mock(GenericRepository.class);
		errors = context.mock(Errors.class);

		context.checking(new Expectations() {{
			allowing(repo).getEntityClass();
			will(returnValue(ExampleEntity.class));
		}});

		rev = new ReflectionEntityValidator<ExampleEntity>(repo);
	}

	@Test
	public void testSupports() {
		assertTrue(rev.supports(ExampleEntity.class));
		assertFalse(rev.supports(String.class));
		assertFalse(rev.supports(User.class));
	}

	@Test
	public void testValidateOk() {
		ExampleEntity entity = new ExampleEntity();

		context.checking(new Expectations() {{
			one(repo).findAll();
			will(returnValue(Collections.emptyList()));
		}});

		rev.validate(entity, errors);
	}

	@Test
	public void testValidateNullFail() {
		ExampleEntity entity = new ExampleEntity();
		entity.notNull = null;

		context.checking(new Expectations() {{
			one(repo).findAll();
			will(returnValue(Collections.emptyList()));

			one(errors).rejectValue("notNull", "entityvalidator.nullable");
		}});

		rev.validate(entity, errors);
	}

	@Test
	public void testValidateUniqueFail() {
		ExampleEntity entity = new ExampleEntity();
		entity.unique = "duplicate";

		final ExampleEntity duplicate = new ExampleEntity();
		duplicate.unique = "duplicate";

		context.checking(new Expectations() {{
			one(repo).findAll();
			will(returnValue(Arrays.asList(duplicate)));

			one(errors).rejectValue("unique", "entityvalidator.unique");
		}});

		rev.validate(entity, errors);
	}

	@Test
	public void testValidateEmptyFail() {
		ExampleEntity entity = new ExampleEntity();
		entity.notEmpty = "";

		context.checking(new Expectations() {{
			one(repo).findAll();
			will(returnValue(Collections.emptyList()));

			one(errors).rejectValue("notEmpty", "entityvalidator.nullable");
		}});

		rev.validate(entity, errors);
	}

}
