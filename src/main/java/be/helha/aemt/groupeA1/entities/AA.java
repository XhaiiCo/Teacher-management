package be.helha.aemt.groupeA1.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AA {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id  ;
	
	private String academicYear ;
	
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
}
