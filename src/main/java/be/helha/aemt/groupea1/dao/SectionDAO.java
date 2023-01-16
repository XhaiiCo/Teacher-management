package be.helha.aemt.groupea1.dao;

import be.helha.aemt.groupea1.entities.Department;
import be.helha.aemt.groupea1.entities.Section;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

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
		
		return super.add(section);
	}
	
	
}
