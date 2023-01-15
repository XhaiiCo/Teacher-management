package be.helha.aemt.groupeA1.dao;

import java.util.List;

import be.helha.aemt.groupeA1.entities.Mission;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;

@Stateless
@LocalBean
public class MissionDAO extends AbstractDAO<Mission>{
	

	public MissionDAO() {
		super(Mission.class);
	}

	/** find the mission on his entitled
	 * 
	 * @return the mision or null if not found
	 *
	 */
	public Mission find(Mission mission) {
		if (mission==null) return null;
		
		String rq = "SELECT m From Mission m where m.entitled=?1";
		
		TypedQuery<Mission>query = em.createQuery(rq,Mission.class);
		query.setParameter(1,mission.getEntitled());
		
		List<Mission> result=query.getResultList();
		
		if (result.isEmpty()) return null;
		return result.get(0);
	}
	
	public Mission add(Mission mission) {
		if (mission==null) return null;
		
		if(find(mission)!=null) return null;
		
		return super.add(mission);
	}
	
	
}
