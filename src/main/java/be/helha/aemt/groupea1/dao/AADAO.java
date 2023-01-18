package be.helha.aemt.groupea1.dao;

import java.util.List;

import be.helha.aemt.groupea1.entities.AA;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;

@Stateless
@LocalBean
public class AADAO extends AbstractDAO<AA>{

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
		
		String rq = "Select a From AA a where a.code=?1" ;
		
		TypedQuery<AA>query = em.createQuery(rq,AA.class);
		query.setParameter(1,aa.getCode());
		
		List<AA> result=query.getResultList();
		
		if (result.isEmpty()) return null;
		return result.get(0);
	}
	
	/**
	 * Redefinition of the method to add a condition to check that there are no duplicates on code
	 */
	public AA add (AA aa) {
		if (aa == null) return null;
		
		if (find(aa)!=null) return null;
			
		return super.add(aa);	
	}
	
	/**
	 * Redefinition of the method to add a condition to check that there are no duplicates on the code
	 */
	/*
	public AA update(AA aa) {
	}

		//Find the old AA
		AA oldAA = findById(aa.getId()) ;
		if(aa == null) return null ;
		
		if(oldAA.getCode().equals(aa.getCode()))
			return super.update(aa);

		if(find(aa) == null)
			return super.update(aa);
		
		return null ;
	}
	*/
	
}
