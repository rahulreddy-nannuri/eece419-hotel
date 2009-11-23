package ubc.eece419.pod1.dao;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ubc.eece419.pod1.entity.Databasable;
import ubc.eece419.pod1.entity.Reservation;
import ubc.eece419.pod1.reflection.ReflectionUtils;

public abstract class GenericDao<T extends Databasable<?>> implements GenericRepository<T> {

	@PersistenceContext
	protected EntityManager em;

	protected final Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public GenericDao() {
		Type dt = ReflectionUtils.getInterfaceGenericParameters(getClass(), GenericRepository.class)[0];
		if (!(dt instanceof Class<?>)) {
			throw new IllegalStateException("Subclassing GenericDao without specifiying Databaseable type");
		}
		this.entityClass = (Class<T>) dt;
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
	
	public List<T> findAll(String filter) {
		return findAll();
	}
	
	public T save(T entity) {
		return em.merge(entity);
	}

	public void delete(T entity) {
		em.remove(entity);
	}
}
