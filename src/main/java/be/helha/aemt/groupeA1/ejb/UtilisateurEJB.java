package be.helha.aemt.groupeA1.ejb;

import be.helha.aemt.groupeA1.dao.UtilisateurDAO;
import be.helha.aemt.groupeA1.entities.Utilisateur;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class UtilisateurEJB {

	@EJB
	private UtilisateurDAO utilisateurDAO;
	
	public Utilisateur add(Utilisateur utilisateur) {
		return utilisateurDAO.add(utilisateur) ;
	}
	
	public Utilisateur find(Utilisateur utilisateur) {
		return utilisateurDAO.find(utilisateur) ;
	}
}
