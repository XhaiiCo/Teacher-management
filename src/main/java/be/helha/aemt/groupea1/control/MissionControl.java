 package be.helha.aemt.groupea1.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.event.RowEditEvent;

import be.helha.aemt.groupea1.ejb.MissionEJB;
import be.helha.aemt.groupea1.ejb.TeacherEJB;
import be.helha.aemt.groupea1.entities.Mission;
import be.helha.aemt.groupea1.entities.MissionTransversale;
import be.helha.aemt.groupea1.entities.Teacher;
import be.helha.aemt.groupea1.exception.NotAvailableEmailException;
import be.helha.aemt.groupea1.util.Toast;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@SessionScoped
public class MissionControl implements Serializable {

	private List<Mission> missions;
	public List<Mission> getMissions() { return missions ;}
	public void setMissions(List<Mission> missions) {this.missions=missions;}

	private Mission newMission ;//Used when create a new mission
	public Mission getNewMission() {return newMission;}
	public void setNewMission(Mission newMission) {this.newMission = newMission;}

	private Mission removeMission;
	public Mission getRemoveMission() {return removeMission;}
	public void setRemoveMission(Mission removeMission) {this.removeMission = removeMission;}

	@EJB
	private MissionEJB missionEJB;

	@PostConstruct
	public void init () {
		this.missions = this.missionEJB.findAll();
	}
	
	public void onRowEdit(RowEditEvent<Mission> event) {
		Mission updatedMission = event.getObject() ;

		if(this.missionEJB.update(updatedMission) != null) 
			Toast.showInfoToast("Modifié", "Mission modifiée" );
		else
			Toast.showErrorToast("Erreur", new NotAvailableEmailException().getMessage());
	}

	public void onRowCancel(RowEditEvent<Mission> event) {
		FacesMessage msg = new FacesMessage("Modification annulée");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void openNewMission() {
		this.newMission = new MissionTransversale() ; // TODO
	}
	
	public void saveNewMission() {
		Mission missionAdd = this.missionEJB.add(newMission);
		
		if(missionAdd != null) {
			this.missions.add(missionAdd) ;
			Toast.showInfoToast("Ajouté", "Mission ajoutée");
		}
		else
			Toast.showErrorToast("Erreur", "Erreur lors de l'ajout, cette mission est peut être déjà présente");
		this.type = 1;
	}

	public void removeMission() {
		if(this.removeMission == null) return ;

		Mission removedMission = this.missionEJB.delete(this.removeMission); 
		if(removedMission != null) {
			this.missions.remove(removedMission);
			Toast.showInfoToast("Supprimé", "Mission supprimée");
		}
		else
			Toast.showErrorToast("Erreur", "Erreur lors de la suppression");
	}

}
