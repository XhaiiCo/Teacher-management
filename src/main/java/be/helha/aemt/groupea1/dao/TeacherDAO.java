package be.helha.aemt.groupea1.dao;

import java.util.ArrayList;
import java.util.List;

import be.helha.aemt.groupea1.entities.AA;
import be.helha.aemt.groupea1.entities.Assignment;
import be.helha.aemt.groupea1.entities.Mission;
import be.helha.aemt.groupea1.entities.Teacher;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;

@Stateless//Used to do independent operations, it does not have any associated client state
@LocalBean
public class TeacherDAO extends AbstractDAO<Teacher>{

	@EJB
	private AADAO aaDAO ;

	@EJB
	private AssignmentDAO assignmentDAO;

	@EJB
	private MissionDAO missionDAO ;


	public TeacherDAO() {
		super(Teacher.class) ;
	}

	@Override
	/**
	 * Redefinition of the method to add a condition to check that there are no duplicates on the email
	 */
	public Teacher add(Teacher teacher) {
		if(teacher == null) return null ;

		if(find(teacher) != null) return null ;

		return super.add(teacher);
	}

	@Override
	/**
	 * Redefinition of the method to add a condition to check that there are no duplicates on the email
	 */
	public Teacher update(Teacher teacher) {

		//Find the old teacher
		Teacher oldTeacher = findById(teacher.getId()) ;
		if(oldTeacher == null) return null ;

		if(oldTeacher.getEmail().equals(teacher.getEmail()))
			return super.update(teacher);

		if(find(teacher) == null)
			return super.update(teacher);

		return null ;
	}

	/**
	 * Find the teacher based on his email
	 * 
	 * @return  the teacher or null if not found
	 */
	public Teacher find(Teacher teacher) {
		if(teacher == null) return null ;

		String rq = "SELECT t FROM Teacher t where t.email = ?1" ;

		TypedQuery<Teacher> query = em.createQuery(rq, Teacher.class);
		query.setParameter(1, teacher.getEmail()) ;

		List<Teacher> result = query.getResultList() ;

		if(result.isEmpty()) return null ;

		return result.get(0) ; 
	}

	@Override
	public Teacher delete(Teacher teacher) {
		if(teacher == null) return null;

		//Remove assignements
		List<AA> aas = aaDAO.findByTeacher(teacher) ;
		aas.forEach(aa -> {
			List<Assignment> toRemoves= new ArrayList<>() ;
			aa.getAssignments().forEach(assignment -> {
				if(assignment.getTeacher().equals(teacher)) {
					toRemoves.add(assignment) ;
				}
			});
			toRemoves.forEach(toRemove -> {
				aa.removeAssignment(toRemove) ;
				assignmentDAO.delete(toRemove) ;
			});
			aaDAO.update(aa) ;
		});

		//Remove in missions
		List<Mission> missions = missionDAO.findByTeacher(teacher) ;
		List<Mission> toRemoves = new ArrayList<>() ;
		missions.forEach(mission -> {
			toRemoves.add(mission) ;
		});

		toRemoves.forEach(toRemove -> {
			toRemove.removeTeacher(teacher) ;
			missionDAO.update(toRemove) ;
		});

		return  super.delete(teacher);
	}
}