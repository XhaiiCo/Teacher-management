package be.helha.aemt.groupea1.dao;

import be.helha.aemt.groupea1.entities.Section;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

@Stateless//Used to do independent operations, it does not have any associated client state
@LocalBean
public class SectionDAO extends AbstractDAO<Section> {

	public SectionDAO() {
		super(Section.class);
	}

	@Override
	/**
	 * Redefinition of the method to add a condition to check that there are no duplicates on the id
	 */
	public Section add(Section section) {
		if(section == null) return null ;

		return super.add(section);
	}
	
	
}
