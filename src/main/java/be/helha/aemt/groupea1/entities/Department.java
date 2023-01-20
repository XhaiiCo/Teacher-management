package be.helha.aemt.groupea1.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
	private List<Section> sections = new ArrayList<>();
	
	public Department() {}
	
	public Department(String name) {
		this.name = name;
	}
	
	public boolean addSection(Section section) {	
		if(this.sections.contains(section)) return false ;

		return this.sections.add(section) ;
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
		this.sections = new ArrayList<>() ;
		sections.forEach(section -> this.addSection(section));
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + ", sections=" + sections  ;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	/**
	 * equals on the department name
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		return Objects.equals(name, other.name);
	}
}
