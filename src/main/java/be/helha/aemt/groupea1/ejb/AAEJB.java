package be.helha.aemt.groupea1.ejb;

import be.helha.aemt.groupea1.dao.AADAO;
import be.helha.aemt.groupea1.entities.AA;
import be.helha.aemt.groupea1.entities.Mission;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class AAEJB {

	@EJB
	private AADAO aaDAO;
	
	
	public AA add(AA aa) {
		return aaDAO.add(aa) ;
	}

	public AA delete(AA aa) {
		return aaDAO.delete(aa);
	}

	public AA update(AA aa) {
		return aaDAO.update(aa);
	}
}
