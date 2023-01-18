package be.helha.aemt.groupea1.dao;

import java.util.List;

import be.helha.aemt.groupea1.entities.AA;
import be.helha.aemt.groupea1.entities.Department;
import be.helha.aemt.groupea1.entities.Section;
import be.helha.aemt.groupea1.entities.UE;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;

@Stateless
@LocalBean
public class UEDAO extends AbstractDAO<UE> {
	
	@EJB
	public DepartmentDAO departmentDAO;
	
	@EJB
	public SectionDAO sectionDAO;
	
	public UEDAO() {
		super(UE.class);
	}
	
	/**find the ue on his code
	 * 
	 * @return ue or null is not found
	 */
	public UE find(UE ue) {
		if(ue==null) return null;
		
		String rq = "SELECT ue FROM UE ue where ue.code=?1 and ue.academicYear=?2" ;
		
		TypedQuery<UE>query = em.createQuery(rq, UE.class);
		query.setParameter(1, ue.getCode());
		query.setParameter(2, ue.getAcademicYear());
		
		List<UE> result = query.getResultList();
		
		if (result.isEmpty()) return null;
		
		return result.get(0);
	}
	
	public UE add(UE ue) {
		if (ue == null) return null;
		
		if (find(ue) != null) return null;
			
		Department department = departmentDAO.find(ue.getSection().getDepartment());
		
		if(department != null) {
			ue.getSection().setDepartment(department);
		}
		
		Section section = sectionDAO.find(ue.getSection());
		
		if(section != null) {
			ue.setSection(section);
		}
		
		return super.add(ue);	
	}

	public UE update(UE ue) {
		
		UE oldUE = findById(ue.getId()) ;
		if(ue == null) return null ;
		
		if(oldUE.getCode().equals(ue.getCode()))
			return super.update(ue);

		if(find(ue) == null)
			return super.update(ue);
		
		return null ;
	}
	
	public UE fetchAllByAA(AA aa) {
		String rq = "SELECT ue FROM Ue ue where ue.code = ?1";

		TypedQuery<UE> query = em.createQuery(rq, UE.class);
		query.setParameter(1, aa.getCode());

		List<UE> result = query.getResultList();

		if(result.isEmpty()) return null ;

		return result.get(0);
	}

}
