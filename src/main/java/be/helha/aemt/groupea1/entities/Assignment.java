package be.helha.aemt.groupea1.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Assignment implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;

	private Teacher teacher ;

	private EQuarter quarter ;

	private int numGroup ;

	private int NbHours ;

	public Assignment() {}

	public Assignment(Teacher teacher,  EQuarter quarter, int nbHours, int group) {
		super();
		this.teacher = teacher ;
		this.quarter = quarter;
		this.NbHours = nbHours;
		this.numGroup = group ;
	}

	public int getNumGroup() {
		return numGroup;
	}

	public void setNumGroup(int numGroup) {
		this.numGroup = numGroup;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public EQuarter getQuarter() {
		return quarter;
	}

	public void setQuarter(EQuarter quarter) {
		this.quarter = quarter;
	}


	public int getNbHours() {
		return NbHours;
	}

	public void setNbHours(int nbHours) {
		NbHours = nbHours;
	}

	@Override
	public int hashCode() {
		return Objects.hash(NbHours, numGroup, quarter, teacher);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Assignment other = (Assignment) obj;
		return NbHours == other.NbHours && numGroup == other.numGroup && quarter == other.quarter
				&& Objects.equals(teacher, other.teacher);
	}

	@Override
	public String toString() {
		return "Assignment [id=" + id + ", teacher=" + teacher + ", quarter=" + quarter + ", numGroup=" + numGroup
				+ ", NbHours=" + NbHours + "]";
	}
	
}