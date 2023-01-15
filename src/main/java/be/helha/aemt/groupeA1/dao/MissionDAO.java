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
	 * @return the mission or null if not found
	 *
	 */
	public Mission find(Mission mission) {
		if (mission==null) return null;
		
		String rq = "SELECT m From Mission m where m.entitled=?1 and m.academicYear=?2";
		
		TypedQuery<Mission>query = em.createQuery(rq,Mission.class);
		query.setParameter(1,mission.getEntitled());
		query.setParameter(2,mission.getAcademicYear());
		
		List<Mission> result=query.getResultList();
		
		if (result.isEmpty()) return null;
		return result.get(0);
	}
	@Override
	/**
	 * Redefinition of the method to add a condition to check that there are no duplicates on entitle and academicYear
	 */
	public Mission add(Mission mission) {
		if (mission==null) return null;
		
		if(find(mission)!=null) return null;
		
		return super.add(mission);
	}
	
	/**
	 * Redefinition of the method to add a condition to check that there are no duplicates on the entitle and the academicYear
	 */
	public Mission update(Mission mission) {

		//Find the old Mission
		Mission oldMission = findById(mission.getId()) ;
		if(mission == null) return null ;
		
		if(oldMission.getEntitled().equals(mission.getEntitled()) && oldMission.getAcademicYear().equals(mission.getAcademicYear()))
			return super.update(mission);

		if(find(mission) == null)
			return super.update(mission);
		
		return null ;
	}
	
	
}
