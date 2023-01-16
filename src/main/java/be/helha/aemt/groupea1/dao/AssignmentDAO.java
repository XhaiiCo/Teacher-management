package be.helha.aemt.groupea1.dao;

import be.helha.aemt.groupea1.entities.Assignment;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

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

		return super.add(assignment);
	}
	
	
}
