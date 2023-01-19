package be.helha.aemt.groupea1.ejb;

import java.util.List;

import be.helha.aemt.groupea1.dao.DepartmentDAO;
import be.helha.aemt.groupea1.entities.Department;
import be.helha.aemt.groupea1.entities.Utilisateur;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class DepartmentEJB {
	
	@EJB
	private DepartmentDAO departmentDAO ;
	
	public Department add(Department department) {
		return departmentDAO.add(department) ;
	}

	public List<Department> findAll() {
		return departmentDAO.findAll();
	}
	
}
