package be.helha.aemt.groupea1.control;

import java.io.Serializable;
import java.util.List;

import be.helha.aemt.groupea1.ejb.DepartmentEJB;
import be.helha.aemt.groupea1.ejb.SectionEJB;
import be.helha.aemt.groupea1.entities.Department;
import be.helha.aemt.groupea1.entities.Section;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class SectionControl implements Serializable {

	@EJB
	private SectionEJB sectionEJB ;

	private List<Section> sections;
	public List<Section> getSections() { return sections;	}
	
	@PostConstruct
	public void init() {
		this.fetchSections();
	}
	
	private void fetchSections() {
		this.sections = this.sectionEJB.findAll() ;
	}
	
}
