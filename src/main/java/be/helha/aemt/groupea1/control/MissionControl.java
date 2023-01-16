package be.helha.aemt.groupea1.control;

import java.io.Serializable;
import java.util.List;

import be.helha.aemt.groupea1.ejb.MissionEJB;
import be.helha.aemt.groupea1.entities.Mission;
import be.helha.aemt.groupea1.entities.Teacher;
import jakarta.ejb.EJB;

public class MissionControl implements Serializable{

	
	private List<Mission> missions;
	public List<Mission> getMission() { return missions ;}
	public void setMission(List<Mission> missions) {this.missions=missions;}
	
	
	private Mission newMission ;//Used when create a new mission
	public Mission getNewMission() {return newMission;}
	public void setNewMission(Mission newTeacher) {this.newMission = newMission;}
	
	@EJB
	private MissionEJB missionEJB;
	
	public void init () {
		this.missions=this.missionEJB.findAll();
	}
	
	public void openNewMission() {
		this.newMission = new Mission() ;
	}
	
	public void saveNewMission() {
		Mission missionAdd = this.missionEJB.add(newMission) ;

		if(missionAdd != null) {
			this.missions.add(missionAdd) ;
		}
	}

}
