package be.helha.aemt.groupea1.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.event.RowEditEvent;

import be.helha.aemt.groupea1.ejb.MissionEJB;
import be.helha.aemt.groupea1.ejb.TeacherEJB;
import be.helha.aemt.groupea1.entities.Department;
import be.helha.aemt.groupea1.entities.Mission;
import be.helha.aemt.groupea1.entities.MissionDepartment;
import be.helha.aemt.groupea1.entities.MissionSection;
import be.helha.aemt.groupea1.entities.MissionTransversale;
import be.helha.aemt.groupea1.entities.Section;
import be.helha.aemt.groupea1.entities.Teacher;
import be.helha.aemt.groupea1.exception.InvalidHoursException;
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

	private int type;
	public int getType() {return type;}
	public void setType(int type) {this.type = type;}

	// Section for new mission;
	private Section sectionMission;
	public Section getSectionMission() {return sectionMission;}
	public void setSectionMission(Section section) {this.sectionMission = section;}

	// Department for new mission;
	private Department departmentMission;
	public Department getDepartmentMission() {return departmentMission;}
	public void setDepartmentMission(Department department) {this.departmentMission = department;}

	private Mission removeMission;
	public Mission getRemoveMission() {return removeMission;}
	public void setRemoveMission(Mission removeMission) {this.removeMission = removeMission;}

	private Mission selectedMission ;
	public Mission getSelectedMission() {return selectedMission;	}
	public void setSelectedMission(Mission selectedMission) {this.selectedMission = selectedMission;	}

	private Teacher teacherToRemove ;
	public Teacher getTeacherToRemove() {	return teacherToRemove;}
	public void setTeacherToRemove(Teacher teacherToRemove) {this.teacherToRemove = teacherToRemove;}

	@EJB
	private MissionEJB missionEJB;

	@PostConstruct
	public void init () {
		this.missions = this.missionEJB.findAll();
		this.type = 1;
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
		this.newMission = new MissionTransversale();
	} 

	public void saveNewMission() {
		if (this.type == 2) {
			try {
				newMission = new MissionDepartment(newMission.getAcademicYear(), newMission.getEntitled(), newMission.getHours(), newMission.getTeachers(), departmentMission);
			} catch (InvalidHoursException e) {
				Toast.showErrorToast("Erreur", e.getMessage());
			}
		} else if (type == 3) {
			try {
				newMission = new MissionSection(newMission.getAcademicYear(), newMission.getEntitled(), newMission.getHours(), newMission.getTeachers(), sectionMission);
			} catch (InvalidHoursException e) {
				Toast.showErrorToast("Erreur", e.getMessage());
			}
		}

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

	public String goToDetailPage(Mission mission) {

		this.setSelectedMission(mission);

		return "/loggedUser/DDE/missionDetail.xhtml" ;
	}

	public void removeTeacher() {
		this.selectedMission.removeTeacher(teacherToRemove);
		this.missionEJB.update(this.selectedMission) ;
		Toast.showInfoToast("Désattribué", "Enseignant désattribué");
	}
}
