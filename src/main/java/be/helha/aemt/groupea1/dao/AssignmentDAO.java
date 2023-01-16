package be.helha.aemt.groupea1.dao;

import java.util.List;

import be.helha.aemt.groupea1.entities.Assignment;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;

@Stateless//Used to do independent operations, it does not have any associated client state
@LocalBean
public class AssignmentDAO extends AbstractDAO<Assignment> {

	public AssignmentDAO() {
		super(Assignment.class) ;
	}
	
	@Override
	/**
	 * Redefinition of the method to add a condition to check that there are no duplicates on the id
	 */
	public Assignment add(Assignment assignment) {
		if(assignment == null) return null ;

		if(find(assignment) != null) return null ;

		return super.add(assignment);
	}
	
	@Override
	/**
	 * Redefinition of the method to add a condition to check that there are no duplicates on the id
	 */
	public Assignment find(Assignment assignment) {
		if(assignment == null) return null ;

		String rq = "SELECT a FROM Assignment a where a.id = ?1" ;

		TypedQuery<Assignment> query = em.createQuery(rq, Assignment.class);
		query.setParameter(1, assignment.getId()) ;

		List<Assignment> result = query.getResultList() ;

		if(result.isEmpty()) return null ;

		return result.get(0) ; 
	}
	
	
	
}
