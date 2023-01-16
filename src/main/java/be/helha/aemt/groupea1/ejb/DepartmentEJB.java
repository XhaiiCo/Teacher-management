package be.helha.aemt.groupea1.ejb;

import be.helha.aemt.groupea1.dao.DepartmentDAO;
import be.helha.aemt.groupea1.entities.Department;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class DepartmentEJB {
	
	@EJB
	private DepartmentDAO departmentDAO ;
	
	public Department add(Department department) {
		return departmentDAO.add(department) ;
	}
	
}
