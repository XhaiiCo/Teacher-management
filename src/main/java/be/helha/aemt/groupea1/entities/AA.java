package be.helha.aemt.groupea1.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import be.helha.aemt.groupea1.exception.HoursNotWantedException;
import be.helha.aemt.groupea1.exception.NumberNegatifException;
import be.helha.aemt.groupea1.exception.OutOfBoundNbAssignement;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AA implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id  ;

	@Column(unique=true)
	private String code ;

	private String entitled ;

	private int credit ;

	private int hoursQ1 ;

	private int hoursQ2 ;

	private int nbGroup ;

	private int nbStudent ;

	private EFraction fraction ;

	private Map<Teacher, Integer> teachers = new HashMap<>();

	private UE ue ;

	public  AA() {}

	public AA(String code, String entitled, int credit, int hoursQ1, int hoursQ2,
			int nbGroup, int nbStudent, EFraction fraction, UE ue
			) throws NumberNegatifException, HoursNotWantedException {
		super();
		this.code = code;
		this.entitled = entitled;
		this.credit = credit;
		this.setHoursQ1(hoursQ1);
		this.setHoursQ2(hoursQ2);
		this.nbGroup = nbGroup;
		this.setNbStudent(nbStudent);
		this.fraction = fraction;
		this.ue = ue;
	}

	public void addTeacher(Teacher teacher, int nbAssignements) throws OutOfBoundNbAssignement{
		if(nbAssignements + computeNbAssignements() > nbGroup)
			throw new OutOfBoundNbAssignement() ;

		this.teachers.put(teacher, nbAssignements) ;	
	}

	public void removeTeachers(Teacher teacher) {
		this.teachers.remove(teacher) ;	
	}

	public String assignStatus() {
		if(this.computeNbAssignements() == this.nbGroup) 
			return EAssignationStatus.Done.getText() ;

		if(this.computeNbAssignements() > 0)
			return EAssignationStatus.InProgress.getText() ;

		return EAssignationStatus.Nothing.getText() ;
	}

	public Map<Teacher, Integer> getTeachers() {
		return teachers;
	}

	public int computeNbAssignements() {
		return this.teachers.values().stream().reduce(0, (a,b) -> a + b) ;
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

	public int getNbGroup() {
		return nbGroup;
	}

	public void setNbGroup(int nbGroup) throws NumberNegatifException {
		if( nbGroup >= 0 ) this.nbGroup = nbGroup;

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
		return "AA [id=" + id +  ", code=" + code + ", entitled=" + entitled
				+ ", credit=" + credit + ", hoursQ1=" + hoursQ1 + ", hoursQ2=" + hoursQ2
				+ ", nbGroup=" + nbGroup + ", nbStudent=" + nbStudent + ", fraction=" + fraction + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, id);
	}
	/**
	 * Equals on id and code
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AA other = (AA) obj;
		return Objects.equals(code, other.code) && id == other.id;
	}
}
