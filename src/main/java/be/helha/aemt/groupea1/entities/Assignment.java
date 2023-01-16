package be.helha.aemt.groupea1.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Assignment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;

	private String academicYear ;

	private List<AA> aas ;

	private List<Mission> missions ;

	public Assignment() {} 
}
