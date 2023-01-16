package be.helha.aemt.groupea1.entities;

import java.io.Serializable;
import java.util.Objects;

import be.helha.aemt.groupea1.exception.NumberNegatifException;
import be.helha.aemt.groupea1.exception.HoursNotWantedException;
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
	
	private String academicYear ;
	
	@Column(unique=true)
	private String code ;
	
	private String entitled ;
	
	private int credit ;
	
	private int hours ;
	
	private int hoursQ1 ;

	private int hoursQ2 ;
	
	private int nbGroup ;
	
	private int nbStudent ;

	private EFraction fraction ;
	
	public  AA() {}

	public AA(String academicYear, String code, String entitled, int credit, int hours, int hoursQ1, int hoursQ2,
			int nbGroup, int nbStudent, EFraction fraction) throws NumberNegatifException,HoursNotWantedException{
		this.academicYear = academicYear;
		this.code = code;
		this.entitled = entitled;
		setCredit(credit);
		setHoursQ1(hoursQ1);
		setHoursQ2(hoursQ2);
		setHours(hours);
		setNbGroup(nbGroup);
		setNbStudent(nbStudent);
		this.fraction = fraction;
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

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) throws NumberNegatifException,HoursNotWantedException {
		if( hours >= 0 ) 
		{
			if (getHoursQ1()+ getHoursQ2()== hours)
			{
				this.hours = hours;
			}
			else 
			{
				throw new HoursNotWantedException();
			}
		}
		else {
			throw new NumberNegatifException();
		}
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
		return "AA [id=" + id + ", academicYear=" + academicYear + ", code=" + code + ", entitled=" + entitled
				+ ", credit=" + credit + ", hours=" + hours + ", hoursQ1=" + hoursQ1 + ", hoursQ2=" + hoursQ2
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
