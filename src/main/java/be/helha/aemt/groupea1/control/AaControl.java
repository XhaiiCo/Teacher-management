package be.helha.aemt.groupea1.control;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import be.helha.aemt.groupea1.ejb.AAEJB;
import be.helha.aemt.groupea1.ejb.TeacherEJB;
import be.helha.aemt.groupea1.entities.AA;
import be.helha.aemt.groupea1.entities.Teacher;
import be.helha.aemt.groupea1.exception.InvalidEmailException;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@SessionScoped
public class AaControl implements Serializable {

	@EJB
	private AAEJB aaEJB ;

	@EJB
	private TeacherEJB teacherEJB ;

	private List<AA> aas ;
	public List<AA> getAas() {
		return aas ;
	}

	private List<Teacher> teachers ;
	public List<Teacher> getTeachers(){return this.teachers; }
	public void setTeachers(List<Teacher>  value) {this.teachers =value ; }

	private Map<String, String> teachersMap = new LinkedHashMap<>();
	public Map<String, String> getTeachersMap() {return teachersMap;	}
	public void setTeachersMap(Map<String, String> teachersMap) {this.teachersMap = teachersMap;	}

	private AA selected ;
	public AA getSelected() {return selected ;	}

	private String selectedTeacherEmail;
	public String getSelectedTeacherEmail() {return selectedTeacherEmail ;	}
	public void setSelectedTeacherEmail(String value) {this.selectedTeacherEmail = value;}

	public List<Teacher> getSelectedTeachers(){return selected.getTeachers() ;}

	@PostConstruct
	public void init() {
		this.aas = aaEJB.findAll() ;
		this.teachers = teacherEJB.findAll() ;
		for(Teacher teacher : this.teachers) {
			this.teachersMap.put(teacher.getEmail(), teacher.getEmail()) ;	
		}
	}

	public String openDetail(AA aa) {
		this.selected = aa ;
		try {
			this.selected.addTeacher(new Teacher("Leclercq", "David", "david@helha.be", null));
		} catch (InvalidEmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "/DDE/aaDetail" ;
	}
	public void showInfoToast(String summary, String detail ) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
	}

	public void showErrorToast(String summary, String detail ) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
	}

	public void addTeacher() {
		try {
			Teacher teacherToAdd = this.teacherEJB.findByEmail(new Teacher("", "", selectedTeacherEmail, null)) ;
			if(teacherToAdd == null) {
				this.showErrorToast("Erreur", "Erreur lors de l'ajout");
				return ;
			}
			this.selected.addTeacher(teacherToAdd);
			this.showInfoToast("Ajouté", selectedTeacherEmail + " ajouté");
		} catch (InvalidEmailException e) {
			e.printStackTrace();
			this.showErrorToast("Erreur", "Erreur lors de l'ajout");
		}
	}
}