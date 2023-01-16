package be.helha.aemt.groupea1.entities;

import java.io.Serializable;

import be.helha.aemt.groupea1.exception.InvalidHoursException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Mission implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;
	
	private String academicYear ;
	
	private String entitled ;
	
	private int hours ;

	public Mission (){
	}
	
	public Mission(String academicYear, String entitled, int hours) throws InvalidHoursException {
		this.academicYear = academicYear;
		this.entitled = entitled;
		setHours(hours);
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

	public String getEntitled() {
		return entitled;
	}

	public void setEntitled(String entitled) {
		this.entitled = entitled;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) throws InvalidHoursException {
		if (hours <= 1400)this.hours=hours;
			
		else throw new InvalidHoursException();	
	}

	@Override
	public String toString() {
		return "Mission [id=" + id + ", academicYear=" + academicYear + ", entitled=" + entitled + ", hours=" + hours
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((academicYear == null) ? 0 : academicYear.hashCode());
		result = prime * result + ((entitled == null) ? 0 : entitled.hashCode());
		result = prime * result + id;
		return result;
	}

	@Override
	/**
	 * Equals on the id ,entitle and academicYear
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mission other = (Mission) obj;
		if (academicYear == null) {
			if (other.academicYear != null)
				return false;
		} else if (!academicYear.equals(other.academicYear))
			return false;
		if (entitled == null) {
			if (other.entitled != null)
				return false;
		} else if (!entitled.equals(other.entitled))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	

	
}
