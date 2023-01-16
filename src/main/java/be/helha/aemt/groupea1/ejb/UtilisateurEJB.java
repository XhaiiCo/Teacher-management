package be.helha.aemt.groupea1.ejb;

import java.util.List;

import be.helha.aemt.groupea1.dao.UtilisateurDAO;
import be.helha.aemt.groupea1.entities.Utilisateur;
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

	public List<Utilisateur> findAll() {
		return utilisateurDAO.findAll() ;
	}

	public Utilisateur update(Utilisateur utilisateur) {
		return utilisateurDAO.update(utilisateur) ;
	}

	public Utilisateur delete(Utilisateur utilisateur) {
		return utilisateurDAO.delete(utilisateur) ;
	}
}
