package be.helha.aemt.groupea1.control;

import java.io.Serializable;
import java.util.List;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import be.helha.aemt.groupea1.ejb.TeacherEJB;
import be.helha.aemt.groupea1.entities.Teacher;
import be.helha.aemt.groupea1.exception.NotAvailableEmailException;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@SessionScoped
public class TeacherControl implements Serializable{

	private List<Teacher> teachers ;
	public List<Teacher> getTeachers() { return teachers; }
	public void setTeachers(List<Teacher> teachers) {this.teachers = teachers; }

	private Teacher newTeacher ;//Used when create a new teacher
	public Teacher getNewTeacher() {return newTeacher;}
	public void setNewTeacher(Teacher newTeacher) {this.newTeacher = newTeacher;}

	private Teacher removeTeacher ;
	public Teacher getRemoveTeacher() {return removeTeacher;}
	public void setRemoveTeacher(Teacher removeTeacher) {this.removeTeacher = removeTeacher;}

	@EJB
	private TeacherEJB teacherEJB ;

	@PostConstruct
	public void init() {
		this.teachers = this.teacherEJB.findAll() ;
	}

	public void showInfoToast(String summary, String detail ) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
	}

	public void showErrorToast(String summary, String detail ) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
	}

	public void onRowEdit(RowEditEvent<Teacher> event) {
		Teacher updatedTeacher = event.getObject() ;

		if(this.teacherEJB.update(updatedTeacher) != null) 
			this.showInfoToast("Modifié", "Enseignant modifié" );
		else
			this.showErrorToast("Erreur", new NotAvailableEmailException().getMessage());
	}

	public void onRowCancel(RowEditEvent<Teacher> event) {
		FacesMessage msg = new FacesMessage("Modification annulée");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}


	/**
	 * create a new teacher, called when the user click on the new button
	 */
	public void openNewTeacher() {
		this.newTeacher = new Teacher() ;
	}

	/**
	 * Save the new teacher in db
	 * Used when the use click on the add button
	 */
	public void saveNewTeacher() {
		Teacher addedTeacher = this.teacherEJB.add(newTeacher) ;

		if(addedTeacher != null) {
			this.teachers.add(addedTeacher) ;
			this.showInfoToast("Ajouté", "Enseignant ajouté");
		}
		else
			this.showErrorToast("Erreur", "Erreur lors de l'ajout, cet enseignant est peut être déjà présent");
	}

	public void removeTeacher() {
		if(this.removeTeacher == null) return ;

		Teacher removedTeacher = this.teacherEJB.delete(this.removeTeacher) ; 
		if(removedTeacher != null) {
			this.teachers.remove(removedTeacher) ;
			this.showInfoToast("Supprimé", "Enseignant supprimé");
		}
		else
			this.showErrorToast("Erreur", "Erreur lors de la suppression");
	}
}
