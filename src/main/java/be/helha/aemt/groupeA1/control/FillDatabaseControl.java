package be.helha.aemt.groupeA1.control;

import java.io.Serializable;

import be.helha.aemt.groupeA1.ejb.TeacherEJB;
import be.helha.aemt.groupeA1.ejb.UtilisateurEJB;
import be.helha.aemt.groupeA1.entities.EDepartment;
import be.helha.aemt.groupeA1.entities.ERole;
import be.helha.aemt.groupeA1.entities.Teacher;
import be.helha.aemt.groupeA1.entities.Utilisateur;
import be.helha.aemt.groupeA1.exception.InvalidEmailException;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class FillDatabaseControl implements Serializable{

	@EJB
	private TeacherEJB teacherEJB ;
	
	@EJB 
	private UtilisateurEJB utilisateurEJB;

	public void doAddTestTeacher() {

		for(int i = 1 ; i <= 20 ; i++) {
			try {
				Teacher t = new Teacher("N" + i, "P" + i, "n" + i + "p@helha.be", null) ;
				teacherEJB.add(t) ;
			}catch (InvalidEmailException e) {
				System.err.println(e.getMessage()) ;
			}
		}
	}
	
	public void doAddMockUtilisateur() {
		
		for(int i = 1 ; i <= 6 ; i++)
		{
			Utilisateur dde;
			Utilisateur secr;
			try 
			{
				dde = new Utilisateur("NDirDept" + i , "NDirDept" + i, "ndirdept" + i + "p@helha.be", 
						"d+Rn6wFp6C538JDfIXoyM1fGoVepjAN15vbbr+ApyDo=", ERole.DDE, EDepartment.values()[i-1]);
				
				secr = new Utilisateur("SDept" + i , "PDept" + i, "sdept" + i + "p@helha.be", 
						"d+Rn6wFp6C538JDfIXoyM1fGoVepjAN15vbbr+ApyDo=", ERole.S, EDepartment.values()[i-1]);
				
				
				utilisateurEJB.add(dde);
				utilisateurEJB.add(secr);
			} 
			catch (InvalidEmailException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/*
			Utilisateur ddom;
			try 
			{
				ddom = new Utilisateur("NDirDom", "PDirDom", "ndirdomp@helha.be", 
						"d+Rn6wFp6C538JDfIXoyM1fGoVepjAN15vbbr+ApyDo=", ERole.DDOM, );
				
				utilisateurEJB.add(ddom);
			} 
			catch (InvalidEmailException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
		}
		
	}
	
	
	
	
}
