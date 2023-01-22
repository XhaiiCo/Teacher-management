package be.helha.aemt.groupea1.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import be.helha.aemt.groupea1.exception.InvalidHoursException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class Mission implements Serializable {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;

	private String academicYear ;

	private String entitled ;

	private int hours ;

	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
	private List<Teacher> teachers = new ArrayList<>();

	public Mission (){
	}

	public Mission(String academicYear, String entitled, int hours, List<Teacher> teachers) throws InvalidHoursException {
		this.academicYear = academicYear;
		this.entitled = entitled;
		setHours(hours);
		setTeachers(teachers);
	}

	public Mission(String academicYear, String entitled, int hours) throws InvalidHoursException {
		this.academicYear = academicYear;
		this.entitled = entitled;
		setHours(hours);
		this.teachers = new ArrayList<Teacher>();
	}

	public String getAssigned() {
		if (this.teachers.isEmpty())
			return "Non attribuée";
		else
			return "Terminée";
	}

	public abstract String getType();
	public abstract String getName();

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

	public List<Teacher> getTeachers() {
		return teachers;
	}

	public boolean addTeacher(Teacher teacher) {
		if(this.teachers.contains(teacher))
			return false ;
		return this.teachers.add(teacher) ;
	}

	public void removeTeacher(Teacher teacher) {
		this.teachers.remove(teacher);
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers.clear();
		if (teachers != null)
			teachers.forEach(teacher -> this.addTeacher(teacher));
	}

	@Override
	public String toString() {
		return "Mission [id=" + id + ", academicYear=" + academicYear + ", entitled=" + entitled + ", hours=" + hours + ", teachers=" + teachers
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

	/**
	 * Equals on the id, entitle and academicYear
	 */
	@Override
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
