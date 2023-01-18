package be.helha.aemt.groupea1.entities;

import java.util.List;
import java.util.Objects;

import be.helha.aemt.groupea1.exception.InvalidHoursException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("MissionSection")
public class MissionSection extends Mission {

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private Section section;
	
	private final static String TYPE = "Section";
	
	public MissionSection() {
		super();
	}
	
	public MissionSection(String academicYear, String entitled, int hours, List<Teacher> teachers, Section section) throws InvalidHoursException {
		super(academicYear, entitled, hours, teachers);
		this.section = section;
	}

	public String getType() {
		return MissionSection.TYPE;
	}

	public String getName() {
		return section.getName();
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	@Override
	public String toString() {
		return "MissionSection [id=" + getId() + ", academicYear=" + getAcademicYear() + ", entitled=" + getEntitled() + ", hours=" + getHours() + "section=" + section + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(section);
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
		MissionSection other = (MissionSection) obj;
		return Objects.equals(section, other.section);
	}
	
	
	
}
