package be.helha.aemt.groupea1.entities;

import java.util.List;
import java.util.Objects;

import be.helha.aemt.groupea1.exception.InvalidHoursException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("MissionDepartment")
public class MissionDepartment extends Mission {

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private Department department;
	
	private final static String TYPE = "Departement";
	
	public MissionDepartment() {
		super();
	}
	
	public MissionDepartment(String academicYear, String entitled, int hours, List<Teacher> teachers, Department department) throws InvalidHoursException {
		super(academicYear, entitled, hours, teachers);
		this.department = department;
	}
	
	public MissionDepartment(String academicYear, String entitled, int hours, Department department) throws InvalidHoursException {
		super(academicYear, entitled, hours);
		this.department = department;
	}

	public String getType() {
		return MissionDepartment.TYPE;
	}

	public String getName() {
		return department.getName();
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "MissionDepartment [id=" + getId() + ", academicYear=" + getAcademicYear() + ", entitled=" + getEntitled() + ", hours=" + getHours() + "department=" + department + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(department);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MissionDepartment other = (MissionDepartment) obj;
		return Objects.equals(department, other.department);
	}	
	
}
