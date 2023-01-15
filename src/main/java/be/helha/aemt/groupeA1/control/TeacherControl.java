package be.helha.aemt.groupeA1.control;

import java.io.Serializable;
import java.util.List;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import be.helha.aemt.groupeA1.ejb.TeacherEJB;
import be.helha.aemt.groupeA1.entities.Teacher;
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

	@EJB
	private TeacherEJB teacherEJB ;

	@PostConstruct
	public void init() {
		this.teachers = this.teacherEJB.findAll() ;
	}

	public void onRowEdit(RowEditEvent<Teacher> event) {
		Teacher updatedTeacher = event.getObject() ;
		String resultMsg = "Erreur lors de la modification";

		if(this.teacherEJB.update(updatedTeacher) != null) {
			resultMsg = "Enseignant modifié";
		}
		else {
			resultMsg = "Erreur lors de la modification de l'enseignant" ;
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(resultMsg));
	}

	public void onRowCancel(RowEditEvent<Teacher> event) {
		FacesMessage msg = new FacesMessage("Modification annulée");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}
