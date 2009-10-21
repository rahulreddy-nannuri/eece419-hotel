package ubc.eece419.pod1.validator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.persistence.Column;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ubc.eece419.pod1.controller.CRUDController;
import ubc.eece419.pod1.dao.GenericRepository;
import ubc.eece419.pod1.entity.Databasable;

public class ReflectionEntityValidator<T extends Databasable<?>> implements Validator {

	// because controller have their repository injected, they can't pass it to us in their constructor
	// because InitializingBean doesn't work... we do this
	private CRUDController<T> controller;
	private GenericRepository<T> repository;
	private final Class<T> targetClass;

	public ReflectionEntityValidator(GenericRepository<T> repository) {
		this.repository = repository;
		this.controller = null;
		this.targetClass = repository.getEntityClass();
	}

	public ReflectionEntityValidator(CRUDController<T> controller) {
		this.repository = null;
		this.controller = controller;
		this.targetClass = controller.getEntityClass();
	}

	public GenericRepository<T> getRepository() {
		return (repository == null) ? controller.getRepository() : repository;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class clazz) {
		return targetClass.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// get fields & getters with @Column annotations
		Field[] fields = target.getClass().getFields();
		for (Field f : fields) {
			Column annot = f.getAnnotation(Column.class);
			SupressEntityValidation suppress = f.getAnnotation(SupressEntityValidation.class);

			if (suppress == null && annot != null) {
				validateField(target, f, annot, errors);
			}
		}

		Method[] methods = target.getClass().getMethods();
		for (Method m : methods) {
			Column annot = m.getAnnotation(Column.class);
			SupressEntityValidation suppress = m.getAnnotation(SupressEntityValidation.class);

			if (suppress == null && annot != null) {
				validateMethod(target, m, annot, errors);
			}
		}
	}

	private void nullCheck(Object value, String field, Errors errors) {
		if (value == null) {
			errors.rejectValue(field, "entityvalidator.nullable");
		} else if (value instanceof String) {
			if (((String) value).isEmpty()) {
				errors.rejectValue(field, "entityvalidator.nullable");
			}
		}
	}

	// this can't see private fields! annotate the getters instead
	protected void validateField(Object target, Field field, Column annot, Errors errors) {
		try {
			Object value = field.get(target);

			if (!annot.nullable()) {
				nullCheck(value, field.getName(), errors);
			}

			if (annot.unique()) {
				for (T existing : getRepository().findAll()) {
					if (!existing.equals(target)) {
						Object o = field.get(existing);
						if (o != null && o.equals(value)) {
							errors.rejectValue(field.getName(), "entityvalidator.unique");
							break;
						}
					}
				}
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	protected void validateMethod(Object target, Method method, Column annot, Errors errors) {
		try {
			Object value = method.invoke(target);
			String fieldName = method.getName();
			if (fieldName.startsWith("get")) {
				fieldName = fieldName.substring(3);
				fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
			}

			if (!annot.nullable()) {
				nullCheck(value, fieldName, errors);
			}

			if (annot.unique()) {
				for (T existing : getRepository().findAll()) {
					if (!existing.equals(target)) {
						Object o = method.invoke(existing);
						if (o != null && o.equals(value)) {
							errors.rejectValue(fieldName, "entityvalidator.unique");
							break;
						}
					}
				}
			}
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
