package be.helha.aemt.groupeA1.control;

import java.io.Serializable;
import java.util.List;

import be.helha.aemt.groupeA1.ejb.TeacherEJB;
import be.helha.aemt.groupeA1.entities.Teacher;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class TeacherControl implements Serializable{
	
	
	@EJB
	private TeacherEJB teacherEJB ;
	
	public List<Teacher> doFindAll() {
		return teacherEJB.findAll() ;
	}
}
