package be.helha.aemt.groupea1.control;

import java.io.Serializable;
import java.util.List;

import be.helha.aemt.groupea1.ejb.AAEJB;
import be.helha.aemt.groupea1.entities.AA;
import be.helha.aemt.groupea1.entities.Teacher;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class AaControl implements Serializable {

	@EJB
	private AAEJB aaEJB ;

	private List<AA> aas ;
	public List<AA> getAas() {
		return aas ;
	}
	
	private AA selected ;
	public AA getSelected() {return selected ;	}
	
	public List<Teacher> getSelectedTeachers(){return selected.getTeachers() ;}

	@PostConstruct
	public void init() {
		this.aas = aaEJB.findAll() ;
	}
	
	public String openDetail(AA aa) {
		this.selected = aa ;
		
		return "/DDE/aaDetail" ;
	}
}