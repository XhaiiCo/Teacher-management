package be.helha.aemt.groupea1.dao;

import java.util.List;

import be.helha.aemt.groupea1.entities.AA;
import be.helha.aemt.groupea1.entities.UE;
import jakarta.persistence.TypedQuery;


public class UEDAO extends AbstractDAO<UE> {
	
	public UEDAO() {
		super(UE.class);
	}
	
	/**find the ue on his code
	 * 
	 * @return ue or null is not found
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
	

	public UE add (UE ue) {
		if (ue == null) return null;
		
		if (find(ue)!=null) return null;
			
		return super.add(ue);	
	}


	public UE update (UE ue) {
		
		UE oldUE = findById(ue.getId()) ;
		if(ue == null) return null ;
		
		if(oldUE.getCode().equals(ue.getCode()))
			return super.update(ue);

		if(find(ue) == null)
			return super.update(ue);
		
		return null ;
	}

}
