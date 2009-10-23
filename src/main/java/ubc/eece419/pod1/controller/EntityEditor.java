package ubc.eece419.pod1.controller;

import java.beans.PropertyEditorSupport;

import ubc.eece419.pod1.dao.GenericRepository;
import ubc.eece419.pod1.entity.Databasable;

/**
 * transform entites to/from their id, to use in request binding
 */
public class EntityEditor<T extends Databasable<?>> extends PropertyEditorSupport {

	private final GenericRepository<T> repo;

	public EntityEditor(GenericRepository<T> repo) {
		if (repo == null) throw new NullPointerException("repo cannot be null");
		this.repo = repo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getAsText() {
		T val = (T) getValue();
		if (val == null) {
			return "null";
		}
		return String.valueOf(val.getId());
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (null == text || "".equals(text) || "null".equals(text)) {
			setValue(null);
		} else {
			long id = Long.parseLong(text);
			setValue(repo.findById(id));
		}
	}
}
