package ubc.eece419.pod1.entity;

import java.lang.reflect.Type;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import ubc.eece419.pod1.reflection.ReflectionUtils;

@MappedSuperclass
public abstract class AbstractEntity<T> implements Databasable<T> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public static String entityName(Class<?> clazz) {
		String name = clazz.getSimpleName();
		name = name.substring(0, 1).toLowerCase() + name.substring(1);
		return name;
	}

	@Override
	public String getEntityName() {
		return entityName(getClass());
	}

	@Transient
	public boolean isNewEntity() {
		return id < 1;
	}

	// TODO: do we want to use id like this, or only as a surrogate key?
	// this is an appropriate way of doing it for anonymous things, like stay records, perhaps
	// user -> username
	// room -> room#
	@Override
	public int hashCode() {
		// as from java.lang.Long.hashCode()
		return (int) (id ^ (id >>> 32));
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;

		if (!(obj instanceof AbstractEntity<?>)) return false;
		AbstractEntity<T> other = (AbstractEntity<T>) obj;
		if (abstractEntityType(this) != abstractEntityType(other)) return false;

		// TODO: handle pre-persisted equals()
		return !isNewEntity() && (id == other.id);
	}

	// this is hilarious, and probably pointless
	static Type abstractEntityType(AbstractEntity<?> entity) {
		return ReflectionUtils.getGenericParameter(entity.getClass(), AbstractEntity.class);
	}

	@Override
	public String toString() {
		return ((Class<?>)abstractEntityType(this)).getSimpleName() + "[" + id + "]";
	}

}
