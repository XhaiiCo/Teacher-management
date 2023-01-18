package be.helha.aemt.groupea1.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import be.helha.aemt.groupea1.exception.NumberNegatifException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "ue", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code", "academicyear"})
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

	private Section section;
	
	@OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
	private List<AA> aas;
	
	public UE() {}
	
	public UE(String academicYear, String bloc, String code, String entitled,
			int credit, Section section) throws NumberNegatifException {
		this.academicYear = academicYear;
		this.bloc = bloc;
		this.code = code;
		this.entitled = entitled;
		setCredit(credit);
		this.aas= new ArrayList<AA>();
		this.section = section ;
	}
	
	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public void addAA(AA aa) {
		this.aas.add(aa) ;
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
		this.aas = aas;
	}


	@Override
	public String toString() {
		return "UE [id=" + id + ", academicYear=" + academicYear + ", bloc=" + bloc + ", code=" + code + ", entitled=" + entitled + ", credit=" + credit + ", aas=" + aas
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, id);
	}

	/**
	 * Equals on the id and the code
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UE other = (UE) obj;
		return Objects.equals(code, other.code) && id == other.id;
	}
}

