package ubc.eece419.pod1.entity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

import javax.persistence.Entity;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

@SuppressWarnings("serial")
public class AbstractEntityTest {

	Object one;
	Object two;

	static void printIndented(int indent, String msg) {
		for (int i = 0; i < indent; i++) {
			System.out.print("  ");
		}
		System.out.println(msg);
	}

	public static void printGenericClassHierarchy(Object o) {
		System.out.println();
		System.out.println("Class Hierarchy of " + o);
		int indent = 0;
		Class<?> clazz = o.getClass();
		while (clazz != null) {
			printIndented(indent, "Class: " + clazz);
			Type gs = clazz.getGenericSuperclass();
			printIndented(indent, "Generic Superclass: " + gs);
			if (gs != null)
				printIndented(indent, "GS class: " + gs.getClass());
			if (gs instanceof ParameterizedType) {
				printIndented(indent, "GS ActualTypeArgs: " + Arrays.toString(((ParameterizedType) gs).getActualTypeArguments()));
				printIndented(indent, "GS OwnerType: " + ((ParameterizedType) gs).getOwnerType());
				printIndented(indent, "GS RawType: " + ((ParameterizedType) gs).getRawType());
			}
			printIndented(indent, "Annotations: " + Arrays.toString(clazz.getAnnotations()));
			printIndented(indent, "TypeParams: " + Arrays.toString(clazz.getTypeParameters()));

			clazz = clazz.getSuperclass();
			indent++;
		}
	}

	@Test @Ignore
	public void showMe() {
		printGenericClassHierarchy(new ConcreteEntity());
		printGenericClassHierarchy(new ConcreteEntitySubclass());
		printGenericClassHierarchy(new UnrelatedEntity());
	}

	@Entity
	static class ConcreteEntity extends AbstractEntity<ConcreteEntity> {
		public ConcreteEntity() {}
		public ConcreteEntity(long id) { setId(id); }
	}

	static class ConcreteEntitySubclass extends ConcreteEntity {
		public ConcreteEntitySubclass() {}
		public ConcreteEntitySubclass(long id) { super(id); }
	}

	@Entity
	static class UnrelatedEntity extends AbstractEntity<UnrelatedEntity> {
		public UnrelatedEntity() {}
		public UnrelatedEntity(long id) { setId(id); }
	}

	@Test
	public void SameClassDifferentIdUnequalTest() {
		one = new ConcreteEntity(1);
		two = new ConcreteEntity(2);
		assertFalse(one.equals(two));
		assertFalse(two.equals(one));
	}

	@Test
	public void SameClassEqualIdEqualTest() {
		one = new ConcreteEntity(1);
		two = new ConcreteEntity(1);
		assertTrue(one.equals(two));
		assertTrue(two.equals(one));
	}

	// This is wrong for a number of reasons, but required if
	// hibernate is to create dynamic proxies of our entities
	@Test
	public void SubclassEqualIdEqualTest() {
		one = new ConcreteEntity(1);
		two = new ConcreteEntitySubclass(1);
		assertTrue(one.equals(two));
		assertTrue(two.equals(one));
	}

	@Test
	public void UnrelatedEntityEqualIdUnequalTest() {
		one = new ConcreteEntity(1);
		two = new UnrelatedEntity(1);
		assertFalse(one.equals(two));
		assertFalse(two.equals(one));
	}

	@Test
	public void UnrelatedClassUnequalTest() {
		one = new ConcreteEntity(1);
		two = new Object();
		assertFalse(one.equals(two));
		assertFalse(two.equals(one));
	}

}
