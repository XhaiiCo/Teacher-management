package be.helha.aemt.groupea1.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UE {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id  ;
	
	private String academicYear ;

	private Department department ;
	
	private Section section ;
	
	private String bloc ;
	
	private String code ;
	
	private String entitled ;
	
	private int credit ;
	
	private List<AA> aas ;
	
	public UE() {}
}

