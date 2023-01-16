package be.helha.aemt.groupea1.control;

import java.io.Serializable;

import be.helha.aemt.groupea1.ejb.UtilisateurEJB;
import be.helha.aemt.groupea1.entities.Utilisateur;
import be.helha.aemt.groupea1.exception.InvalidEmailException;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;

@Named
@SessionScoped
public class UtilisateurControl implements Serializable {
	
	@EJB
	private UtilisateurEJB utilisateurEJB ;
	
	private Utilisateur utilisateur;
		
	@PostConstruct
	public void init() {
		this.currentUserInfos();
	}
	
	private void currentUserInfos() {
		
		/*get the username of the currently logged user
		the name is the email as defined in the "User Name Column" in the Payara Realm*/
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		 
		String email = request.getUserPrincipal().getName();
		
		try 
		{
			Utilisateur u = new Utilisateur(email);
			
			this.utilisateur = this.utilisateurEJB.find(u) ;
			
			//empty the password so it is impossible to get the hash from here
			this.utilisateur.setPassword("");
		} 
		catch (InvalidEmailException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Utilisateur getUtilisateur() 
	{
		return utilisateur;
	}
	
	
	
}
