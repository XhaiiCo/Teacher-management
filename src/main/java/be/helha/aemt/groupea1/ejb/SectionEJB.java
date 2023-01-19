package be.helha.aemt.groupea1.ejb;

import java.util.List;

import be.helha.aemt.groupea1.dao.SectionDAO;
import be.helha.aemt.groupea1.entities.Department;
import be.helha.aemt.groupea1.entities.Section;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class SectionEJB {

	@EJB
	private SectionDAO sectionDAO;
	
	public Section add(Section section) {
		return sectionDAO.add(section) ;
	}

	public List<Section> findAll() {
		return sectionDAO.findAll();
	}
	
}
