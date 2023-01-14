package be.helha.aemt.groupeA1.dao;

import java.util.List;

import be.helha.aemt.groupeA1.entities.Utilisateur;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;

@Stateless//Used to do independent operations, it does not have any associated client state
@LocalBean
public class UtilisateurDAO extends AbstractDAO<Utilisateur> {

	public UtilisateurDAO() {
		super(Utilisateur.class);
	}
	
	@Override
	public Utilisateur add(Utilisateur utilisateur) {
		if(utilisateur == null) return null ;

		if(find(utilisateur) != null) return null ;

		return super.add(utilisateur);
	}
	
	
	/**
	 * Find the user based on his email
	 * 
	 * @return the user or null if not found
	 */
	@Override
	public Utilisateur find(Utilisateur utilisateur) {
		if(utilisateur == null) return null ;

		String rq = "SELECT u FROM Utilisateur u where u.email = ?1" ;

		TypedQuery<Utilisateur> query = em.createQuery(rq, Utilisateur.class);
		query.setParameter(1, utilisateur.getEmail()) ;

		List<Utilisateur> result = query.getResultList() ;

		if(result.isEmpty()) return null ;

		return result.get(0) ; 
	}

}
