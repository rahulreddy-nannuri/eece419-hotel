package ubc.eece419.pod1.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ubc.eece419.pod1.entity.Databasable;

public abstract class GenericDao<T extends Databasable> implements GenericRepository<T> {

	@PersistenceContext
	protected EntityManager em;

	protected final Class<T> entityClass;

	protected GenericDao(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	protected String getTableName() {
		return entityClass.getSimpleName();
	}

	public T findById(long id) {
		// this will return null, rather than throw an EntityNotFoundEx, or somesuch
		return em.find(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return em.createQuery(String.format("select x from %s x", getTableName())).getResultList();
	}

	public T save(T entity) {
		return em.merge(entity);
	}

	public void delete(T entity) {
		em.remove(entity);
	}
}
