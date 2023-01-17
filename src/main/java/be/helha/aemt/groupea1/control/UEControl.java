package be.helha.aemt.groupea1.control;

import java.io.Serializable;
import java.util.List;

import org.primefaces.event.RowEditEvent;

import be.helha.aemt.groupea1.ejb.UEEJB;
import be.helha.aemt.groupea1.entities.UE;
import be.helha.aemt.groupea1.exception.NotAvailableEmailException;
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
	public List<UE> getUe() { return ues; }
	public void setUe(List<UE> UE) {this.ues = ues; }

	private UE newUe ;//Used when create a new teacher
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

	public void showInfoToast(String summary, String detail ) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
	}

	public void showErrorToast(String summary, String detail ) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
	}

	public void onRowEdit(RowEditEvent<UE> event) {
		UE updatedUe = event.getObject() ;

		if(this.ueEJB.update(updatedUe) != null) 
			this.showInfoToast("Modifié", "UE modifié" );
		else
			this.showErrorToast("Erreur", new NotAvailableEmailException().getMessage());
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
			this.showInfoToast("Ajouté", "UE ajouté");
		}
		else
			this.showErrorToast("Erreur", "Erreur lors de l'ajout, cet ue est peut être déjà présent");
	}


	public void removeUe() {
		if(this.removeUe == null) return ;

		UE removedUe = this.ueEJB.delete(this.removeUe) ; 
		if(removedUe != null) {
			this.ues.remove(removedUe) ;
			this.showInfoToast("Supprimé"," UE supprimé");
		}
		else
		{
			this.showErrorToast("Error","Erreur de lors de la suppression");
		}
}


}
