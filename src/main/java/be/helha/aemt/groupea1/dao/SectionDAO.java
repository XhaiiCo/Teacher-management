package be.helha.aemt.groupea1.dao;

import java.util.List;

import be.helha.aemt.groupea1.entities.Department;
import be.helha.aemt.groupea1.entities.Section;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;

@Stateless//Used to do independent operations, it does not have any associated client state
@LocalBean
public class SectionDAO extends AbstractDAO<Section> {

	@EJB
	private DepartmentDAO departmentDAO;
	
	public SectionDAO() {
		super(Section.class);
	}

	@Override
	/**
	 * Redefinition of the method to check if the department is existing
	 */
	public Section add(Section section) {
		if(section == null) return null ;

		Department department = departmentDAO.find(section.getDepartment());
		
		if(department != null) {
			section.setDepartment(department);
		}
		
		if(find(section) != null) return null ;
		
		return super.add(section);
	}
	
	/**
	 * Find the section based on his department and name
	 * 
	 * @return the sec or null if not found
	 */
	public Section find(Section section) {
		
		if(section == null) return null ;

		String rq = "SELECT s FROM Section s where s.name = ?1 and s.department.id = ?2";
		
		TypedQuery<Section> query = em.createQuery(rq, Section.class);
		query.setParameter(1, section.getName());
		query.setParameter(2, section.getDepartment().getId());
		
		List<Section> result = query.getResultList() ;
		
		if(result.isEmpty()) return null;

		return result.get(0); 
	}
	
	
}
