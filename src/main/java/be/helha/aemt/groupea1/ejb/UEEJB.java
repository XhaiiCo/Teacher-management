package be.helha.aemt.groupea1.ejb;

import java.util.List;

import be.helha.aemt.groupea1.dao.UEDAO;
import be.helha.aemt.groupea1.entities.Mission;
import be.helha.aemt.groupea1.entities.Teacher;
import be.helha.aemt.groupea1.entities.UE;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class UEEJB {

	@EJB
	private UEDAO ueDAO;
	
	public List<UE> findAll() {
		return ueDAO.findAll() ;
	}
	
	public UE add(UE ue) {
		return ueDAO.add(ue) ;
	}

	public UE delete(UE ue) {
		return ueDAO.delete(ue);
	}

	public UE update(UE ue) {
		return ueDAO.update(ue);
	}
}
