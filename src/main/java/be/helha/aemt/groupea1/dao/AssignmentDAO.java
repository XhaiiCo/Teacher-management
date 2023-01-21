package be.helha.aemt.groupea1.dao;

import java.util.ArrayList;
import java.util.List;

import be.helha.aemt.groupea1.entities.Assignment;
import be.helha.aemt.groupea1.entities.UE;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;

@Stateless
@LocalBean
public class AssignmentDAO extends AbstractDAO<Assignment>{

	public AssignmentDAO() {
		super(Assignment.class);
	}
	
	
	public List<Assignment> findByAA(int id) {
		
		String rq = "SELECT assignment FROM assignment assignment INNER JOIN a.aa_assignment aa"
				+ " INNER JOIN aa.aa a where a.id=?1";
		
		TypedQuery<Assignment>query = em.createQuery(rq, Assignment.class);
		query.setParameter(1, id);
	
		
		List<Assignment> result = query.getResultList();
		
		if (result.isEmpty()) return null;
		
		return result;
		
	}
}