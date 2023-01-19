package be.helha.aemt.groupea1.control;

import java.io.Serializable;
import java.util.List;

import org.primefaces.event.RowEditEvent;

import be.helha.aemt.groupea1.ejb.UtilisateurEJB;
import be.helha.aemt.groupea1.entities.ERole;
import be.helha.aemt.groupea1.entities.Utilisateur;
import be.helha.aemt.groupea1.exception.InvalidEmailException;
import be.helha.aemt.groupea1.util.Toast;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;

@Named
@SessionScoped
public class UtilisateurControl implements Serializable {

	@EJB
	private UtilisateurEJB utilisateurEJB ;

	private List<Utilisateur> users ;
	public List<Utilisateur> getUsers() { return users;	}

	private Utilisateur newUser ;
	public Utilisateur getNewUser() {return newUser;}
	public void setNewUser(Utilisateur newUser) {this.newUser = newUser;}

	private Utilisateur removeUser ;
	public Utilisateur getRemoveUser() {return removeUser;}
	public void setRemoveUser(Utilisateur removeUser) {this.removeUser = removeUser;}

	private Utilisateur connectedUser;
	public Utilisateur getConnectedUser() {return this.connectedUser ;} 

	@PostConstruct
	public void init() {
		this.fetchConnectedUser();
		this.fetchUsers() ;
	}
	
    public ERole[] getRoles() {
        return ERole.values();
    }

	private void fetchConnectedUser() {

		/*get the username of the currently logged user
		the name is the email as defined in the "User Name Column" in the Payara Realm*/
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

		String email = request.getUserPrincipal().getName();

		try 
		{
			Utilisateur u = new Utilisateur(email);

			this.connectedUser = this.utilisateurEJB.find(u) ;

			//empty the password so it is impossible to get the hash from here
			this.connectedUser.setPassword("");
		} 
		catch (InvalidEmailException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void fetchUsers() {
		this.users = this.utilisateurEJB.findAll() ;
	}

	public void onRowEdit(RowEditEvent<Utilisateur> event) {
		Utilisateur updatedUser = event.getObject() ;

		if(this.utilisateurEJB.update(updatedUser) != null) 
			Toast.showInfoToast("Modifié", "Utilisateur modifié" );
		else
			Toast.showErrorToast("Erreur", "Erreur lors de la modification");
	}

	public void onRowCancel(RowEditEvent<Utilisateur> event) {
		FacesMessage msg = new FacesMessage("Modification annulée");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void openNewUser() {
		this.newUser = new Utilisateur() ;
	}

	public void saveNewUser() {
		Utilisateur addedUser = this.utilisateurEJB.add(newUser) ;

		if(addedUser != null) {
			this.users.add(addedUser) ;
			Toast.showInfoToast("Ajouté", "Utilisateur ajouté");
		}
		else
			Toast.showErrorToast("Erreur", "Erreur lors de l'ajout, cet utilisateur est peut être déjà présent");
	}

	public void removeUser() {
		if(this.removeUser == null) return ;

		Utilisateur removedUser = this.utilisateurEJB.delete(this.removeUser) ; 
		if(removedUser != null) {
			this.users.remove(removedUser) ;
			Toast.showInfoToast("Supprimé", "Utilisateur supprimé");
		}
		else
			Toast.showErrorToast("Erreur", "Erreur lors de la suppression");
	}
}