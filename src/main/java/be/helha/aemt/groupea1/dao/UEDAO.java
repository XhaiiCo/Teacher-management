package be.helha.aemt.groupea1.dao;

import java.util.List;

import be.helha.aemt.groupea1.entities.AA;
import be.helha.aemt.groupea1.entities.UE;
import jakarta.persistence.TypedQuery;

public class UEDAO extends AbstractDAO<UE> {
	
	public UEDAO() {
		super(UE.class);
	}
	
	/**find the aa on his code
	 * 
	 * @return aa or null is not found
	 */
	public UE find(UE ue) {
		if(ue==null) return null;
		
		String rq = "Select ue From UE ue where ue.code=?1" ;
		
		TypedQuery<UE>query = em.createQuery(rq, UE.class);
		query.setParameter(1, ue.getCode());
		
		List<UE> result = query.getResultList();
		
		if (result.isEmpty()) return null;
		return result.get(0);
	}
	
	/**
	 * Redefinition of the method to add a condition to check that there are no duplicates on code
	 */
	public UE add (UE ue) {
		if (ue == null) return null;
		
		if (find(ue)!=null) return null;
			
		return super.add(ue);	
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
