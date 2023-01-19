package be.helha.aemt.groupea1.ejb;

import java.util.List;

import be.helha.aemt.groupea1.dao.AADAO;
import be.helha.aemt.groupea1.dao.AssignmentDAO;
import be.helha.aemt.groupea1.entities.AA;
import be.helha.aemt.groupea1.entities.Assignment;
import be.helha.aemt.groupea1.entities.Teacher;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class AssignmentEJB {

	@EJB
	private AssignmentDAO assignmentDAO ;
	
	public Assignment delete(Assignment assignment) {
		return this.assignmentDAO.delete(assignment) ;
	}
}
