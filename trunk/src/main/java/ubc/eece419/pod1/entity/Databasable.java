package ubc.eece419.pod1.entity;

import java.io.Serializable;

public interface Databasable<T> extends Serializable {
	long getId();

	void setId(long id);

	String getName();
}
