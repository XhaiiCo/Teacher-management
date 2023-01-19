package be.helha.aemt.groupea1.control;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import be.helha.aemt.groupea1.ejb.AAEJB;
import be.helha.aemt.groupea1.ejb.AssignmentEJB;
import be.helha.aemt.groupea1.ejb.TeacherEJB;
import be.helha.aemt.groupea1.entities.AA;
import be.helha.aemt.groupea1.entities.Assignment;
import be.helha.aemt.groupea1.entities.EAssignationStatus;
import be.helha.aemt.groupea1.entities.EQuarter;
import be.helha.aemt.groupea1.entities.Teacher;
import be.helha.aemt.groupea1.exception.InvalidEmailException;
import be.helha.aemt.groupea1.exception.NumberNegatifException;
import be.helha.aemt.groupea1.exception.OutOfBoundNbAssignement;
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
	private AssignmentEJB assignmentEJB ;

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

	private Assignment selectedAssignment ;
	public Assignment getSelectedAssignment() {return selectedAssignment;}
	public void setSelectedAssignment(Assignment selectedAssignment) {this.selectedAssignment = selectedAssignment;	}

	private String selectedTeacherEmail;
	public String getSelectedTeacherEmail() {return selectedTeacherEmail ;	}
	public void setSelectedTeacherEmail(String value) {this.selectedTeacherEmail = value;}

	private int newGroup = 1 ;
	public int getNewGroup() {return newGroup ;	}
	public void setNewGroup(int value) {this.newGroup = value;}

	private Map<String, Integer> groupsMap = new LinkedHashMap<>();
	public Map<String, Integer> getGroupsMap() {return groupsMap;}
	public void setGroupsMap(Map<String, Integer> groupsMap) {this.groupsMap = groupsMap;}

	private EQuarter newQuarter = EQuarter.Q1;
	public EQuarter getNewQuarter() {return newQuarter ;	}
	public void setNewQuarter(EQuarter value) {this.newQuarter = value;}

	private int newHoursAssignment = 1 ;
	public int getNewHoursAssignment() {return newHoursAssignment ;}
	public void setNewHoursAssignment(int value) {this.newHoursAssignment = value;}

	public List<Assignment> getSelectedAssignments(){
		return this.selected.getAssignments() ;
	}

	@PostConstruct
	public void init() {
		this.aas = aaEJB.findAll() ;
		this.teachers = teacherEJB.findAll() ;
		for(Teacher teacher : this.teachers) {
			this.teachersMap.put(teacher.getLastName() + " " + teacher.getFirstName() + " (" +  teacher.getEmail() + ")", teacher.getEmail()) ;	
		}
	}

	public EQuarter[] getQuarters() {
		return EQuarter.values();
	}

	public EAssignationStatus[] getAssignation()
	{
		return EAssignationStatus.values();
	}

	public String openDetail(AA aa) {
		this.selected = aa ;
		this.newGroup =  1 ;
		this.onItemSelectQuarter(); 
		if(!this.teachers.isEmpty())
			this.selectedTeacherEmail = this.teachers.get(0).getEmail() ;

		return "/DDE/aaDetail" ;
	}

	public Map<String, Integer> generateGroupsMap(){
		int nbGroup = this.selected.getNbGroupQ1() ;
		if(this.newQuarter != null && this.newQuarter.equals(EQuarter.Q2)) 
			nbGroup = this.selected.getNbGroupQ2() ;

		Map<String, Integer> result = new LinkedHashMap<>() ;

		for(int i = 1 ; i <= nbGroup ; i++)
			result.put("Groupe " + i , i) ;

		return result ; 
	}
	
	public void onItemSelectQuarter() {
		this.setGroupsMap(generateGroupsMap());
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

			try {
				Assignment newAssignment = new Assignment(teacherToAdd, newQuarter, newHoursAssignment, newGroup) ;

				this.selected.addAssignment(newAssignment);
			} catch (OutOfBoundNbAssignement | NumberNegatifException e ) {
				this.showErrorToast("Erreur", e.getMessage());
				return ;
			} 

			this.selected = aaEJB.update(this.selected) ;
			this.showInfoToast("Ajouté", selectedTeacherEmail + " ajouté");
		} catch (InvalidEmailException e) {
			e.printStackTrace();
			this.showErrorToast("Erreur", "Erreur lors de l'ajout");
		}
	}

	public void removeAssignment() {
		this.selected.removeAssignment(this.selectedAssignment) ;
		this.aaEJB.update(this.selected) ;
		this.assignmentEJB.delete(this.selectedAssignment) ;
		this.showInfoToast("Désattribué", "Enseignant désattribué");
	}
}