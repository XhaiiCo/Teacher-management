package be.helha.aemt.groupeA1.dao;

import java.util.List;

/**
 * This class defines the signature of the basic methods of a DAO 
 * 
 * @author david
 *
 * @param <T> The entity concerned by the DAO
 */
public interface IDAO<T> {

	public T add(T t) ;

	public T find(T t) ;

	public T findById(Integer id) ;

	public List<T> findAll() ;

	public T update(T t) ;

	public T delete(T t) ;

}
