package be.helha.aemt.groupea1.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UE implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id  ;
	
	private String academicYear ;

	private Department department ;
	
	private Section section ;
	
	private String bloc ;
	
	private String code ;
	
	private String entitled ;
	
	private int credit ;
	
	private List<AA> aas ;
	
	public UE() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public String getBloc() {
		return bloc;
	}

	public void setBloc(String bloc) {
		this.bloc = bloc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEntitled() {
		return entitled;
	}

	public void setEntitled(String entitled) {
		this.entitled = entitled;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public List<AA> getAas() {
		return aas;
	}

	public void setAas(List<AA> aas) {
		this.aas = aas;
	}
	
}

