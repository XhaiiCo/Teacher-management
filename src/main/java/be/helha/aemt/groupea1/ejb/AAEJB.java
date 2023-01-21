package be.helha.aemt.groupea1.ejb;

import java.util.List;
import java.util.Map;

import be.helha.aemt.groupea1.dao.AADAO;
import be.helha.aemt.groupea1.entities.AA;
import be.helha.aemt.groupea1.entities.Teacher;
import be.helha.aemt.groupea1.entities.UE;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class AAEJB {

	@EJB
	private AADAO aaDAO;
	
	
	public List<AA> findAll(){
		return aaDAO.findAll() ;
	}

	public AA add(AA aa) {
		return aaDAO.add(aa) ;
	}

	public AA delete(AA aa) {
		return aaDAO.delete(aa);
	}

	public AA update(AA aa) {
		return aaDAO.update(aa);
	}
	
	public List<AA> findByTeacher(Teacher teacher){
		return this.aaDAO.findByTeacher(teacher) ; 
	}
	
	public Map<AA, Integer> computeNbHoursInAAsForTeacher(Teacher teacher){
		return aaDAO.computeNbHoursInAAsForTeacher(teacher);
	}
	
	public List<AA> findAllByUe (UE ue){
		return aaDAO.findAllByUe(ue);
	}
}
