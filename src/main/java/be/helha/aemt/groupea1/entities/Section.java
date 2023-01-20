package be.helha.aemt.groupea1.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "section", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "department_id"})
})
public class Section implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private Department department ;
	
	private String name;
	
	@OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private List<UE> ues = new ArrayList<>() ;
	
	public List<UE> getUes() {
		return ues;
	}
	
	public void setUes(List<UE> ues) {
		this.ues = new ArrayList<>() ;
		ues.forEach(ue -> this.addUe(ue));
	}

	public Section() {}
	
	public Section(Department department, String name) {
		this.department = department;
		this.name = name;
	}
	
	public boolean addUe(UE ue) {
		if(this.ues.contains(ue)) return false ;

		return this.ues.add(ue) ;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Section [id=" + id + ", department=" + department + ", name=" + name + ", missions=" + ", ues=" + ues + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(department, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Section other = (Section) obj;
		return Objects.equals(department, other.department) && Objects.equals(name, other.name);
	}
}
