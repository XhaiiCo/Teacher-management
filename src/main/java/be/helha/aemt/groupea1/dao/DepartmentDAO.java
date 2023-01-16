package be.helha.aemt.groupea1.dao;

import java.util.List;

import be.helha.aemt.groupea1.entities.Department;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;

@Stateless//Used to do independent operations, it does not have any associated client state
@LocalBean
public class DepartmentDAO extends AbstractDAO<Department>{
	
	public DepartmentDAO() {
		super(Department.class) ;
	}
	
	@Override
	/**
	 * Redefinition of the method to add a condition to check that there are no duplicates on the name
	 */
	public Department add(Department department) {
		if(department == null) return null ;

		if(find(department) != null) return null ;

		return super.add(department);
	}
	
	/**
	 * Find the department based on his email
	 * 
	 * @return the department or null if not found
	 */
	public Department find(Department department) {
		if(department == null) return null ;

		String rq = "SELECT d FROM Department d where d.name = ?1" ;

		TypedQuery<Department> query = em.createQuery(rq, Department.class);
		query.setParameter(1, department.getName()) ;

		List<Department> result = query.getResultList() ;

		if(result.isEmpty()) return null ;

		return result.get(0) ; 
	}
	
	
	
}
