/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ubc.eece419.pod1.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ubc.eece419.pod1.entity.Databasable;

/**
 *
 * @author yang
 */
public abstract class GenericDao<T extends Databasable> implements GenericRepository<T> {

    @PersistenceContext
    protected EntityManager em;

    abstract Class<T> getClazz();

    protected String getTableName() {
        return getClazz().getSimpleName();
    }

    public T findById(long id) {
        // this will return null, rather than throw an EntityNotFoundEx, or somesuch
        return em.find(getClazz(), id);
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
