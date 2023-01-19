package be.helha.aemt.groupea1.util;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

public class Toast {

	public static void showInfoToast(String summary, String detail ) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
	}

	public static void showErrorToast(String summary, String detail ) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
	}
}
