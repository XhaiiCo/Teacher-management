package be.helha.aemt.groupea1.entities;

import java.util.List;

import be.helha.aemt.groupea1.exception.InvalidHoursException;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MissionTransversale")
public class MissionTransversale extends Mission {
	
	private final static String TYPE = "Transversale";
	
	public MissionTransversale() {
		super();
	}

	public MissionTransversale(String academicYear, String entitled, int hours, List<Teacher> teachers) throws InvalidHoursException {
		super(academicYear, entitled, hours, teachers);
	}

	public String getType() {
		return MissionTransversale.TYPE;
	}

	public String getName() {
		return "";
	}
	
}
