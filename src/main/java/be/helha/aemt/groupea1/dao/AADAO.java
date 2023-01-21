package be.helha.aemt.groupea1.dao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.helha.aemt.groupea1.entities.AA;
import be.helha.aemt.groupea1.entities.Assignment;
import be.helha.aemt.groupea1.entities.Department;
import be.helha.aemt.groupea1.entities.Section;
import be.helha.aemt.groupea1.entities.Teacher;
import be.helha.aemt.groupea1.entities.UE;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;

@Stateless
@LocalBean
public class AADAO extends AbstractDAO<AA>{

	@EJB
	private DepartmentDAO departmentDAO;

	@EJB
	private SectionDAO sectionDAO;

	@EJB
	private UEDAO ueDAO;

	@EJB
	private AssignmentDAO assignmentDAO;

	public AADAO()
	{
		super(AA.class);
	}

	/**find the aa on his code
	 * 
	 * @return aa or null is not found
	 */
	public AA find(AA aa) {
		if(aa==null) return null;

		String rq = "Select a From AA a where a.code=?1 and a.ue.id=?2" ;

		TypedQuery<AA>query = em.createQuery(rq, AA.class);
		query.setParameter(1, aa.getCode());
		query.setParameter(2, aa.getUe().getId());

		List<AA> result=query.getResultList();

		if (result.isEmpty()) return null;
		return result.get(0);
	}

	/**
	 * Redefinition of the method to add a condition to check that there are no duplicates on code
	 */
	public AA add (AA aa) {
		if (aa == null) return null;

		Department department = departmentDAO.find(aa.getUe().getSection().getDepartment());

		if(department != null) {
			aa.getUe().getSection().setDepartment(department);
		}

		Section section = sectionDAO.find(aa.getUe().getSection());

		if(section != null) {
			aa.getUe().setSection(section);
		}

		UE ue = ueDAO.find(aa.getUe());

		if(ue != null) {
			aa.setUe(ue);
		}

		if (find(aa)!=null) return null;

		return super.add(aa);	
	}

	public List<AA> findByTeacher(Teacher teacher){
		if(teacher == null) return null ;

		List<AA> aas = super.findAll() ;
		List<AA> result = new ArrayList<AA>() ;

		aas.forEach(aa -> {
			aa.getAssignments().forEach((Assignment a) -> {
				if(a.getTeacher().equals(teacher)) {
					result.add(aa) ;
					return ;
				}
			}) ;
		});

		return result ;
	}

	public Map<AA, Integer> computeNbHoursInAAsForTeacher(Teacher teacher){
		if(teacher == null) return null ;

		Map<AA, Integer> result = new HashMap<>() ;

		this.findByTeacher(teacher).forEach(aa -> {
			int cpt = 0 ;
			for(Assignment assignment : aa.getAssignments()) {
				if(assignment.getTeacher().equals(teacher))
					cpt += assignment.getNbHours() ;
			}
			result.put(aa, cpt) ;
		});

		return result ;
	}

	public List<AA> findAllByUe (UE ue)
	{
		if(ue==null) return null;

		String rq = "Select a From AA a where a.ue.id=?2" ;

		TypedQuery<AA>query = em.createQuery(rq, AA.class);
		query.setParameter(2, ue.getId());

		List<AA> result=query.getResultList();

		if (result.isEmpty()) return null;
		return result;
	}
}
