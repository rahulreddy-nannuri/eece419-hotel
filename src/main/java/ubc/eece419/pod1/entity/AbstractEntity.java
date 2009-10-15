package ubc.eece419.pod1.entity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class AbstractEntity<T> implements Databasable {
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

	@Override
	public String getName() {
		return this.getClass().getSimpleName().toLowerCase();
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
		return id == other.id;
	}

	// this is hilarious, and probably pointless
	static Type abstractEntityType(AbstractEntity<?> entity) {
		Class<?> clazz = entity.getClass();
		while (clazz != null) {
			Type gs = clazz.getGenericSuperclass();
			if (gs instanceof ParameterizedType) {
				if (AbstractEntity.class.equals(((ParameterizedType) gs).getRawType())) {
					Type[] ata = ((ParameterizedType) gs).getActualTypeArguments();
					if (ata.length == 1) {
						return ata[0];
					} else {
						return null;
					}
				}
			}
			clazz = clazz.getSuperclass();
		}
		return null;
	}

}
