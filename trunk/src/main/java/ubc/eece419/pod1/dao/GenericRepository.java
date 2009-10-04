package ubc.eece419.pod1.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GenericRepository<T> {

	public T findById(long id);

	public List<T> findAll();

	public T save(T entity);

	public void delete(T entity);

}
