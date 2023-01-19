package be.helha.aemt.groupea1.entities;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import be.helha.aemt.groupea1.exception.NumberNegatifException;
import be.helha.aemt.groupea1.exception.AllHoursAssignmedException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "aa", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"code", "ue_id"})
})
public class AA implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id  ;

	private String code ;

	private String entitled ;

	private int credit ;

	private int hoursQ1 ;

	private int hoursQ2 ;

	private int nbGroupQ1 ;

	private int nbGroupQ2 ;

	private int nbStudent ;

	@Enumerated(EnumType.STRING)
	private EFraction fraction ;

	@OneToMany(cascade = {CascadeType.ALL, CascadeType.MERGE})
	private List<Assignment> assignments = new ArrayList<>();

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private UE ue;

	public  AA() {}

	public AA(String code, String entitled, int credit, int hoursQ1, int hoursQ2,
			int nbGroupQ1, int nbGroupQ2, int nbStudent, 
			EFraction fraction, UE ue) throws NumberNegatifException  {
		this.code = code;
		this.entitled = entitled;
		this.credit = credit;
		this.setHoursQ1(hoursQ1);
		this.setHoursQ2(hoursQ2);
		this.setNbGroupQ1(nbGroupQ1);
		this.setNbGroupQ2(nbGroupQ2);
		this.setNbStudent(nbStudent);
		this.fraction = fraction;
		this.ue = ue;
	}

	public int getNbGroupQ1() {
		return nbGroupQ1;
	}

	public void setNbGroupQ1(int nbGroupQ1) throws NumberNegatifException{
		if(nbGroupQ1 < 0) throw new NumberNegatifException() ;

		this.nbGroupQ1 = nbGroupQ1;
	}

	public int getNbGroupQ2() {
		return nbGroupQ2;
	}

	public void setNbGroupQ2(int nbGroupQ2) throws NumberNegatifException {
		if(nbGroupQ2 < 0) throw new NumberNegatifException() ;

		this.nbGroupQ2 = nbGroupQ2;
	}

	public void addAssignment(Assignment assignment) throws AllHoursAssignmedException, NumberNegatifException{
		if(this.computeNbHoursAssignmentForAGroup(assignment.getQuarter(), assignment.getNumGroup()) + assignment.getNbHours() > nbHoursForQuarter(assignment.getQuarter()))
			throw new AllHoursAssignmedException() ;

		this.assignments.add(assignment) ;
	}

	public int computeNbHoursAssignmentForAGroup(EQuarter quarter, int group) {
		int result = 0 ;

		for(Assignment a : this.assignments) {
			if(a.getQuarter().equals(quarter) && a.getNumGroup() == group)
				result += a.getNbHours() ;
		}

		return result ;
	}

	public int nbHoursForQuarter(EQuarter quarter) {
		if(quarter.equals(EQuarter.Q1)) 
			return this.hoursQ1 ; 

		return this.hoursQ2 ;
	}

	public void removeAssignment(Assignment assignment) {
		this.assignments.remove(assignment) ;	
	}

	public int computeNbHours() {
		return this.hoursQ1 + this.hoursQ2 ;
	}

	public String assignStatus() {
		if(this.assignments.isEmpty())
			return EAssignationStatus.Nothing.getText() ;

		if(this.isAssignationDone())
			return EAssignationStatus.Done.getText() ;

		return EAssignationStatus.InProgress.getText() ;

	}

	public boolean isAssignationDone() {
		for(int i = 1 ; i <= this.nbGroupQ1 ; i++) {
			if(this.computeNbHoursAssignmentForAGroup(EQuarter.Q1, i) != this.hoursQ1)
				return false ;
		}

		for(int i = 1 ; i <= this.nbGroupQ2 ; i++) {
			if(this.computeNbHoursAssignmentForAGroup(EQuarter.Q2, i) != this.hoursQ2)
				return false ;
		}

		return true ;
	}

	public List<Assignment> getAssignments() {
		return assignments;
	}

	public int computeNbAssignments() {
		return assignments.size() ;
	}

	public UE getUe() {
		return ue;
	}

	public void setUe(UE ue) {
		this.ue = ue;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getHoursQ1() {
		return hoursQ1;
	}

	public void setHoursQ1(int hoursQ1)throws NumberNegatifException {
		if( hoursQ1 >= 0 ) this.hoursQ1 = hoursQ1;

		else throw new NumberNegatifException();

	}

	public int getHoursQ2() {
		return hoursQ2;
	}

	public void setHoursQ2(int hoursQ2) throws NumberNegatifException {
		if( hoursQ2 >= 0 ) this.hoursQ2 = hoursQ2;

		else throw new NumberNegatifException();
	}

	public int getNbStudent() {
		return nbStudent;
	}

	public void setNbStudent(int nbStudent) throws NumberNegatifException {
		if( nbStudent >= 0 ) this.nbStudent = nbStudent;

		else throw new NumberNegatifException();
	}

	public EFraction getFraction() {
		return fraction;
	}

	public void setFraction(EFraction fraction) {
		this.fraction = fraction;
	}

	@Override
	public String toString() {
		return "AA [id=" + id + ", code=" + code + ", entitled=" + entitled + ", credit=" + credit + ", hoursQ1="
				+ hoursQ1 + ", hoursQ2=" + hoursQ2 + ", nbGroupQ1=" + nbGroupQ1 + ", nbGroupQ2=" + nbGroupQ2
				+ ", nbStudent=" + nbStudent + ", fraction=" + fraction + ", assignments=" + assignments + ", ue=" + ue
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, ue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AA other = (AA) obj;
		return Objects.equals(code, other.code) && Objects.equals(ue, other.ue);
	}
}
