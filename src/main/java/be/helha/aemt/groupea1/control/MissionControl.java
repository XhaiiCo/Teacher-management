package be.helha.aemt.groupea1.control;

import java.io.Serializable;
import java.util.List;

import org.primefaces.event.RowEditEvent;

import be.helha.aemt.groupea1.ejb.MissionEJB;
import be.helha.aemt.groupea1.entities.Mission;
import be.helha.aemt.groupea1.exception.NotAvailableEmailException;
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

	public void showInfoToast(String summary, String detail ) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
	}

	public void showErrorToast(String summary, String detail ) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
	}
	
	public void onRowEdit(RowEditEvent<Mission> event) {
		Mission updatedMission = event.getObject() ;

		if(this.missionEJB.update(updatedMission) != null) 
			this.showInfoToast("Modifié", "Mission modifiée" );
		else
			this.showErrorToast("Erreur", new NotAvailableEmailException().getMessage());
	}

	public void onRowCancel(RowEditEvent<Mission> event) {
		FacesMessage msg = new FacesMessage("Modification annulée");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void openNewMission() {
		this.newMission = new Mission() ;
	}
	
	public void saveNewMission() {
		Mission missionAdd = this.missionEJB.add(newMission) ;

		if(missionAdd != null) {
			this.missions.add(missionAdd) ;
			this.showInfoToast("Ajouté", "Mission ajoutée");
		}
		else
			this.showErrorToast("Erreur", "Erreur lors de l'ajout, cette mission est peut être déjà présent");
	}

	public void removeMission() {
		if(this.removeMission == null) return ;

		Mission removedMission = this.missionEJB.delete(this.removeMission); 
		if(removedMission != null) {
			this.missions.remove(removedMission);
			this.showInfoToast("Supprimé", "Mission supprimée");
		}
		else
			this.showErrorToast("Erreur", "Erreur lors de la suppression");
	}

}
