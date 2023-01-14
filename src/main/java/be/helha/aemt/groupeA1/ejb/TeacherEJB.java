package be.helha.aemt.groupeA1.ejb;

import java.util.List;

import be.helha.aemt.groupeA1.dao.TeacherDAO;
import be.helha.aemt.groupeA1.entities.Teacher;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class TeacherEJB {

	@EJB
	private TeacherDAO teacherDAO ;//Get a reference from the server
	
	public List<Teacher> findAll() {
		return teacherDAO.findAll() ;
	}

	public Teacher add(Teacher enseignant) {
		return teacherDAO.add(enseignant) ;
	}

	public Teacher delete(Teacher enseignant) {
		return teacherDAO.delete(enseignant);
	}

	public Teacher update(Teacher enseignant) {
		return teacherDAO.update(enseignant);
	}
}
