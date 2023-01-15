package be.helha.aemt.groupeA1.control;

import java.io.Serializable;
import java.util.List;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import be.helha.aemt.groupeA1.ejb.TeacherEJB;
import be.helha.aemt.groupeA1.entities.Teacher;
import be.helha.aemt.groupeA1.exception.NotAvailableEmailException;
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

	public void saveNewTeacher() {
		System.out.println(newTeacher);
	}
}
