package be.helha.aemt.groupea1.dao;

import be.helha.aemt.groupea1.entities.Assignment;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

@Stateless
@LocalBean
public class AssignmentDAO extends AbstractDAO<Assignment>{

	public AssignmentDAO() {
		super(Assignment.class);
	}
}