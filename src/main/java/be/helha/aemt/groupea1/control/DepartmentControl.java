package be.helha.aemt.groupea1.control;

import java.io.Serializable;
import java.util.List;

import be.helha.aemt.groupea1.ejb.DepartmentEJB;
import be.helha.aemt.groupea1.entities.Department;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class DepartmentControl implements Serializable {

	@EJB
	private DepartmentEJB departmentEJB ;

	private List<Department> departments ;
	public List<Department> getDepartments() { return departments;	}
	
	@PostConstruct
	public void init() {
		this.fetchDepartments();
	}
	
	private void fetchDepartments() {
		this.departments = this.departmentEJB.findAll() ;
	}
	
}
