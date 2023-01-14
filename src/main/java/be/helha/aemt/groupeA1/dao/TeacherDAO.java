package be.helha.aemt.groupeA1.dao;

import java.util.List;

import be.helha.aemt.groupeA1.entities.Teacher;
import be.helha.aemt.groupeA1.util.Environment;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Stateless//Used to do independent operations, it does not have any associated client state
@LocalBean
public class TeacherDAO  extends AbstractDAO<Teacher>{

	public TeacherDAO() {
		super(Teacher.class) ;
	}

	@Override
	public Teacher add(Teacher teacher) {
		if(teacher == null) return null ;

		if(find(teacher) != null) return null ;

		return super.add(teacher);
	}

	/**
	 * Find the teacher based on his email
	 * 
	 * @return  the teacher or null if not found
	 */
	@Override
	public Teacher find(Teacher teacher) {
		if(teacher == null) return null ;

		String rq = "SELECT t FROM Teacher t where t.email = ?1" ;

		TypedQuery<Teacher> query = em.createQuery(rq, Teacher.class);
		query.setParameter(1, teacher.getEmail()) ;

		List<Teacher> result = query.getResultList() ;

		if(result.isEmpty()) return null ;

		return result.get(0) ; 
	}
}
