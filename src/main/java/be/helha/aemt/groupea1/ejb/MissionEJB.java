package be.helha.aemt.groupea1.ejb;

import java.util.List;

import be.helha.aemt.groupea1.dao.MissionDAO;
import be.helha.aemt.groupea1.entities.Mission;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class MissionEJB {

	@EJB
	private MissionDAO missionDAO;
	
	public List<Mission> findAll() {
		return missionDAO.findAll() ;
	}
	
	public Mission add(Mission mission) {
		return missionDAO.add(mission) ;
	}

	public Mission delete(Mission mission) {
		return missionDAO.delete(mission);
	}

	public Mission update(Mission mission) {
		return missionDAO.update(mission);
	}
	
}
