package be.helha.aemt.groupeA1.dao;

import java.util.List;

import be.helha.aemt.groupeA1.util.Environment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * This class implements some methods that can be generic,
 *  the others must be redefined.
 * 
 * @author david
 *
 * @param <T> The entity concerned by the DAO
 */
public abstract class AbstractDAO<T> implements IDAO<T> {
	
	/**
	 * The class of the T param
	 */
	private Class<T> cls ;

	@PersistenceContext(unitName = Environment.PERSISTENCE_UNIT_NAME)
	protected EntityManager em ;//Get a reference from the server

	public AbstractDAO(Class<T> cls) {
		this.cls = cls ;
	}

	@Override
	public T add(T t) {
		if(t == null) return t ;
		
		em.persist(t);
		return t;
	}

	@Override
	public T findById(Integer id) {
		if(id == null) return null ;
		T result = em.find(cls, id) ;

		return result ;
	}

	@Override
	public List<T> findAll() {
		String jpqlQuery = "SELECT t FROM " + cls.getName() + " t" ;

		Query query = em.createQuery(jpqlQuery) ;		

		return query.getResultList() ;	

	}

	@Override
	public T delete(T t) {
		if(t == null) return t;
		
		T toRemove = em.merge(t) ;
		em.remove(toRemove);

		return t;
	}
	
	@Override
	public T update(T t) {
		if (t == null) return null ;

		T result = em.merge(t) ;

		return result;
	}
}
