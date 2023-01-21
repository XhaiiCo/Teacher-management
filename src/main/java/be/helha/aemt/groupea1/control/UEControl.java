package be.helha.aemt.groupea1.control;

import java.io.Serializable;
import java.util.List;

import org.primefaces.event.RowEditEvent;

import be.helha.aemt.groupea1.ejb.UEEJB;
import be.helha.aemt.groupea1.entities.UE;
import be.helha.aemt.groupea1.exception.NotAvailableEmailException;
import be.helha.aemt.groupea1.util.Toast;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@SessionScoped
public class UEControl implements Serializable{
	
	private List<UE> ues;
	public List<UE> getUes() {
		return ues;
	}
	public void setUes(List<UE> ues) {
		this.ues = ues;
	}

	private UE newUe ;//Used when create a new UE
	public UE getNewUe() {return newUe;}
	public void setNewUe(UE newUe) {this.newUe = newUe;}
	
	private UE removeUe ;
	public UE getRemoveUe() {return removeUe;}
	public void setRemoveUe(UE removeUe) {this.removeUe = removeUe;}

	private UE selectedUe ;
	public UE getSelectedUe() {return selectedUe;}
	public void setSelectedUe(UE selectedUe) {this.selectedUe= selectedUe;}

	
	@EJB
	private UEEJB ueEJB ; 
	
	@PostConstruct
	public void init() {
		this.ues = this.ueEJB.findAll() ;
	}

	public void onRowEdit(RowEditEvent<UE> event) {
		UE updatedUe = event.getObject() ;

		if(this.ueEJB.update(updatedUe) != null) 
			Toast.showInfoToast("Modifié", "UE modifié" );
		else
			Toast.showErrorToast("Erreur", new NotAvailableEmailException().getMessage());
	}

	public void onRowCancel(RowEditEvent<UE> event) {
		FacesMessage msg = new FacesMessage("Modification annulée");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/**
	 * create a new UE, called when the user click on the new button
	 */
	public void openNewUe() {
		this.newUe = new UE() ;
	}

	/**
	 * Save the new ue in db
	 * Used when the use click on the add button
	 */
	public void saveNewUe() {
		UE addedUe = this.ueEJB.add(newUe) ;

		if(addedUe != null) {
			this.ues.add(addedUe) ;
			Toast.showInfoToast("Ajouté", "UE ajouté");
		}
		else
			Toast.showErrorToast("Erreur", "Erreur lors de l'ajout, cet ue est peut être déjà présent");
	}


	public void removeUe() {
		if(this.removeUe == null) return ;

		UE removedUe = this.ueEJB.delete(this.removeUe) ; 
		if(removedUe != null) {
			this.ues.remove(removedUe) ;
			Toast.showInfoToast("Supprimé"," UE supprimé");
		}
		else
		{
			Toast.showErrorToast("Error","Erreur de lors de la suppression");
		}
		
	}
	
}
