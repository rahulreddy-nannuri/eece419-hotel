package ubc.eece419.pod1.reflection;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Type;

import org.junit.Ignore;
import org.junit.Test;

import ubc.eece419.pod1.entity.AbstractEntity;
import ubc.eece419.pod1.entity.Databasable;
import ubc.eece419.pod1.entity.User;

public class ReflectionUtilsTest {

	static interface AnInterface<T> {}

	static class StringImplementor implements AnInterface<String> {}

	static class StringImplementorSubclass extends StringImplementor {}

	static class AbstractImplementor<T> implements AnInterface<T> {}

	static class ConcreteImplementor extends AbstractImplementor<String> {}

	Type[] types;

	@Test
	public void InterfaceGenericParametersTest() {
		assertEquals(String.class, ReflectionUtils.getGenericParameter(StringImplementor.class, AnInterface.class));
		assertEquals(String.class, ReflectionUtils.getGenericParameter(StringImplementorSubclass.class, AnInterface.class));
	}

	@Test @Ignore("not sure if there is any way to get around this one (erasure ftl)")
	public void AbstractSuperclassInterfaceGenericParametersTest() {
		assertEquals(String.class, ReflectionUtils.getGenericParameter(ConcreteImplementor.class, AnInterface.class));
	}

	@Test
	public void AbstractSuperclassGenericParametersTest() {
		assertEquals(String.class, ReflectionUtils.getGenericParameter(ConcreteImplementor.class, AbstractImplementor.class));
	}

	@Test
	public void UserGenericClassParametersTest() {
		types = ReflectionUtils.getClassGenericParameters(User.class, AbstractEntity.class);
		assertEquals(1, types.length);
		assertEquals(User.class, types[0]);
	}

	@Test @Ignore // same as AbstractSuperclassInterfaceGenericParametersTest
	public void UserGenericInterfaceParametersTest() {
		types = ReflectionUtils.getInterfaceGenericParameters(User.class, Databasable.class);
		assertEquals(1, types.length);
		assertEquals(User.class, types[0]);
	}

}
