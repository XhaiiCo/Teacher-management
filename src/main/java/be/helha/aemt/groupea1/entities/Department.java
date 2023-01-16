package be.helha.aemt.groupea1.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Department implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id  ;
	
	@Column(unique = true)
	private String name ;
	
	@OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private List<Section> sections ;
	
	@OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private List<Mission> missions ;

	public Department() {}
	
	public Department(String name) {
		
		this.name = name;
		this.sections = new ArrayList<Section>();
		this.missions = new ArrayList<Mission>();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	public List<Mission> getMissions() {
		return missions;
	}

	public void setMissions(List<Mission> missions) {
		this.missions = missions;
	}

	
	
	
}
