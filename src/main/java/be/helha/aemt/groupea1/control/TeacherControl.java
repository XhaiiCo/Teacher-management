package be.helha.aemt.groupea1.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.primefaces.event.RowEditEvent;

import be.helha.aemt.groupea1.ejb.AAEJB;
import be.helha.aemt.groupea1.ejb.TeacherEJB;
import be.helha.aemt.groupea1.entities.AA;
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

	private Teacher selectedTeacher ;
	public Teacher getSelectedTeacher() {return selectedTeacher;}
	public void setSelectedTeacher(Teacher selectedTeacher) {this.selectedTeacher = selectedTeacher;}

	@EJB
	private TeacherEJB teacherEJB ;

	@EJB
	private AAEJB aaEJB ;


	@PostConstruct
	public void init() {
		this.teachers = this.teacherEJB.findAll() ;
	}

	public String goToDetailPage(Teacher selectedTeacher) {

		this.setSelectedTeacher(selectedTeacher);

		return "/S/teacherDetail" ;
	}

	public void onRowEdit(RowEditEvent<Teacher> event) {
		Teacher updatedTeacher = event.getObject() ;

		if(this.teacherEJB.update(updatedTeacher) != null) 
			Toast.showInfoToast("Modifié", "Enseignant modifié" );
		else
			Toast.showErrorToast("Erreur", new NotAvailableEmailException().getMessage());
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
			Toast.showInfoToast("Ajouté", "Enseignant ajouté");
		}
		else
			Toast.showErrorToast("Erreur", "Erreur lors de l'ajout, cet enseignant est peut être déjà présent");
	}

	public void removeTeacher() {
		if(this.removeTeacher == null) return ;

		Teacher removedTeacher = this.teacherEJB.delete(this.removeTeacher) ; 
		if(removedTeacher != null) {
			this.teachers.remove(removedTeacher) ;
			Toast.showInfoToast("Supprimé", "Enseignant supprimé");
		}
		else
			Toast.showErrorToast("Erreur", "Erreur lors de la suppression");
	}

	public List<AA> findAAByTeacher(){
		return this.aaEJB.findByTeacher(this.selectedTeacher) ;
	}

	public List<AA> findDistinctAAByTeacher(){
		List<AA> aas = this.aaEJB.findByTeacher(this.selectedTeacher) ;

		//Remove duplicate
		Set<AA> set = new LinkedHashSet<>(aas);
		List<AA> listWithoutDuplicates = new ArrayList<>(set);

		return listWithoutDuplicates ;
	}
	
	public float computeWorkload() {
		
		Map<AA, Integer> aa_hours = aaEJB.computeNbHoursInAAsForTeacher(selectedTeacher);
		
		float ratio = 0;
		for (var entry : aa_hours.entrySet()) 
		{
			int nbHours = aa_hours.get(entry.getKey());
		    int fraction = entry.getKey().getFraction().getFraction();
		    
		    ratio += Teacher.computeRatio(nbHours, fraction);   
		}
		
		return ratio * 10;	
	}
	
}
