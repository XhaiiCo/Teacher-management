package be.helha.aemt.groupea1.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import be.helha.aemt.groupea1.exception.NumberNegatifException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "ue", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"code", "academicyear", "section_id"})
})
public class UE implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;

	private String academicYear;

	private String bloc;

	private String code;

	private String entitled;

	private int credit;

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private Section section;

	@OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
	private List<AA> aas = new ArrayList<>();

	public UE() {}

	public UE(String code, String academicYear, Section section) {
		this.code = code;
		this.academicYear = academicYear;
		this.section = section;
	}

	public UE(String academicYear, String bloc, String code, String entitled,
			int credit, Section section) throws NumberNegatifException {
		this.academicYear = academicYear;
		this.bloc = bloc;
		this.code = code;
		this.entitled = entitled;
		setCredit(credit);
		this.section = section ;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public boolean addAA(AA aa) {
		if(this.aas.contains(aa)) return false ;

		return this.aas.add(aa) ;
	}
	
	public void removeAllAA() {
		this.aas.clear();
	}
	
	public int nbAA() {
		return this.aas.size();
	}

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

	public void setCredit(int credit) throws NumberNegatifException {
		if( credit >= 0 ) this.credit = credit;

		else throw new NumberNegatifException();
	}

	public List<AA> getAas() {
		return aas;
	}

	public void setAas(List<AA> aas) {
		this.aas = new ArrayList<>() ;
		aas.forEach(aa -> this.addAA(aa)) ;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, section);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UE other = (UE) obj;
		return Objects.equals(code, other.code) && Objects.equals(section, other.section);
	}

}

