package ubc.eece419.pod1.dao;

import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ubc.eece419.pod1.entity.Databasable;
import ubc.eece419.pod1.reflection.ReflectionUtils;

public abstract class GenericDao<T extends Databasable<?>> implements GenericRepository<T> {
	private static final Logger log = Logger.getLogger(GenericDao.class.getName());

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

	public T save(T entity) {
		T refreshedEntity=em.merge(entity);
		//em.refresh(refreshedEntity);
		return refreshedEntity;
	}

	public void delete(T entity) {
		em.remove(entity);
	}
}
